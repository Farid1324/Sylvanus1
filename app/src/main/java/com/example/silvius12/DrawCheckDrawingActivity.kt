// DrawCheckDrawingActivity.kt
package com.example.silvius12

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class DrawCheckDrawingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_draw_check)
        findViewById<View>(R.id.btnSaveMap).setOnClickListener {
            startActivity(Intent(this, DrawMappingCompleteActivity::class.java))
        }
    }
}