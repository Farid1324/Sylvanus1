package com.example.silvius12

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial

/**
 * Uses: ic_silvanus_mark, img_farm_field_illustration, bg_card_soft_teal,
 * ic_map_pin, ic_eye, ic_gps_strong, bg_btn_orange_pill, ic_clock, ic_wifi_off.
 */
class ReadyToMapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_ready_to_map)

        findViewById<SwitchMaterial>(R.id.switchAllowLocation)?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(this, R.string.label_allow_location, Toast.LENGTH_SHORT).show()
            }
        }

        // style Widget.Silvanus.Button.Orange → bg_btn_orange_pill + ic_gps_strong
        findViewById<MaterialButton>(R.id.btnMapFirstPlot).setOnClickListener {
            startActivity(Intent(this, PlotSizeMethodActivity::class.java))
        }
    }
}