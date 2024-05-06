package com.example.whenandwhere

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class GroupSetting_leader : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_setting_leader)

        val back: ImageView = findViewById(R.id.arrowleft)

        back.setOnClickListener{
            val intent = Intent(this, Grouphome::class.java)
            startActivity(intent)
        }

    }
}