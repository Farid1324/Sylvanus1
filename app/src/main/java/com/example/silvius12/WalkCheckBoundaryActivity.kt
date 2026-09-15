// WalkCheckBoundaryActivity.kt
package com.example.silvius12

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class WalkCheckBoundaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_check_boundary)
        findViewById<View>(R.id.btnSaveMap).setOnClickListener {
            startActivity(Intent(this, WalkMappingCompleteActivity::class.java))
        }
    }
}