package com.example.whenandwhere

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.whenandwhere.databinding.ActivityTimeResultBinding
import java.text.SimpleDateFormat
import java.util.*

class TimeResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTimeResultBinding
    private val numPage = 2000 // 페이지 수
    private var scheduleList: List<ScheduleDto> = listOf()
    val currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 2 // 현재 월을 가져옴

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimeResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Member ID
        //val memberIds = intent.getParcelableArrayListExtra<MemberClass>("memberIds")

        // Current date information
        val currentCalendar = Calendar.getInstance()
        val currentYear = currentCalendar.get(Calendar.YEAR)
        val currentMonth = currentCalendar.get(Calendar.MONTH)
        val currentWeek = currentCalendar.get(Calendar.WEEK_OF_MONTH)

        // Generate week data for the current year
        val dataList = generateWeekDataForYear(currentYear)
        val viewPager: ViewPager2 = findViewById(R.id.viewpager)
        val indicatorText: TextView = findViewById(R.id.indicator_text)


//         scheduleList 초기화
        scheduleList = intent.getParcelableArrayListExtra<ScheduleDto>("scheduleList") ?: listOf()

        val pagerAdapter = MyPagerAdapter(this, dataList, scheduleList)
        viewPager.adapter = pagerAdapter


        // Find the initial position based on the current month and week
        val initialPosition = dataList.indexOfFirst { data ->
            val (monthString, weekString) = data.week.split(" ")
            val month = monthString.substringBefore("월").toInt() // "월" 앞의 문자열을 추출하여 정수로 변환
            val week = weekString.substringBefore("주차").toInt() // "주차" 앞의 문자열을 추출하여 정수로 변환
            month == currentMonth + 1 && week == currentWeek
        }

        viewPager.setCurrentItem(initialPosition, false) // 초기 위치 설정

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                indicatorText.text = pagerAdapter.getWeek(position)
            }
        })


        val backbutton = findViewById<ImageView>(R.id.arrowleft)
        backbutton.setOnClickListener {
            // PutExtra 전달 값 때문에 scheduletitle로 다시 이동해야함
            val intent = Intent(this, ScheduleTitle::class.java)
            startActivity(intent)
        }

        val nextbtn = findViewById<Button>(R.id.resultbutton)
        nextbtn.setOnClickListener {
            val intent = Intent(this, middleplace::class.java)
            startActivity(intent)
        }

    }

    private fun generateWeekDataForYear(year: Int): List<MyData> {
        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val weekDataList = mutableListOf<MyData>()


        for (month in 0 until 12) {
            calendar.set(year, month, 1)

            // Find the first Monday of the month
            var firstMonday = calendar.firstDayOfWeek
            while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                calendar.add(Calendar.DATE, 1)
                firstMonday++
            }

            var weekOfMonth = 1
            while (calendar.get(Calendar.MONTH) == month) {
                val formattedDate = "${year}-${month + 1}-${calendar.get(Calendar.DAY_OF_MONTH)}"
                val weekData = MyData("${month + 1}월 ${weekOfMonth}주차", "데이터 $formattedDate")

                weekDataList.add(weekData)
                calendar.add(Calendar.DATE, 7)

                // Increment week number only if the next week is still within the same month
                if (calendar.get(Calendar.MONTH) == month) {
                    weekOfMonth++
                }
            }
        }

        return weekDataList
    }

    //시간 결정 팝업
    private fun showCheckPopup(startTime: String, orangeCount: Int) {
        val dialogView =
            LayoutInflater.from(this).inflate(R.layout.time_result_check_popup, null)
        val alertDialogBuilder = AlertDialog.Builder(this).setView(dialogView)
        val alertDialog = alertDialogBuilder.create()

        val TimeTextView = dialogView.findViewById<TextView>(R.id.checktext)
        TimeTextView.text = "선택된 일정 : ${startTime}부터 ${orangeCount}시간"

        dialogView.findViewById<Button>(R.id.confirm).setOnClickListener {
            // 선택한 일정 저장

        }

        dialogView.findViewById<Button>(R.id.cancel).setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }
}
/*
                // 멤버 함수로 변경
                private fun updateBoxesColor(position: Int) {
                    /** 여기서 box를 계산해서 색을 칠해 */
                    val newPosition = position % numPage // 현재 페이지 인덱스 계산
                    val startBoxIndex = newPosition * 4 // 시작 상자의 인덱스
                    val endBoxIndex = startBoxIndex + 3 // 마지막 상자의 인덱스

                    // 시작 상자부터 마지막 상자까지 순회하면서 회색으로 변경
                    for (i in startBoxIndex..endBoxIndex) {
                        // i는 0부터 시작하기 때문에, 실제 뷰의 인덱스는 i + 1이 됩니다.
                        val boxView = binding.root.findViewWithTag<ImageView>("box_${i + 1}")
                        boxView?.setColorFilter(resources.getColor(R.color.gray))
                    }
                }
                private fun getData(
                    memberIds : List<MemberClass>,
                ){
                    // Retrofit 객체 생성
                    val jwt = HttpUtil().getJWTFromSharedPreference(this) ?: ""
                    val client = HttpUtil().createClient(jwt)
                    val retrofit = HttpUtil().createRetrofitWithHeader(client)

                    //데이터 정리
                    val data = mutableListOf<Any>()

                    lifecycleScope.launch(Dispatchers.IO){
                        val apiService = retrofit.create(ApiService::class.java)

                        /** member id 별로 데이터를 받아오는 것으로 했습니다!
                         * 제가 전체적으로 이해가 부족해서 많이 못해드렸네요ㅜ */
                        memberIds.forEach{user ->
                            val call = apiService.getSchedules(user.userId)

                            val response = call.execute()
                            if (response.isSuccessful) {
                                val responseData = response.body()
                                // 응답 데이터 로그
                                responseData?.let {
                                    Log.d("ApiTest", "유저 스케줄: ${it.data}")
                                    for(schedule in it.data){
                                        /** 여기서 중복되는 시간들을 조건으로 넣어서 조건 충족 시 data에 넣으면 될 것 같습니다! */
                                        data.add(ScheduleItem(schedule.id, schedule.title, schedule.detail, schedule.startTime, schedule.endTime))
                                    }

                                }
                                // 예: responseData를 TextView에 설정하거나, 다른 작업을 수행할 수 있습니다.
                            } else {
                                // 요청 실패 처리
                                Log.d("ERRR", "실패")
                            }
                        }
                    }
                }

    private fun updateBoxesColor(position: Int) {
        val newPosition = position % numPage // 현재 페이지 인덱스 계산
        val startBoxIndex = newPosition * 4 // 시작 상자의 인덱스
        val endBoxIndex = startBoxIndex + 3 // 마지막 상자의 인덱스

        // 시작 상자부터 마지막 상자까지 순회하면서 회색으로 변경
        for (i in startBoxIndex..endBoxIndex) {
            // i는 0부터 시작하기 때문에, 실제 뷰의 인덱스는 i + 1이 됩니다.
            val boxView = binding.root.findViewWithTag<ImageView>("box_${i + 1}")
            boxView?.let {
                // Set the color of the box to orange (or any other color)
                it.setColorFilter(resources.getColor(R.color.gray))
                // Set an onClickListener to show the popup
                it.setOnClickListener {
                    showCheckPopup()
                }
            }
        }
    }
    */