package com.example.whenandwhere

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class memberAdd : AppCompatActivity() {
    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_add)

        val rectangle3Button = findViewById<Button>(R.id.sharelink)
        rectangle3Button.isEnabled = false

        val back : ImageView = findViewById(R.id.arrowleft)
        val shareLink : Button = findViewById(R.id.sharelink)

        // 뒤로가기 버튼
        back.setOnClickListener {
            val intent = Intent(this, NewGroupMake::class.java)
            startActivity(intent)
        }
        // 링크 복사 또는 공유 버튼
        shareLink.setOnClickListener {
            // 카카오 api 사용
        }

    }
}