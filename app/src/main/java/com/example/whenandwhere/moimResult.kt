package com.example.whenandwhere

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class moimResult : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moim_result)

        val backbtn = findViewById<ImageView>(R.id.arrowleft)
        backbtn.setOnClickListener {
            val intent = Intent(this, Grouphome::class.java)
            startActivity(intent)
        }
    }
}