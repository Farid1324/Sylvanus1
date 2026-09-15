package com.example.silvius12

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

/**
 * Uses: bg_header_teal, bg_card_white, bg_step_num_orange, bg_icon_chip,
 * ic_person, ic_home, ic_map_pin, ic_cloud, bg_status_chip_green, ic_wifi_off,
 * bg_btn_teal_pill (primary button).
 */
class StepsOverviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_steps_overview)

        findViewById<MaterialButton>(R.id.btnNext).setOnClickListener {
            startActivity(Intent(this, AboutYouOnboardingActivity::class.java))
        }
    }
}