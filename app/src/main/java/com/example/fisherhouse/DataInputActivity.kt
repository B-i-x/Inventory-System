package com.example.fisherhouse

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity


class DataInputActivity : AppCompatActivity() {

    private val product = ProductItem()
    private var counter : Int = 0

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_input)

        supportActionBar?.hide()

        val barcode = intent.getStringExtra("barcode")

        product.barcode = barcode.toString()

        val name : String = lookupBarcode(barcode.toString())

        //val test_name = "TEST"

        Log.d("DataInputActivity", "received name as $name")
        setName(name)

        setupNumberPicker()

        setupExpirationDate()

        setItemToSend()

    }

    private fun setItemToSend() {

        product.printObj("DataInputActivity")
        val sendButton = findViewById<Button>(R.id.sendButton)

        sendButton.setOnClickListener {
            val code = sendItemtoDatabase(product)

            if (code == "SUCCESS") {
                changeToExitChooseActivity(code)
            }
            else {
                Toast.makeText(this, "FAILED TO SEND TO DATABASE", Toast.LENGTH_SHORT).show()}
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setupExpirationDate(){
        val c = Calendar.getInstance(TimeZone.getTimeZone("GMT"))
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val textExp = findViewById<TextView>(R.id.tvExpDate)

        textExp.setOnClickListener {
            val dpd = DatePickerDialog(this,  R.style.MyDatePickerStyle,
                { view, year, month, DayofMonth ->
                    textExp.text = "$DayofMonth/${month + 1}/$year"

                    product.expDate = DayofMonth
                    product.expMonth = month + 1
                    product.expYear = year
                },year, month, day)

            dpd.show()
        }

    }


    private fun setupNumberPicker() {
        val numPicker = findViewById<NumberPicker>(R.id.quantityPicker)

        numPicker.minValue = 0
        numPicker.maxValue = 50

        numPicker.setOnValueChangedListener {picker, oldVal, newVal ->
            product.quantity = newVal
        }

        val typeOfInsertoDatabase = findViewById<ToggleButton>(R.id.toggleButton)

        typeOfInsertoDatabase.setOnClickListener {
            counter += 1

            if (counter % 2 == 1) {
                product.typeOfInsert = ProductItem.databaseType.REMOVE
            }
            if (counter % 2 == 0) {
                product.typeOfInsert = ProductItem.databaseType.INSERT
            }

            product.printObj("DataInputActivity")
        }
    }

    private fun setName(name: String) {

        findViewById<EditText>(R.id.editTextProductName).setText(name)

        product.name = name
    }

    fun changeToExitChooseActivity(code: String) {

        val intent = Intent(this, ExitChooseActivity::class.java).apply {
            putExtra("code", code)
        }
        startActivity(intent)

        finish()
    }

}

/*{
  "code": "string",
  "total": 0,
  "offset": 0,
  "items": [
    {
      "ean": "0885909950805",
      "title": "Apple iPhone 6, Space Gray, 64 GB (T-Mobile)",
      "upc": "885909950805",
      "gtin": "string",
      "asin": "B00NQGOZV0",
      "description": "iPhone 6 isn't just bigger - it's better...",
      "brand": "Apple",
      "model": "MG5A2LL/A",
      "dimension": "string",
      "weight": "string",
      "category": "Electronics > Communications > Telephony > Mobile Phones > Unlocked Mobile Phones",
      "currency": "string",
      "lowest_recorded_price": 3.79,
      "highest_recorded_price": 8500,
      "images": [
        "http://img1.r10.io/PIC/112231913/0/1/250/112231913.jpg"
      ],
      "offers": [
        {
          "merchant": "Newegg.com",
          "domain": "newegg.com",
          "title": "Apple iPhone 6 64GB T-Mobile Space Gray MG5A2LL/A",
          "currency": "string",
          "list_price": 0,
          "price": 1200,
          "shipping": "Free Shipping",
          "condition": "New",
          "availability": "Out of Stock",
          "link": "https://www.upcitemdb.com/norob/alink/?id=v2p2...",
          "updated_t": 1479243029
        }
      ]
    }
  ]
}*/

class UPCResponse(val items: Array<Product>)
class Product(val title: String)