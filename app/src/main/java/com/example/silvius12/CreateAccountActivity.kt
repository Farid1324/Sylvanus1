package com.example.silvius12

import android.content.Intent
import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.silvius12.data.ProfileRepository
import com.example.silvius12.ui.asPicker
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class CreateAccountActivity : AppCompatActivity() {

    private val profiles by lazy { ProfileRepository(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_create_account)

        setupCountryCodeDropdown()
        setupSendCode()
    }

    /**
     * Dialling codes, labelled with their country so the list is readable. Only
     * the code itself is stored, so the label is stripped on selection.
     */
    private fun setupCountryCodeDropdown() {
        val codes = listOf(
            "+251 Ethiopia", "+254 Kenya", "+256 Uganda", "+255 Tanzania",
            "+250 Rwanda", "+233 Ghana", "+234 Nigeria", "+225 Côte d'Ivoire",
            "+237 Cameroon", "+57 Colombia", "+55 Brazil", "+51 Peru",
            "+504 Honduras", "+502 Guatemala", "+84 Vietnam", "+62 Indonesia",
            "+91 India", "+44 United Kingdom", "+1 United States"
        )

        val dropdown = findViewById<AutoCompleteTextView>(R.id.dropdownCountryCode)
        // Only the dialling code is stored, so the country label is dropped on pick.
        dropdown.asPicker(codes) { picked -> dropdown.setText(picked.substringBefore(' '), false) }
    }

    private fun setupSendCode() {
        findViewById<MaterialButton>(R.id.btnSendCode).setOnClickListener {
            if (savePhone()) {
                startActivity(Intent(this, ReadyToMapActivity::class.java))
            }
        }
    }

    /** Completes the profile started on "About you" with the phone number. */
    private fun savePhone(): Boolean {
        val phoneField = findViewById<TextInputEditText>(R.id.etPhoneNumber)
        val phone = phoneField.text?.toString().orEmpty().trim()
        if (phone.isEmpty()) {
            phoneField.error = getString(R.string.error_phone_required)
            phoneField.requestFocus()
            return false
        }

        val code = findViewById<AutoCompleteTextView>(R.id.dropdownCountryCode)
            .text?.toString().orEmpty().substringBefore(' ').trim()

        return when (profiles.savePhone(countryCode = code, phoneNumber = phone)) {
            ProfileRepository.PhoneResult.Saved -> true

            ProfileRepository.PhoneResult.NoProfile -> {
                Toast.makeText(this, R.string.error_profile_missing, Toast.LENGTH_LONG).show()
                false
            }

            ProfileRepository.PhoneResult.AlreadyRegistered -> {
                phoneField.error = getString(R.string.error_phone_taken)
                phoneField.requestFocus()
                false
            }
        }
    }
}
