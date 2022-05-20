package com.example.fisherhouse

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.zxing.client.android.Intents
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import java.io.DataInput


class ScanBarcodeActivity : AppCompatActivity() {

    private val product = ProductItem()

    private val barcodeLauncher = registerForActivityResult(
        ScanContract()

    ) { result: ScanIntentResult ->
        if (result.contents == null) {
            val originalIntent = result.originalIntent
            if (originalIntent == null) {
                Log.d("ScanBarcodeActivity", "Cancelled scan")
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else if (originalIntent.hasExtra(Intents.Scan.MISSING_CAMERA_PERMISSION)) {
                Log.d(
                    "ScanBarcodeActivity",
                    "Cancelled scan due to missing camera permission"
                )
                Toast.makeText(
                    this,
                    "Cancelled due to missing camera permission",
                    Toast.LENGTH_LONG
                ).show()
            }
        } else { //means that everything is good
            Log.d("ScanBarcodeActivity", "Scanned barcode as ${result.contents}")

            startDataInputActivity(result.contents)

        }
    }

    //^this stuff is literal black magic i just copied and pasted so dont mess with it
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("ScanBarcodeActivity", "Created")
        supportActionBar?.hide()

        val options = ScanOptions()
        options.setDesiredBarcodeFormats(setOf(ScanOptions.UPC_A, ScanOptions.UPC_E, ScanOptions.EAN_13, ScanOptions.EAN_8))
        options.setOrientationLocked(false)
        options.setBeepEnabled(true)

        barcodeLauncher.launch(options)

    }

    private fun startDataInputActivity(barcode: String) {
        val intent = Intent(this, DataInputActivity::class.java).apply {
            putExtra("barcode", barcode)
        }
        startActivity(intent)
    }

    // Get the Intent that started this activity and extract the string


}