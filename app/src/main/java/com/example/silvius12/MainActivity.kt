package com.example.silvius12

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvPlotName: TextView
    private lateinit var tvSelectedCrop: TextView
    private lateinit var tvSelectedSize: TextView

    private var plotName: String = ""

    private val crops = arrayOf(
        "Coffee", "Cocoa", "Maize", "Cassava",
        "Rice", "Banana", "Other"
    )

    private val sizes = arrayOf(
        "I'm not sure",
        "Less than 0.5 ha",
        "0.5 – 1 ha",
        "1 – 2 ha",
        "2 – 5 ha",
        "More than 5 ha"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvPlotName = findViewById(R.id.tvPlotName)
        tvSelectedCrop = findViewById(R.id.tvSelectedCrop)
        tvSelectedSize = findViewById(R.id.tvSelectedSize)

        // Plot name – input dialog
        findViewById<View>(R.id.rowPlotName).setOnClickListener {
            val input = EditText(this).apply {
                setText(plotName)
                hint = "e.g. North Field, Block A…"
                setSingleLine(true)
                setPadding(48, 32, 48, 32)
            }

            AlertDialog.Builder(this)
                .setTitle("Plot name")
                .setView(input)
                .setPositiveButton("Save") { _, _ ->
                    plotName = input.text.toString().trim()
                    tvPlotName.text = if (plotName.isEmpty()) "Plot name" else plotName
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        // Main crop picker
        findViewById<View>(R.id.rowMainCrop).setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Select main crop")
                .setItems(crops) { _, which ->
                    tvSelectedCrop.text = crops[which]
                }
                .show()
        }

        // Estimated size picker
        findViewById<View>(R.id.rowEstimatedSize).setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Estimated size")
                .setItems(sizes) { _, which ->
                    tvSelectedSize.text = sizes[which]
                }
                .show()
        }

        // Continue to next screen
        findViewById<View>(R.id.btnContinueToMap).setOnClickListener {
            val intent = Intent(this, ReadyToMapActivity::class.java)
            intent.putExtra("plot_name", plotName)
            intent.putExtra("crop", tvSelectedCrop.text.toString())
            intent.putExtra("size", tvSelectedSize.text.toString())
            startActivity(intent)
        }
    }
}