package com.example.whenandwhere

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class ScheduleTitle : AppCompatActivity() {
    private lateinit var backbutton: ImageView
    private var scheduleList = mutableListOf<scheduleClass>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_title)

        backbutton = findViewById(R.id.arrowleft2)

        // Retrofit 객체 생성
        val jwt = HttpUtil().getJWTFromSharedPreference(this) ?: ""
        val client = HttpUtil().createClient(jwt)
        val retrofit = HttpUtil().createRetrofitWithHeader(client)

        // RecyclerView 설정
        val recyclerView: RecyclerView = findViewById(R.id.list)
        val adapter = ScheduleAdapter(this, scheduleList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // api 실행 및 그룹 리스트 매핑시키기
        lifecycleScope.launch {
            val memberList = getMembers(retrofit)
            for(member in memberList){
                if(member.id != null && member.nickname != null){
                    scheduleList.add(scheduleClass(member.id, member.nickname))
                }
            }
            adapter.notifyDataSetChanged()
        }

        backbutton.setOnClickListener {
            val intent = Intent(this, Grouphome::class.java)
            startActivity(intent)
        }


        val resultButton = findViewById<Button>(R.id.resultbutton3)
        resultButton.setOnClickListener {
            val intent = Intent(this, EditPlace::class.java)
            startActivity(intent)
        }
    }

    private suspend fun getMembers(retrofit: Retrofit): ArrayList<Members> {
        return withContext(Dispatchers.IO) {

            val groupId = HttpUtil().getCurrentGroupIdFromSharedPreference(this@ScheduleTitle)
            val apiService = retrofit.create(ApiService::class.java)
            val call = apiService.getGroupMembers(groupId)

            val response = call.execute()
            if (response.isSuccessful) {
                val responseData = response.body()
                // 응답 데이터 로그
                responseData?.let {
                    Log.d("ApiTest", "그룹 멤버: ${it.data}")
                    if (it.data.isNotEmpty()) {
                        val resultList = arrayListOf<Members>()
                        for (member in it.data) {
                            resultList.add(Members(member.id, member.userId, member.nickname))
                        }
                        return@withContext resultList
                    }
                }
                // 예: responseData를 TextView에 설정하거나, 다른 작업을 수행할 수 있습니다.
            } else {
                // 요청 실패 처리
                Log.d("ERRR", "실패")
            }
            return@withContext ArrayList()
        }
    }
}