package com.example.fisherhouse

import android.util.Log
import okhttp3.*
import java.io.IOException
import java.util.concurrent.CountDownLatch

fun sendItemtoDatabase(productItem: ProductItem): String{

    var exitCode : String = ""

    var excel = setExcelSheet("Use",productItem)

    productItem.printObj("sending")

    Log.d("product", productItem.quantity.toString())
    Log.d("product", excel.CompleteLink())

    val request = Request.Builder()
        .url(excel.CompleteLink())
        .build()

    val client = OkHttpClient()

    val countdownLatch = CountDownLatch(1)
    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {

            Log.d("okHTTP", "FAILURE TO SEND PRODUCT TO DATABASE")

            exitCode = "FAIL"
            countdownLatch.countDown()
        }

        override fun onResponse(call: Call, response: Response) {

            Log.d("okHTTP", "PRODUCT SENT")
            exitCode = "SUCCESS"
            response.close()
            countdownLatch.countDown()
        }
    })

    countdownLatch.await()

    return exitCode

}

fun getquantity(productItem: ProductItem): String {
    if (productItem.typeOfInsert == ProductItem.databaseType.INSERT) {
        return productItem.quantity.toString()
    }
    else if (productItem.typeOfInsert == ProductItem.databaseType.REMOVE) {
        return (productItem.quantity * -1).toString()
    }

    return "-1"
}

fun setExcelSheet(choice: String, productItem: ProductItem): ExcelSheetClass {

    var sheet = ExcelSheetClass()

    var sheetlink = ""
    var barcode_entry = ""
    var name_entry = ""
    var quantity_entry = ""
    var day_entry = ""
    var month_entry = ""
    var year_entry = ""

    if (choice == "testing") {
        sheetlink = "https://docs.google.com/forms/d/1K3Nhyfw0Tm9cMpTZOzujg0AH4PqE39RwSrLuJbw2pyo/formResponse"

        barcode_entry = "entry.1566973724"
        name_entry = "entry.854602764"
        quantity_entry = "entry.465566673"
        day_entry = "entry.1727357826"
        month_entry = "entry.125465877"
        year_entry = "entry.887811136"


    } else if (choice == "Use") {
        sheetlink = "https://docs.google.com/forms/d/1VvFJM6IPdzWB8bJmXp3rzIBKzrLxT9pLokfnJJrlr8k/formResponse"

        barcode_entry = "entry.591841404"
        name_entry = "entry.448740826"
        quantity_entry = "entry.1034773119"
        day_entry = "entry.715869454"
        month_entry = "entry.1358195413"
        year_entry = "entry.429392709"
    }

    with(sheet) {
        prefilled_link = sheetlink

        setEntry(barcode_entry, productItem.barcode)
        setEntry(name_entry, productItem.name)
        setEntry(quantity_entry, getquantity(productItem))
        setEntry(day_entry, productItem.expDate.toString())
        setEntry(month_entry, productItem.expMonth.toString())
        setEntry(year_entry, productItem.expYear.toString())
    }

    return sheet
}