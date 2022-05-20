package com.example.fisherhouse

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException
import java.util.concurrent.CountDownLatch


fun lookupBarcode(upc: String) : String{

    val link = "https://api.upcitemdb.com/prod/trial/lookup?upc=$upc"

    var nameOfProduct = NameofProduct()

    val request = Request.Builder()
        .url(link)
        .build()

    val client = OkHttpClient()

    val countdownLatch = CountDownLatch(1)
    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {

            Log.d("okHTTP", "FAILURE FAILURE FAILURE FAILURE FAILURE ")

            setName("Lookup Failed", nameOfProduct)

            countdownLatch.countDown()
        }

        override fun onResponse(call: Call, response: Response){

            Log.d("okHTTP", "SUCCESS In Looking Up")

            val bod = response.body?.string()

            val gson = GsonBuilder().create()

            val upcResponse = gson.fromJson(bod, UPCResponse::class.java)

            Log.d("okHTTP", upcResponse.items.toString())

            if (upcResponse.items.isEmpty()) {
                Log.d("okHTTP", "upc not in database")
                setName("NOT RECOGNIZED", nameOfProduct)
            }
            else {

                println(upcResponse.items[0].title)

                setName(upcResponse.items[0].title, nameOfProduct)

                Log.d("okHTTP", upcResponse.items[0].title)



            }

            response.close()
            countdownLatch.countDown()

        }
    })

    countdownLatch.await()

    Log.d("okHTTP", "final string is " + nameOfProduct.name)

    return nameOfProduct.name
}

fun setName(name: String, class_thing: NameofProduct) {
    class_thing.name = name
}

class NameofProduct(name: String = "ERROR") {
    var name: String = name
}