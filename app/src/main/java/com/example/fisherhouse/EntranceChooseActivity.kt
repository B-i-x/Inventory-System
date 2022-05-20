package com.example.fisherhouse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class EntranceChooseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entrance_choose)
    }

    fun startScanBarcodeActivity(view: View) {
        val intent = Intent(this, ScanBarcodeActivity::class.java)
        startActivity(intent)
    }

}