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
    val currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 2 // 현재 월을 가져옴

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
                    updateMonthAndWeek(position) // 멤버 함수 호출
                    updateBoxesColor(position) // 멤버 함수 호출
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

        val nextbtn = findViewById<Button>(R.id.resultbutton)
        nextbtn.setOnClickListener {
            val intent = Intent(this, middleplace::class.java)
            startActivity(intent)
        }
    }

    // 멤버 함수로 변경
    private fun updateBoxesColor(position: Int) {
        val newPosition = position % numPage // 현재 페이지 인덱스 계산
        val startBoxIndex = newPosition * 4 // 시작 상자의 인덱스
        val endBoxIndex = startBoxIndex + 3 // 마지막 상자의 인덱스

        // 시작 상자부터 마지막 상자까지 순회하면서 오렌지 색상으로 변경
        for (i in startBoxIndex..endBoxIndex) {
            // i는 0부터 시작하기 때문에, 실제 뷰의 인덱스는 i + 1이 됩니다.
            val boxView = binding.root.findViewWithTag<ImageView>("box_${i + 1}")
            boxView?.setColorFilter(resources.getColor(R.color.main))
        }
    }

    // 멤버 함수로 변경
    private fun updateMonthAndWeek(position: Int) {
        val newPosition = position % numPage // 현재 페이지 인덱스 계산
        val newMonth = (currentMonth + (newPosition / 4)) % 12 // 현재 월 업데이트
        val newWeek = (newPosition % 4) + 1 // 현재 주차 업데이트

        // 업데이트된 월과 주차를 UI에 반영
        binding.indicatorText.text = "${newMonth + 1}월 ${newWeek}주차"
    }
}
