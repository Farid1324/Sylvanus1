package com.example.silvius12

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class BeforeYouDrawActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_before_you_draw)

        findViewById<MaterialButton>(R.id.btnOpenMap).setOnClickListener {
            startActivity(Intent(this, DrawFindFieldActivity::class.java))
        }
    }
}