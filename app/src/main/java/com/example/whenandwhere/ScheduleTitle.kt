package com.example.whenandwhere

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ScheduleTitle : AppCompatActivity() { // 반드시 액티비티에 맞게 수정하세요.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_title) // 반드시 레이아웃 파일에 맞게 수정하세요.

        val resultButton = findViewById<Button>(R.id.resultbutton3) // resultbutton3 버튼을 찾습니다.
        resultButton.setOnClickListener {
            // Intent를 사용하여 새로운 Activity를 시작합니다.
            val intent = Intent(this, EditPlace::class.java)
            startActivity(intent)
        }
    }
}