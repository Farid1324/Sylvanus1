package com.example.silvius12

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class AtThePlotActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_at_the_plot)

        val btn = findViewById<MaterialButton>(R.id.btnStartMapping)
        if (btn == null) {
            Toast.makeText(this, "btnStartMapping NOT FOUND in layout", Toast.LENGTH_LONG).show()
            Log.e("NAV", "btnStartMapping is null")
            return
        }

        btn.setOnClickListener {
            Toast.makeText(this, "Opening mapping…", Toast.LENGTH_SHORT).show()
            Log.d("NAV", "Starting MappingInProgressActivity")
            startActivity(Intent(this, MappingInProgressActivity::class.java))
        }
    }
}