package com.example.whenandwhere

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.*
import kotlin.collections.ArrayList


class ScheduleSetting : AppCompatActivity() {
    private lateinit var scheduleTitleText: TextView
    private lateinit var scheduleList: MutableList<ScheduleItem>
    private lateinit var selectedSchedule: ScheduleItem // 선택된 스케줄을 저장하는 변수
    private lateinit var recyclerView: RecyclerView
    private lateinit var deletePopup: View // 삭제 팝업

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_setting)

        // SlidingUpPanelLayout에 대한 참조를 가져옵니다.
        val slidingLayout = findViewById<SlidingUpPanelLayout>(R.id.main_frame)

        // 각 뷰에 대한 참조를 가져옵니다.
        val backButton = findViewById<ImageView>(R.id.arrowleft)
        val addScheduleButton = findViewById<AppCompatButton>(R.id.add_schedule)

        // 일정 추가에 대한 뷰들의 참조를 가져옵니다
        val addSdheduleButtonInSlide = findViewById<TextView>(R.id.add)
        val titleInput = findViewById<EditText>(R.id.schedulename)
        val detailInput = "세부사항에 대한 INPUT이 있어야 함"
        val startYearInput = findViewById<EditText>(R.id.startyearinput)
        val startHourInput = findViewById<EditText>(R.id.starthourinput)
        val endYearInput = findViewById<EditText>(R.id.endyearinput)
        val endHourInput = findViewById<EditText>(R.id.endhourinput)

        // 넘겨받은 데이터 및 매핑
        val intent = intent
        val userId = intent.getStringExtra("memberId")
        val userNick = intent.getStringExtra("memberNickname")
        Log.d("DATA", "${userId.toString()} $userNick")
        scheduleTitleText = findViewById(R.id.schedule_title)
        scheduleTitleText.text = "$userNick 의 일정"

        // Retrofit 객체 생성
        val jwt = HttpUtil().getJWTFromSharedPreference(this) ?: ""
        val client = HttpUtil().createClient(jwt)
        val retrofit = HttpUtil().createRetrofitWithHeader(client)

        // api 실행 및 그룹 리스트 매핑시키기
        lifecycleScope.launch {
            scheduleList = getSchedules(intent, retrofit)
            Log.d("ScheduleList", "${scheduleList}")
            val recyclerView = findViewById<RecyclerView>(R.id.schedule_recycler_view)
            recyclerView.layoutManager = LinearLayoutManager(this@ScheduleSetting)
            val adapter = ScheduleSettingAdapter(scheduleList) { scheduleItem ->
                showDeletePopup(scheduleItem)
            }
            recyclerView.adapter = adapter

        }

        // 각 뷰에 대한 클릭 리스너를 설정합니다.
        backButton.setOnClickListener {
            val intent = Intent(this, ScheduleTitle::class.java)
            startActivity(intent)
        }

        //customCalendarview(2줄)
        val materialCalendarView: MaterialCalendarView = findViewById(R.id.calendarView)
        materialCalendarView.setSelectedDate(CalendarDay.today())


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

        val cancelTextView = findViewById<TextView>(R.id.cancle)
        cancelTextView.setOnClickListener {
            slidingLayout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
        }

        val addTextView = findViewById<TextView>(R.id.add)
        addTextView.setOnClickListener {
            val titleText = titleInput.text.toString()
            val detailText = detailInput
            val startTimeText = "${startYearInput.text} ${startHourInput.text}:00"
            val endTimeText = "${endYearInput.text} ${endHourInput.text}:00"

            val newSchedule = ScheduleDto(0, titleText, detailText, startTimeText, endTimeText)

            addScheduleFunc(retrofit, newSchedule)
        }

        deletePopup = findViewById<View>(R.id.delete_schedule_popup)
        val deleteConfirmButton = findViewById<Button>(R.id.delete_schedule_confirm)
        val deleteCancelButton = findViewById<Button>(R.id.delete_schedule_cancel)

        deleteConfirmButton.setOnClickListener {
            deletePopup.visibility = View.GONE
            deleteSchedule(retrofit, selectedSchedule.id)
            val intent = (this as Activity).intent
            this.finish() //현재 액티비티 종료 실시
            this.startActivity(intent) //현재 액티비티 재실행 실시
        }

        deleteCancelButton.setOnClickListener {
            deletePopup.visibility = View.GONE
        }

        materialCalendarView.setOnDateChangedListener { widget, date, selected ->
            selectWeek(widget, date)
        }

    }

    private fun showDeletePopup(scheduleItem: ScheduleItem) {
        selectedSchedule = scheduleItem
        deletePopup.visibility = View.VISIBLE // 삭제 팝업을 보이도록 설정
    }
}
private fun selectWeek(widget: MaterialCalendarView, date: CalendarDay) {
    val calendar = Calendar.getInstance()
    calendar.set(date.year, date.month, date.day)
    val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1 // 일요일이 1이므로 0으로 만듦
    calendar.add(Calendar.DAY_OF_MONTH, -dayOfWeek)
    val startDate = calendar.time
    val endDate = Calendar.getInstance()
    endDate.time = startDate
    endDate.add(Calendar.DAY_OF_MONTH, 6) // 선택된 날짜로부터 6일 후까지의 날짜를 종료일로 설정

    widget.clearSelection()
    calendar.time = startDate
    while (calendar.time <= endDate.time) {
        widget.setDateSelected(CalendarDay.from(calendar), true)
        calendar.add(Calendar.DAY_OF_MONTH, 1)
    }
}

private suspend fun getSchedules(intent : Intent, retrofit: Retrofit) : ArrayList<ScheduleItem> {
    val userId = intent.getStringExtra("memberId") ?: ""
    return withContext(Dispatchers.IO) {

        val apiService = retrofit.create(ApiService::class.java)
        val call = apiService.getSchedules(userId)

        val response = call.execute()
        if (response.isSuccessful) {
            val responseData = response.body()
            // 응답 데이터 로그
            responseData?.let {
                Log.d("ApiTest", "유저 스케줄: ${it.data}")
                val resultList = arrayListOf<ScheduleItem>()
                for(schedule in it.data){
                    resultList.add(ScheduleItem(schedule.id, schedule.title, schedule.detail, schedule.startTime, schedule.endTime))
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

private fun deleteSchedule( retrofit: Retrofit, scheduleId : Int) {
    val apiService = retrofit.create(ApiService::class.java)
    val call = apiService.deleteSchedule(ScheduleDto(scheduleId, "", "" ,"" , ""))

    call.enqueue(object : Callback<ObjectDto> {
        override fun onResponse(call: Call<ObjectDto>, response: Response<ObjectDto>) {
            if (response.code() == 200) {
                Log.d("ApiTest", "스케줄 삭제 여부: ${response.code()}")
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