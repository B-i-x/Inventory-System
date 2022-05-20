package com.example.fisherhouse

import android.accessibilityservice.GestureDescription
import android.util.Log

class ProductItem {

    init {
        //default construction
    }
    var barcode : String = "UNK"
    var name : String = "UNK"
    var quantity: Int = -1

    var expMonth: Int = 13
    var expDate: Int = 32
    var expYear: Int = 1900

    enum class databaseType {INSERT, REMOVE}

    var typeOfInsert = databaseType.INSERT
    //default

    fun printObj(tag: String) {
        Log.d(tag, "barcode:$barcode name:$name quantity:$quantity date:$expDate month:$expMonth year:$expYear type:$typeOfInsert")
    }



}