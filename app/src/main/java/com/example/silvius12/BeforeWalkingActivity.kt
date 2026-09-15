package com.example.silvius12

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class BeforeWalkingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_before_walking)

        findViewById<MaterialButton>(R.id.btnNext)?.setOnClickListener {
            startActivity(Intent(this, AtThePlotActivity::class.java))
        } ?: Toast.makeText(this, "btnNext missing", Toast.LENGTH_LONG).show()
    }
}