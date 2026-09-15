package com.example.silvius12

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

/**
 * "Your first plot" - step 2 of 2. Sits between "How big is your plot?" and
 * the mapping flow, and carries the method chosen on the previous screen so
 * "Continue to map" opens the walking or the drawing flow.
 */
class YourFirstPlotActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_METHOD = "method"
        const val METHOD_WALK = "walk"
        const val METHOD_DRAW = "draw"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_your_first_plot)

        val method = intent.getStringExtra(EXTRA_METHOD) ?: METHOD_WALK

        findViewById<View>(R.id.btnContinueToMap).setOnClickListener {
            val next = if (method == METHOD_DRAW) {
                BeforeYouDrawActivity::class.java
            } else {
                WalkBeforeYouStartActivity::class.java
            }
            startActivity(Intent(this, next))
        }
    }
}
