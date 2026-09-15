package com.example.silvius12

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class DrawCheckActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_draw_check)

        findViewById<MaterialButton>(R.id.btnEditPoints).setOnClickListener {
            finish() // back to mark corners
        }

        findViewById<MaterialButton>(R.id.btnSaveMap).setOnClickListener {
            startActivity(Intent(this, MappingCompleteActivity::class.java))
        }
    }
}