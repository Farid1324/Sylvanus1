package com.example.silvius12

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class DrawFindFieldActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_draw_find_field)

        findViewById<MaterialButton>(R.id.btnFieldVisible).setOnClickListener {
            startActivity(Intent(this, DrawMarkCornersActivity::class.java))
        }
    }
}