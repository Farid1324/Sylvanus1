// DrawMappingCompleteActivity.kt
package com.example.silvius12

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class DrawMappingCompleteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // The draw flow has its own completion screen: the next steps are shown
        // on it, so Done ends the flow instead of opening "What happens next".
        setContentView(R.layout.screen_draw_mapping_complete)
        findViewById<View>(R.id.btnDone).setOnClickListener {
            finishAffinity()
        }
    }
}
