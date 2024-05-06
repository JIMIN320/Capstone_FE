package com.example.whenandwhere

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class ScheduleTitle : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_title)

        val backbutton = findViewById<ImageView>(R.id.arrowleft2)
        backbutton.setOnClickListener {
            val intent = Intent(this, Grouphome::class.java)
            startActivity(intent)
        }

        val member1 = findViewById<Button>(R.id.button2)
        member1.setOnClickListener {
            val intent = Intent(this, ScheduleSetting::class.java)
            startActivity(intent)
        }

        val member2 = findViewById<Button>(R.id.button3)
        member1.setOnClickListener {
            val intent = Intent(this, ScheduleSetting::class.java)
            startActivity(intent)
        }

        val resultButton = findViewById<Button>(R.id.resultbutton3)
        resultButton.setOnClickListener {
            val intent = Intent(this, EditPlace::class.java)
            startActivity(intent)
        }

    }
}