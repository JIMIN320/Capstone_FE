package com.example.whenandwhere

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class Grouphome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grouphome)

        val backButton = findViewById<ImageView>(R.id.arrowleft)
        val setBtn = findViewById<ImageView>(R.id.settings)
        val editScheduleButton: Button = findViewById(R.id.editschedulebutton)

        backButton.setOnClickListener {
            val intent = Intent(this, GrouplistActivity::class.java)
            startActivity(intent)
        }

        setBtn.setOnClickListener {
            val intent = Intent(this, GroupSetting_leader::class.java)
            startActivity(intent)}

        editScheduleButton.setOnClickListener {
            val intent = Intent(this, ScheduleSetting::class.java)
            startActivity(intent)

        }
    }
}