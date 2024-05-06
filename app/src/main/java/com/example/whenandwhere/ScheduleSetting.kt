package com.example.whenandwhere

import android.content.Intent
import android.os.Bundle
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import java.util.*

class ScheduleSetting : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_setting)

        // SlidingUpPanelLayout에 대한 참조를 가져옵니다.
        val slidingLayout = findViewById<SlidingUpPanelLayout>(R.id.main_frame)

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
        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        calendarView.date = calendar.timeInMillis // 현재 날짜로 설정

        // '일정 추가하기' 버튼을 클릭하면 슬라이딩 패널이 확장됩니다.
        addScheduleButton.setOnClickListener {
            slidingLayout.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
        }

        // 다른 버튼의 클릭 리스너 예시
        selectWeekButton.setOnClickListener {
            // 주 선택 버튼을 클릭했을 때 수행할 작업
        }

        firstScheduleButton.setOnClickListener {
            // 첫 번째 일정 버튼을 클릭했을 때 수행할 작업
        }

        secondScheduleButton.setOnClickListener {
            // 두 번째 일정 버튼을 클릭했을 때 수행할 작업
        }

        val cancelTextView = findViewById<TextView>(R.id.cancle)
        cancelTextView.setOnClickListener {
            slidingLayout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
        }
    }
}