// WhatHappensNextActivity.kt
package com.example.silvius12

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class WhatHappensNextActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_what_happens_next)
        findViewById<View>(R.id.btnDone).setOnClickListener {
            finishAffinity()
        }
    }
}