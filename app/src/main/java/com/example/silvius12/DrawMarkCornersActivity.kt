// DrawMarkCornersActivity.kt
package com.example.silvius12

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class DrawMarkCornersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_draw_mark_corners)
        findViewById<View>(R.id.btnCloseBoundary).setOnClickListener {
            startActivity(Intent(this, DrawCheckDrawingActivity::class.java))
        }
    }
}