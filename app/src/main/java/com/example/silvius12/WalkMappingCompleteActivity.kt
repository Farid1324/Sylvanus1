// WalkMappingCompleteActivity.kt
package com.example.silvius12

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class WalkMappingCompleteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_mapping_complete)
        findViewById<View>(R.id.btnViewNext).setOnClickListener {
            startActivity(Intent(this, WhatHappensNextActivity::class.java))
        }
    }
}