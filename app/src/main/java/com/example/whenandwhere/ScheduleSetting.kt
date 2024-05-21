package com.example.whenandwhere

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
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

class ScheduleSetting : AppCompatActivity() {
    private lateinit var scheduleTitleText: TextView
    private lateinit var scheduleList: MutableList<ScheduleItem>
    private lateinit var selectedSchedule: ScheduleItem // 선택된 스케줄을 저장하는 변수
    private lateinit var recyclerView: RecyclerView
    private lateinit var deletePopup: ConstraintLayout
    private lateinit var newSlidingPanel: SlidingUpPanelLayout

    private lateinit var startYearPicker: NumberPicker
    private lateinit var startMonthPicker: NumberPicker
    private lateinit var startDayPicker: NumberPicker
    private lateinit var startHourPicker: NumberPicker
    private lateinit var startMinPicker: NumberPicker
    private lateinit var endYearPicker: NumberPicker
    private lateinit var endMonthPicker: NumberPicker
    private lateinit var endDayPicker: NumberPicker
    private lateinit var endHourPicker: NumberPicker
    private lateinit var endMinPicker: NumberPicker

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_setting)

        val intent = intent
        val userId = intent.getStringExtra("memberId")
        val userNick = intent.getStringExtra("memberNickname")
        Log.d("DATA", "${userId.toString()} $userNick")
        scheduleTitleText = findViewById(R.id.schedule_title)
        scheduleTitleText.text = "$userNick 의 일정"

        startYearPicker = findViewById(R.id.yearPicker_start)
        startMonthPicker = findViewById(R.id.monthPicker_start)
        startDayPicker = findViewById(R.id.daypicker_start)
        startHourPicker = findViewById(R.id.hourPicker_start)
        startMinPicker = findViewById(R.id.minPicker_start)
        endYearPicker = findViewById(R.id.yearPicker_end)
        endMonthPicker = findViewById(R.id.monthPicker_end)
        endDayPicker = findViewById(R.id.daypicker_end)
        endHourPicker = findViewById(R.id.hourPicker_end)
        endMinPicker = findViewById(R.id.minPicker_end)

        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val currentMonth = Calendar.getInstance().get(Calendar.MONTH)

        startYearPicker.minValue = 2000
        startYearPicker.maxValue = currentYear + 10
        startYearPicker.value = currentYear

        startMonthPicker.minValue = 1
        startMonthPicker.maxValue = 12
        startMonthPicker.value = currentMonth

        startDayPicker.minValue = 1
        startDayPicker.maxValue = 31

        startHourPicker.minValue = 0
        startHourPicker.maxValue = 23

        startMinPicker.minValue = 0
        startMinPicker.maxValue = 59

        endYearPicker.minValue = 2000
        endYearPicker.maxValue = currentYear + 10
        endYearPicker.value = currentYear

        endMonthPicker.minValue = 1
        endMonthPicker.maxValue = 12

        endDayPicker.minValue = 1
        endDayPicker.maxValue = 31

        endHourPicker.minValue = 0
        endHourPicker.maxValue = 23

        endMinPicker.minValue = 0
        endMinPicker.maxValue = 59

        // Retrofit 객체 생성
        val jwt = HttpUtil().getJWTFromSharedPreference(this) ?: ""
        val client = HttpUtil().createClient(jwt)
        val retrofit = HttpUtil().createRetrofitWithHeader(client)

        // api 실행 및 그룹 리스트 매핑시키기
        lifecycleScope.launch {
            val scheduleList = getSchedules(retrofit)
            Log.d("MemberList", "${scheduleList}")
        }

        // SlidingUpPanelLayout에 대한 참조를 가져옵니다.
        val slidingLayout = findViewById<SlidingUpPanelLayout>(R.id.main_frame)

        // 각 뷰에 대한 참조를 가져옵니다.
        val backButton = findViewById<ImageView>(R.id.arrowleft)
        val addScheduleButton = findViewById<AppCompatButton>(R.id.add_schedule)

        // 일정 추가에 대한 뷰들의 참조를 가져옵니다
        val addScheduleButtonInSlide = findViewById<TextView>(R.id.add)
        val titleInput = findViewById<EditText>(R.id.scheduletitle)
        val detailInput = findViewById<EditText>(R.id.scheduledetail)

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
        addScheduleButtonInSlide.setOnClickListener {
            val titleText = titleInput.text.toString()
            val detailText = detailInput.text.toString()
            val startTimeText = "${startYearPicker.value}-${startMonthPicker.value}-${startHourPicker.value}:00"
            val endTimeText = "${endYearPicker.value}-${endMonthPicker.value}-${endHourPicker.value}:00"

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
            val detailText = detailInput.text.toString()
            val startTimeText = "${startYearPicker.value}-${startMonthPicker.value}-${startHourPicker.value}:00"
            val endTimeText = "${endYearPicker.value}-${endMonthPicker.value}-${endHourPicker.value}:00"

            Log.d("Input Text", "$titleText $detailText $startTimeText $endTimeText")
            val newSchedule = ScheduleDto(0, titleText, detailText, startTimeText, endTimeText)
            addScheduleFunc(retrofit, newSchedule)
        }

        scheduleList = generateScheduleList().toMutableList()

        scheduleTitleText = findViewById(R.id.schedule_title)
        recyclerView = findViewById(R.id.schedule_recycler_view)

        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = ScheduleSettingAdapter(scheduleList) { scheduleItem ->
            showNewSlidingPanel(scheduleItem)
        }
        recyclerView.adapter = adapter



        val deleteScheduleButton = findViewById<Button>(R.id.delete_schedule_button)
        deleteScheduleButton.setOnClickListener {
            // 선택된 스케줄을 삭제 팝업에 설정합니다.
            showDeletePopup(selectedSchedule)
        }

        deletePopup = findViewById(R.id.delete_schedule_popup)
        val deleteConfirmButton = findViewById<Button>(R.id.delete_schedule_confirm)
        val deleteCancelButton = findViewById<Button>(R.id.delete_schedule_cancel)

        deleteConfirmButton.setOnClickListener {
            deleteSchedule(selectedSchedule)
            deletePopup.visibility = View.GONE
        }

        deleteCancelButton.setOnClickListener {
            deletePopup.visibility = View.GONE
        }

        materialCalendarView.setOnDateChangedListener { widget, date, selected ->
            selectWeek(widget, date)
        }
    }

    private fun showNewSlidingPanel(scheduleItem: ScheduleItem) {
        selectedSchedule = scheduleItem
        newSlidingPanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
    }


    private fun showDeletePopup(scheduleItem: ScheduleItem) {
        selectedSchedule = scheduleItem
        deletePopup.visibility = View.VISIBLE // 삭제 팝업을 보이도록 설정
        val deleteMessageText = deletePopup.findViewById<TextView>(R.id.delete_message_text)
        deleteMessageText.text = "${scheduleItem.scheduleText} 일정을 삭제하시겠습니까?"
    }

    private fun deleteSchedule(scheduleItem: ScheduleItem) {
        // 스케줄 목록에서 삭제
        scheduleList.remove(scheduleItem)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun generateScheduleList(): List<ScheduleItem> {
        val scheduleList = ArrayList<ScheduleItem>()
        for (i in 1..5) {
            val scheduleText = "${i}st_schedule"
            scheduleList.add(ScheduleItem(scheduleText))
        }
        return scheduleList
    }
}

    private fun generateScheduleList(): List<ScheduleItem> {
        val scheduleList = ArrayList<ScheduleItem>()
        for (i in 1..5) {
            val scheduleText = "${i}st_schedule"
            scheduleList.add(ScheduleItem(scheduleText))
        }
        return scheduleList
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
