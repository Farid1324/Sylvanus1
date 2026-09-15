// WalkBeforeYouStartActivity.kt
package com.example.silvius12

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class WalkBeforeYouStartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_before_walking)
        findViewById<View>(R.id.btnNext).setOnClickListener {
            startActivity(Intent(this, AtThePlotActivity::class.java))
        }
    }
}