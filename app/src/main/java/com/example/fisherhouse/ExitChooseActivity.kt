package com.example.fisherhouse

import android.content.Intent
import android.database.sqlite.SQLiteCantOpenDatabaseException
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.client.android.Intents

class ExitChooseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exit_choose)
    }

    fun startScanBarcodeActivity() {
        val intent = Intent(this, ScanBarcodeActivity::class.java)
        startActivity(intent)
    }

}




