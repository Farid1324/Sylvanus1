package com.example.silvius12

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class CheckBoundaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_check_boundary)

        findViewById<MaterialButton>(R.id.btnSaveMap)?.setOnClickListener {
            startActivity(Intent(this, MappingCompleteActivity::class.java))
        } ?: Toast.makeText(this, "btnSaveMap missing", Toast.LENGTH_LONG).show()
    }
}