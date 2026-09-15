// WalkYourFirstPlotActivity.kt
package com.example.silvius12

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class WalkYourFirstPlotActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_ready_to_map)
        findViewById<View>(R.id.btnMapFirstPlot).setOnClickListener {
            startActivity(Intent(this, WalkBeforeYouStartActivity::class.java))
        }
    }
}