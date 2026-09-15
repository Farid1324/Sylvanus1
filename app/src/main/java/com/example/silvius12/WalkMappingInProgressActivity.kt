// WalkMappingInProgressActivity.kt
package com.example.silvius12

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class WalkMappingInProgressActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_mapping_in_progress)
        findViewById<View>(R.id.btnBackAtStart).setOnClickListener {
            startActivity(Intent(this, WalkCheckBoundaryActivity::class.java))
        }
    }
}