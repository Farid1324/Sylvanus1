package com.example.silvius12

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import com.example.silvius12.data.ProfileRepository
import com.google.android.material.button.MaterialButton

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_welcome)

        // Welcome is the task root, so this is the real start of onboarding:
        // detach from any earlier profile so a new run writes a new row rather
        // than overwriting whoever was registered last. No row is created until
        // "About you" is completed, so simply opening the app costs nothing.
        if (savedInstanceState == null && isTaskRoot) {
            ProfileRepository(this).startNewProfile()
        }

        setupLanguageDropdown()
        setupGetStartedButton()
    }

    private fun setupLanguageDropdown() {
        val dropdown = findViewById<AutoCompleteTextView>(R.id.dropdownLanguage) ?: return

        val languages = arrayOf(
            getString(R.string.language_english),
            "Español",
            "Deutsch",
            "Français",
            "العربية"
        )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            languages
        )
        dropdown.setAdapter(adapter)

        // Keep the dropdown open on click and prevent keyboard
        dropdown.setOnClickListener {
            dropdown.showDropDown()
        }
        dropdown.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) dropdown.showDropDown()
        }
    }

    private fun setupGetStartedButton() {
        findViewById<MaterialButton>(R.id.btnGetStarted).setOnClickListener {
            // Optional: light haptic
            it.performHapticFeedback(android.view.HapticFeedbackConstants.VIRTUAL_KEY)
            startActivity(Intent(this, StepsOverviewActivity::class.java))
            // Smooth transition
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }
}