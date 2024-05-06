package com.example.whenandwhere

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.whenandwhere.databinding.ActivityTimeResultBinding
import java.util.*

class TimeResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTimeResultBinding
    private lateinit var pagerAdapter: MyAdapter
    private val numPage = 2000 // 페이지 수
    private val currentMonth = Calendar.getInstance().get(Calendar.MONTH) // 현재 월을 가져옴

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimeResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ViewPager2 설정
        binding.viewpager.apply {
            pagerAdapter = MyAdapter(this@TimeResultActivity, numPage)
            adapter = pagerAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            offscreenPageLimit = 3
            setCurrentItem(1000, false)

            // 페이지 변경 이벤트 처리
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    // 현재 월과 주차 업데이트
                    updateMonthAndWeek(position)
                }
            })
        }

        // Indicator 설정
        updateMonthAndWeek(1000)

        val backbutton = findViewById<ImageView>(R.id.arrowleft)
        backbutton.setOnClickListener {
            val intent = Intent(this, EditPlace::class.java)
            startActivity(intent)
        }

        val middleplace = findViewById<Button>(R.id.resultbutton)
        middleplace.setOnClickListener {
            val intent = Intent(this, middleplace::class.java)
            startActivity(intent)
        }
    }

    // 현재 월과 주차 업데이트하는 함수
    private fun updateMonthAndWeek(position: Int) {
        val newPosition = position % numPage // 현재 페이지 인덱스 계산
        val newMonth = (currentMonth + (newPosition / 4)) % 12 // 현재 월 업데이트
        val newWeek = (newPosition % 4) + 1 // 현재 주차 업데이트

        // 업데이트된 월과 주차를 UI에 반영
        binding.indicatorText.text = "${newMonth + 1}월 ${newWeek}주차"
    }
}
