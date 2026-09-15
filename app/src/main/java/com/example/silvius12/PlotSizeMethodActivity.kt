package com.example.silvius12

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

/**
 * "How big is your plot?"
 *
 * Size tiles are a toggle: bg_tile_teal_dark (selected) / bg_tile_teal_medium
 * (unselected). The two method tiles are the screen's actions — there is no
 * Continue button; tapping one starts that flow straight away.
 */
class PlotSizeMethodActivity : AppCompatActivity() {

    private var selectedSize: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_plot_size_method)

        val tile4ha = findViewById<View>(R.id.tile4haOrLess)
        val tileMore = findViewById<View>(R.id.tileMoreThan4ha)

        selectSize(tile4ha)

        tile4ha?.setOnClickListener { selectSize(it) }
        tileMore?.setOnClickListener { selectSize(it) }

        findViewById<View>(R.id.tileWalkBoundary)?.setOnClickListener {
            openFirstPlot(YourFirstPlotActivity.METHOD_WALK)
        }
        findViewById<View>(R.id.tileDrawOnMap)?.setOnClickListener {
            openFirstPlot(YourFirstPlotActivity.METHOD_DRAW)
        }
    }


    private fun openFirstPlot(method: String) {
        startActivity(
            Intent(this, YourFirstPlotActivity::class.java)
                .putExtra(YourFirstPlotActivity.EXTRA_METHOD, method)
        )
    }

    private fun selectSize(tile: View?) {
        if (tile == null) return
        // Unselected → mid teal; selected → deep teal. Both stay solid so the
        // white icon + label read on either state.
        selectedSize?.background =
            ContextCompat.getDrawable(this, R.drawable.bg_tile_teal_medium)
        tile.background =
            ContextCompat.getDrawable(this, R.drawable.bg_tile_teal_dark)
        selectedSize = tile
    }
}
