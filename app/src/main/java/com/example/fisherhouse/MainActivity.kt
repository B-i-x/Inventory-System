package com.example.fisherhouse

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    var users = Users()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginButton = findViewById<Button>(R.id.LoginButton)

        val username_editText = findViewById<EditText>(R.id.editUsername)
        val password_editText = findViewById<EditText>(R.id.editPassword)

        users.addUser("volunteer", "FHinAZ", "tucsonVA")
        users.addUser("admin", "admin", "kotlin.43")
        users.addUser("testing", "A", "A")

        loginButton.setOnClickListener {

            val username = username_editText.text.toString()
            val password = password_editText.text.toString()

            if (users.is_credentials_valid(username, password)) {
                Toast.makeText(this, "LOGIN SUCCESS", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, EntranceChooseActivity::class.java)
                startActivity(intent)
            }
        }
    }

}