package com.example.whenandwhere

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.whenandwhere.databinding.ActivityTimeResultBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class TimeResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTimeResultBinding
    private lateinit var pagerAdapter: MyAdapter
    private var startDate = ""
    private var endDate = ""
    private val numPage = 2000 // 페이지 수
    val currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 2 // 현재 월을 가져옴

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimeResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //member id
        val memberIds = intent.getParcelableArrayListExtra<MemberClass>("memberIds")

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
                    /*
                    getData(memberIds?.toList() ?: emptyList())
                    /** 받아온 데이터를 updateBoxesColor로 넘겨 */
                    updateBoxesColor(position) // 멤버 함수 호출
                    */

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
    */
    // 멤버 함수로 변경
    private fun updateMonthAndWeek(position: Int) {
        val newPosition = position % numPage // 현재 페이지 인덱스 계산
        val newMonth = (currentMonth + (newPosition / 4)) % 12 // 현재 월 업데이트
        val newWeek = (newPosition % 4) + 1 // 현재 주차 업데이트

        startDate = "2024-${newMonth + 1}-{여기에 현재 주가 시작하는 날짜}"
        endDate = "2024-${newMonth + 1}-{여기에 현재 주가 끝나는 날짜}"
        // 업데이트된 월과 주차를 UI에 반영
        binding.indicatorText.text = "${newMonth + 1}월 ${newWeek}주차"
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


    //시간 결정 팝업
    private fun showCheckPopup() {
        val dialogView =
            LayoutInflater.from(this).inflate(R.layout.time_result_check_popup, null)
        val alertDialogBuilder = AlertDialog.Builder(this).setView(dialogView)
        val alertDialog = alertDialogBuilder.create()

        // Populating the dialog with schedule data if needed
        val TimeTextView = dialogView.findViewById<TextView>(R.id.checktext)
        TimeTextView.text = "선택한 일정 넣기"

        // Set up the confirm
        dialogView.findViewById<Button>(R.id.confirm).setOnClickListener {
            // Handle the confirm button click

        }

        dialogView.findViewById<Button>(R.id.cancel).setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()

    }
}
