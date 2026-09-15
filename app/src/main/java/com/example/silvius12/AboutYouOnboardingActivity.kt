package com.example.silvius12

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.silvius12.data.ProfileRepository
import com.example.silvius12.ui.asPicker
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import java.io.File

class AboutYouOnboardingActivity : AppCompatActivity() {

    private var photoUri: Uri? = null
    private val profiles by lazy { ProfileRepository(this) }

    // ---- Gallery picker ----
    private val pickImage = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            Toast.makeText(this, "ID photo selected", Toast.LENGTH_SHORT).show()
        }
    }

    // ---- Camera capture ----
    private val takePicture = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && photoUri != null) {
            Toast.makeText(this, "ID photo captured", Toast.LENGTH_SHORT).show()
        }
    }

    // ---- Camera permission ----
    private val requestCameraPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) openCamera()
        else Toast.makeText(this, "Camera permission needed", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_about_you)

        setupCountryDropdown()
        setupCropDropdown()
        setupCameraIcon()
        setupContinue()
    }

    // ========================= COUNTRY =========================
    private fun setupCountryDropdown() {
        val countries = listOf(
            "Ethiopia", "Kenya", "Uganda", "Tanzania", "Rwanda",
            "Ghana", "Nigeria", "Côte d'Ivoire", "Cameroon",
            "Colombia", "Brazil", "Peru", "Honduras", "Guatemala",
            "Vietnam", "Indonesia", "India", "Other"
        )

        findViewById<AutoCompleteTextView>(R.id.dropdownCountry).asPicker(countries)
    }

    // ========================= CROP =========================
    private fun setupCropDropdown() {
        val crops = listOf(
            "Coffee", "Cocoa", "Tea", "Banana", "Maize",
            "Rice", "Wheat", "Cassava", "Beans", "Avocado",
            "Cotton", "Sugarcane", "Other"
        )

        findViewById<AutoCompleteTextView>(R.id.dropdownMainCrop).asPicker(crops)
    }

    // ========================= CAMERA / PHOTO =========================
    private fun setupCameraIcon() {
        // The camera is its own button inside the National ID row, not the
        // field's end icon — setEndIconOnClickListener never fired here.
        findViewById<ImageButton>(R.id.btnCameraId).setOnClickListener {
            val options = arrayOf("Take photo", "Choose from gallery")
            androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Add National ID photo")
                .setItems(options) { _, which ->
                    when (which) {
                        0 -> checkCameraAndOpen()
                        1 -> pickImage.launch("image/*")
                    }
                }
                .show()
        }
    }

    private fun checkCameraAndOpen() {
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED -> openCamera()
            else -> requestCameraPermission.launch(Manifest.permission.CAMERA)
        }
    }

    private fun openCamera() {
        val photoFile = File(cacheDir, "id_photo_${System.currentTimeMillis()}.jpg")
        val uri = FileProvider.getUriForFile(
            this,
            "${packageName}.fileprovider",
            photoFile
        )
        photoUri = uri
        takePicture.launch(uri)   // ← non-null Uri, fixes the type error
    }

    // ========================= CONTINUE =========================
    private fun setupContinue() {
        findViewById<MaterialButton>(R.id.btnContinueAboutYou).setOnClickListener {
            if (saveProfile()) {
                startActivity(Intent(this, CreateAccountActivity::class.java))
            }
        }
    }

    /**
     * Writes the four answers to the database. Only the name is required — the
     * rest can be filled in later — so the step is blocked just on that.
     */
    private fun saveProfile(): Boolean {
        val nameField = findViewById<TextInputEditText>(R.id.etFullName)
        val fullName = nameField.text?.toString().orEmpty().trim()
        if (fullName.isEmpty()) {
            nameField.error = getString(R.string.error_name_required)
            nameField.requestFocus()
            return false
        }

        val id = profiles.saveAboutYou(
            fullName = fullName,
            nationalId = findViewById<TextInputEditText>(R.id.etNationalId)
                .text?.toString().orEmpty(),
            country = findViewById<AutoCompleteTextView>(R.id.dropdownCountry)
                .text?.toString().orEmpty(),
            mainCrop = findViewById<AutoCompleteTextView>(R.id.dropdownMainCrop)
                .text?.toString().orEmpty()
        )

        if (id <= 0) {
            Toast.makeText(this, R.string.error_save_failed, Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }
}
