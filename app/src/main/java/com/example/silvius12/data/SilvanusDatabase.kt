package com.example.silvius12.data

import android.content.Context
import android.content.pm.ApplicationInfo
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.File

/**
 * On-device store for everything onboarding collects.
 *
 * Plain [SQLiteOpenHelper] rather than Room: the app has no annotation
 * processor configured, and one table with a handful of columns does not earn
 * the extra build plumbing.
 *
 * Debug builds keep the file in the app's external files directory, where
 * `adb pull` and Android Studio's Device Explorer can read it without `run-as`.
 * Release builds keep it in private storage, because the table holds national
 * IDs and phone numbers and external storage is readable by the device owner
 * and by anything holding storage permission.
 */
class SilvanusDatabase private constructor(context: Context) :
    SQLiteOpenHelper(context.applicationContext, pathFor(context), null, VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE $TABLE_PROFILE (
                $COL_ID           INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_FULL_NAME    TEXT    NOT NULL,
                $COL_NATIONAL_ID  TEXT,
                $COL_COUNTRY      TEXT,
                $COL_MAIN_CROP    TEXT,
                $COL_COUNTRY_CODE TEXT,
                $COL_PHONE        TEXT,
                $COL_CREATED_AT   INTEGER NOT NULL,
                $COL_UPDATED_AT   INTEGER NOT NULL
            )
            """.trimIndent()
        )
        // One farmer should not end up with two rows if onboarding is repeated
        // with the same number, so the phone is the natural key once it is set.
        db.execSQL(
            "CREATE UNIQUE INDEX idx_profile_phone ON $TABLE_PROFILE " +
                    "($COL_COUNTRY_CODE, $COL_PHONE) " +
                    "WHERE $COL_PHONE IS NOT NULL AND $COL_PHONE <> ''"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Nothing to migrate yet. Recreating is safe only while the schema is
        // at version 1; add real ALTER TABLE steps here before shipping v2.
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PROFILE")
        onCreate(db)
    }

    companion object {
        const val NAME = "silvanus.db"
        const val VERSION = 1

        const val TABLE_PROFILE = "farmer_profile"
        const val COL_ID = "id"
        const val COL_FULL_NAME = "full_name"
        const val COL_NATIONAL_ID = "national_id"
        const val COL_COUNTRY = "country"
        const val COL_MAIN_CROP = "main_crop"
        const val COL_COUNTRY_CODE = "country_code"
        const val COL_PHONE = "phone_number"
        const val COL_CREATED_AT = "created_at"
        const val COL_UPDATED_AT = "updated_at"

        private const val TAG = "SilvanusDatabase"

        private fun isDebuggable(context: Context): Boolean =
            (context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0

        /**
         * Absolute path in debug builds, bare name in release.
         *
         * [SQLiteOpenHelper] passes the name straight to the context, which
         * treats anything starting with a separator as an absolute path.
         */
        private fun pathFor(context: Context): String {
            val app = context.applicationContext
            if (!isDebuggable(app)) return NAME
            val dir = app.getExternalFilesDir(null) ?: return NAME
            return File(dir, NAME).absolutePath
        }

        /** Where the file actually is, for logging and for the debug banner. */
        fun fileOf(context: Context): File {
            val path = pathFor(context)
            return if (path.startsWith(File.separator)) File(path)
            else context.applicationContext.getDatabasePath(path)
        }

        /**
         * Moves an existing private-storage database to the external location
         * the first time a debug build runs after the switch, so the rows
         * already collected are not stranded.
         */
        private fun migrateIfNeeded(context: Context) {
            val app = context.applicationContext
            if (!isDebuggable(app)) return
            val target = fileOf(app)
            if (target.absolutePath == app.getDatabasePath(NAME).absolutePath) return
            if (target.exists()) return

            val legacy = app.getDatabasePath(NAME)
            if (!legacy.exists()) return
            try {
                target.parentFile?.mkdirs()
                legacy.copyTo(target, overwrite = true)
                Log.i(TAG, "moved existing database to ${target.absolutePath}")
            } catch (e: Exception) {
                Log.w(TAG, "could not move the existing database", e)
            }
        }

        @Volatile
        private var instance: SilvanusDatabase? = null

        fun get(context: Context): SilvanusDatabase =
            instance ?: synchronized(this) {
                instance ?: run {
                    migrateIfNeeded(context)
                    Log.i(TAG, "database at ${fileOf(context).absolutePath}")
                    SilvanusDatabase(context).also { instance = it }
                }
            }
    }
}
