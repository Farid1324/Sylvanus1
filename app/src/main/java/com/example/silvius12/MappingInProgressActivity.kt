package com.example.silvius12

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class MappingInProgressActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_mapping_in_progress)

        findViewById<MaterialButton>(R.id.btnBackAtStart)?.setOnClickListener {
            startActivity(Intent(this, CheckBoundaryActivity::class.java))
        } ?: Toast.makeText(this, "btnBackAtStart missing", Toast.LENGTH_LONG).show()
    }
}