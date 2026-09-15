// DrawBeforeYouDrawActivity.kt
package com.example.silvius12

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class DrawBeforeYouDrawActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_before_you_draw)
        findViewById<View>(R.id.btnOpenMap).setOnClickListener {
            startActivity(Intent(this, DrawMarkCornersActivity::class.java))
        }
    }
}