package com.example.whenandwhere

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.CalendarView
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.lifecycleScope
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.*

class ScheduleSetting : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_setting)

        // Retrofit 객체 생성
        val jwt = HttpUtil().getJWTFromSharedPreference(this) ?: ""
        val client = HttpUtil().createClient(jwt)
        val retrofit = HttpUtil().createRetrofitWithHeader(client)

        // api 실행 및 그룹 리스트 매핑시키기
        lifecycleScope.launch{
            val scheduleList = getSchedules(retrofit)
            Log.d("MemberList" , "${scheduleList}")
        }

        // SlidingUpPanelLayout에 대한 참조를 가져옵니다.
        val slidingLayout = findViewById<SlidingUpPanelLayout>(R.id.main_frame)

        // 각 뷰에 대한 참조를 가져옵니다.
        val backButton = findViewById<ImageView>(R.id.arrowleft)
        val selectWeekButton = findViewById<AppCompatButton>(R.id.select_week)
        val firstScheduleButton = findViewById<AppCompatButton>(R.id.first_schedule)
        val secondScheduleButton = findViewById<AppCompatButton>(R.id.second_schedule)
        val addScheduleButton = findViewById<AppCompatButton>(R.id.add_schedule)

        // 일정 추가에 대한 뷰들의 참조를 가져옵니다
        val addSdheduleButtonInSlide = findViewById<TextView>(R.id.add)
        val titleInput = findViewById<EditText>(R.id.schedulename)
        val detailInput = "세부사항에 대한 INPUT이 있어야 함"
        val startYearInput = findViewById<EditText>(R.id.startyearinput)
        val startHourInput = findViewById<EditText>(R.id.starthourinput)
        val endYearInput = findViewById<EditText>(R.id.endyearinput)
        val endHourInput = findViewById<EditText>(R.id.endhourinput)

        // 각 뷰에 대한 클릭 리스너를 설정합니다.
        backButton.setOnClickListener {
            val intent = Intent(this, ScheduleTitle::class.java)
            startActivity(intent)
        }

        val calendar = Calendar.getInstance()
        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        calendarView.date = calendar.timeInMillis // 현재 날짜로 설정

        // '일정 추가하기' 버튼을 클릭하면 슬라이딩 패널이 확장됩니다.
        addScheduleButton.setOnClickListener {
            slidingLayout.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
        }

        // 슬라이드 안의 실제 데이터 추가하기 버튼
        addSdheduleButtonInSlide.setOnClickListener {
            val titleText = titleInput.text.toString()
            val detailText = detailInput
            val startTimeText = "${startYearInput.text} ${startHourInput.text}:00"
            val endTimeText = "${endYearInput.text} ${endHourInput.text}:00"

            Log.d("Input Text", "$titleText $detailText $startTimeText $endTimeText")
            val newSchedule = ScheduleDto(0, titleText, detailText, startTimeText, endTimeText)
            addScheduleFunc(retrofit, newSchedule)
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

    private suspend fun getSchedules(retrofit: Retrofit) : ArrayList<Schedules> {
        return withContext(Dispatchers.IO) {

            val apiService = retrofit.create(ApiService::class.java)
            val call = apiService.getSchedules()

            val response = call.execute()
            if (response.isSuccessful) {
                val responseData = response.body()
                // 응답 데이터 로그
                responseData?.let {
                    Log.d("ApiTest", "유저 스케줄: ${it.data}")
                    val resultList = arrayListOf<Schedules>()
                    for(schedule in it.data){
                        resultList.add(Schedules(schedule.id, schedule.title, schedule.detail, schedule.startTime, schedule.endTime))
                    }

                    return@withContext resultList
                }
                // 예: responseData를 TextView에 설정하거나, 다른 작업을 수행할 수 있습니다.
            } else {
                // 요청 실패 처리
                Log.d("ERRR", "실패")
            }
            return@withContext ArrayList()
        }
    }

    private fun addScheduleFunc(retrofit: Retrofit, scheduleDto : ScheduleDto){
        // api 요청
        val apiService = retrofit.create(ApiService::class.java)
        val call = apiService.addSchedule(scheduleDto)

        // Validation
        if(scheduleDto.title.equals("") || scheduleDto.title == null){
            Log.d("Validation", "title NULL")
            return
        }
        else if(scheduleDto.detail.equals("") || scheduleDto.detail == null){
            Log.d("Validation", "detail NULL")
            return
        }
        else if(scheduleDto.startTime.equals("") || scheduleDto.startTime == null){
            Log.d("Validation", "startTime NULL")
            return
        }
        else if(scheduleDto.endTime.equals("") || scheduleDto.endTime == null){
            Log.d("Validation", "endTime NULL")
            return
        }
        call.enqueue(object : Callback<ObjectDto> {
            override fun onResponse(call: Call<ObjectDto>, response: Response<ObjectDto>) {
                if (response.code() == 201) {
                    Log.d("ApiTest", "스케줄 처리 여부: ${response.code()}")
                    // 예: responseData를 TextView에 설정하거나, 다른 작업을 수행할 수 있습니다.
                } else {
                    // 요청 실패 처리
                    Log.d("ERRR", "실패")
                }
            }
            override fun onFailure(call: Call<ObjectDto>, t: Throwable) {
                Log.d("ERRR", "에러 이유 : $t")
                // 네트워크 오류 처리
            }
        })
    }
}