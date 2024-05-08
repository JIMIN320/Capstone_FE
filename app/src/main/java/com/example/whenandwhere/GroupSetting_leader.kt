package com.example.whenandwhere

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class GroupSetting_leader : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_setting_leader)

        val back: ImageView = findViewById(R.id.arrowleft)
        val addMemberButton : Button = findViewById(R.id.addMemberText)
        val removeMemberButton : Button = findViewById(R.id.exitMemberText)
        val exitGroupButton : Button = findViewById(R.id.exitGroupText)
        val deleteGroupButton : Button = findViewById(R.id.removeGroupText)

        back.setOnClickListener{
            val intent = Intent(this, Grouphome::class.java)
            startActivity(intent)
        }
        addMemberButton.setOnClickListener {
            val intent = Intent(this, memberAdd::class.java)
            startActivity(intent)
        }

    }
}