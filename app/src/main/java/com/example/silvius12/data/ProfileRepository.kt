package com.example.silvius12.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import com.example.silvius12.data.SilvanusDatabase.Companion.COL_COUNTRY
import com.example.silvius12.data.SilvanusDatabase.Companion.COL_COUNTRY_CODE
import com.example.silvius12.data.SilvanusDatabase.Companion.COL_CREATED_AT
import com.example.silvius12.data.SilvanusDatabase.Companion.COL_FULL_NAME
import com.example.silvius12.data.SilvanusDatabase.Companion.COL_ID
import com.example.silvius12.data.SilvanusDatabase.Companion.COL_MAIN_CROP
import com.example.silvius12.data.SilvanusDatabase.Companion.COL_NATIONAL_ID
import com.example.silvius12.data.SilvanusDatabase.Companion.COL_PHONE
import com.example.silvius12.data.SilvanusDatabase.Companion.COL_UPDATED_AT
import com.example.silvius12.data.SilvanusDatabase.Companion.TABLE_PROFILE

/**
 * Reads and writes the farmer profile.
 *
 * Onboarding fills one row across two screens, so the id of the row in progress
 * is held in preferences: "About you" creates it, "Create your account"
 * completes it. Anything that needs the profile later just calls [current].
 */
class ProfileRepository(context: Context) {

    private val app = context.applicationContext
    private val db = SilvanusDatabase.get(app)
    private val prefs = app.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    /** Row id of the profile onboarding is currently filling, if any. */
    var currentProfileId: Long
        get() = prefs.getLong(KEY_CURRENT_ID, 0L)
        private set(value) = prefs.edit().putLong(KEY_CURRENT_ID, value).apply()

    /**
     * Stores the "About you" answers. Creates the row on first use and rewrites
     * it if the user comes back and edits, so repeating the step does not leave
     * a trail of half-filled profiles.
     */
    fun saveAboutYou(
        fullName: String,
        nationalId: String,
        country: String,
        mainCrop: String
    ): Long {
        val now = System.currentTimeMillis()
        val values = ContentValues().apply {
            put(COL_FULL_NAME, fullName.trim())
            put(COL_NATIONAL_ID, nationalId.trim())
            put(COL_COUNTRY, country.trim())
            put(COL_MAIN_CROP, mainCrop.trim())
            put(COL_UPDATED_AT, now)
        }

        val existing = currentProfileId
        val id = if (existing > 0 && exists(existing)) {
            db.writableDatabase.update(
                TABLE_PROFILE, values, "$COL_ID = ?", arrayOf(existing.toString())
            )
            existing
        } else {
            values.put(COL_CREATED_AT, now)
            db.writableDatabase.insert(TABLE_PROFILE, null, values)
        }

        if (id > 0) currentProfileId = id
        Log.i(TAG, "saveAboutYou -> row $id")
        return id
    }

    /** Outcome of attaching a phone number to the profile in progress. */
    sealed interface PhoneResult {
        data object Saved : PhoneResult

        /** "About you" was never completed, so there is no row to attach to. */
        data object NoProfile : PhoneResult

        /** Another profile already holds this number; the unique index rejects it. */
        data object AlreadyRegistered : PhoneResult
    }

    /** Completes the row with the phone number. */
    fun savePhone(countryCode: String, phoneNumber: String): PhoneResult {
        val id = currentProfileId
        if (id <= 0 || !exists(id)) {
            Log.w(TAG, "savePhone with no profile in progress")
            return PhoneResult.NoProfile
        }
        val values = ContentValues().apply {
            put(COL_COUNTRY_CODE, countryCode.trim())
            put(COL_PHONE, phoneNumber.trim())
            put(COL_UPDATED_AT, System.currentTimeMillis())
        }
        return try {
            val rows = db.writableDatabase.update(
                TABLE_PROFILE, values, "$COL_ID = ?", arrayOf(id.toString())
            )
            Log.i(TAG, "savePhone -> row $id, $rows updated")
            if (rows > 0) PhoneResult.Saved else PhoneResult.NoProfile
        } catch (e: SQLiteConstraintException) {
            Log.w(TAG, "phone already registered to another profile", e)
            PhoneResult.AlreadyRegistered
        }
    }

    /** The profile onboarding is filling, or null before it starts. */
    fun current(): FarmerProfile? = byId(currentProfileId)

    fun byId(id: Long): FarmerProfile? {
        if (id <= 0) return null
        db.readableDatabase.query(
            TABLE_PROFILE, null, "$COL_ID = ?", arrayOf(id.toString()),
            null, null, null
        ).use { c ->
            return if (c.moveToFirst()) c.toProfile() else null
        }
    }

    /** Every saved profile, newest first. Handy for a debug screen or export. */
    fun all(): List<FarmerProfile> {
        val out = mutableListOf<FarmerProfile>()
        db.readableDatabase.query(
            TABLE_PROFILE, null, null, null, null, null, "$COL_ID DESC"
        ).use { c ->
            while (c.moveToNext()) out += c.toProfile()
        }
        return out
    }

    /** Forgets which row onboarding was filling, so the next run starts fresh. */
    fun startNewProfile() {
        currentProfileId = 0L
    }

    private fun exists(id: Long): Boolean =
        db.readableDatabase.query(
            TABLE_PROFILE, arrayOf(COL_ID), "$COL_ID = ?", arrayOf(id.toString()),
            null, null, null
        ).use { it.count > 0 }

    private fun Cursor.str(column: String): String =
        getColumnIndex(column).let { if (it < 0 || isNull(it)) "" else getString(it) }

    private fun Cursor.long(column: String): Long =
        getColumnIndex(column).let { if (it < 0 || isNull(it)) 0L else getLong(it) }

    private fun Cursor.toProfile() = FarmerProfile(
        id = long(COL_ID),
        fullName = str(COL_FULL_NAME),
        nationalId = str(COL_NATIONAL_ID),
        country = str(COL_COUNTRY),
        mainCrop = str(COL_MAIN_CROP),
        countryCode = str(COL_COUNTRY_CODE),
        phoneNumber = str(COL_PHONE),
        createdAt = long(COL_CREATED_AT),
        updatedAt = long(COL_UPDATED_AT)
    )

    private companion object {
        const val TAG = "ProfileRepository"
        const val PREFS = "silvanus_onboarding"
        const val KEY_CURRENT_ID = "current_profile_id"
    }
}
