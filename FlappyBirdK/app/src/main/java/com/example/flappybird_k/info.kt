package com.example.flappybird_k

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class info : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        val btn_info = findViewById<ImageButton>(R.id.btn_back)
        btn_info.setOnClickListener { // Create an Intent to start SecondActivity
            val intent = Intent(this@info, MainActivity::class.java)
            startActivity(intent)
        }
    }
}