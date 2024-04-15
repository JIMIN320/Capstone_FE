package com.example.whenandwhere

import android.content.Intent
import android.os.Bundle
import android.widget.CalendarView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import java.util.*

class ScheduleSetting : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_setting)

        // 각 뷰에 대한 참조를 가져옵니다.
        val backButton = findViewById<ImageView>(R.id.arrowleft)
        val selectWeekButton = findViewById<AppCompatButton>(R.id.select_week)
        val firstScheduleButton = findViewById<AppCompatButton>(R.id.first_schedule)
        val secondScheduleButton = findViewById<AppCompatButton>(R.id.second_schedule)
        val addScheduleButton = findViewById<AppCompatButton>(R.id.add_schedule)

        // 각 뷰에 대한 클릭 리스너를 설정합니다.
        backButton.setOnClickListener {
            val intent = Intent(this, Grouphome::class.java)
            startActivity(intent)
        }

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)

        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        calendarView.date = calendar.timeInMillis // 현재 날짜로 설정

        selectWeekButton.setOnClickListener {
            // 주 선택 버튼을 클릭했을 때 수행할 작업을 여기에 추가합니다.
        }

        firstScheduleButton.setOnClickListener {
            // 첫 번째 일정 버튼을 클릭했을 때 수행할 작업을 여기에 추가합니다.
        }

        secondScheduleButton.setOnClickListener {
            // 두 번째 일정 버튼을 클릭했을 때 수행할 작업을 여기에 추가합니다.
        }

        addScheduleButton.setOnClickListener {
            // 일정 추가 버튼을 클릭했을 때 수행할 작업을 여기에 추가합니다.
        }
    }
}