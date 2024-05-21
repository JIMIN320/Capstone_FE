package com.example.whenandwhere

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whenandwhere.databinding.ActivityAiResultBinding
import com.example.whenandwhere.databinding.ActivityGrouplistBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class ai_result : AppCompatActivity() {
    private lateinit var binding: ActivityAiResultBinding
    private var count: Int = 1 //추천 받은 횟수

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAiResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrofit 객체 생성
        val jwt = HttpUtil().getJWTFromSharedPreference(this) ?: ""
        val client = HttpUtil().createClient(jwt)
        val retrofit = HttpUtil().createAIRetrofitWithHeader(client)

        // 전달된 값을 받아옵니다
        val booleanValue = intent.getBooleanExtra("booleanValue", false) //음주여부
        val selectedPlace = intent.getStringExtra("SELECTED_PLACE")  //선택된 중간 장소
        val testPlace = "인천광역시 연수구"
        // 로그로 전달된 값을 확인합니다
        Log.d("ai_result", "Boolean 값: $booleanValue")
        Log.d("ai_result", "선택된 장소: $selectedPlace")

        binding.name1.text=""
        binding.name2.text = "밥먹자"

        val back: ImageView = findViewById(R.id.arrowleft)
        lifecycleScope.launch {
            val recommendPlace = recommendFunc(retrofit, testPlace, booleanValue)
            Log.d("AI_TEST" , "${recommendPlace.cafeObj?.name}")
        }

        back.setOnClickListener{
            val intent = Intent(this, middleplace::class.java)
            startActivity(intent)
        }

        val selectBtn : Button = findViewById(R.id.select)
        selectBtn.setOnClickListener{
            val intent = Intent(this,moimResult::class.java)
            startActivity(intent)
        }

        val retryBtn : Button = findViewById(R.id.retry)
        retryBtn.setOnClickListener {
            count++
            Log.d("ai_result","횟수: $count")
        }
    }

    private suspend fun recommendFunc(retrofit: Retrofit, address : String, isDrink : Boolean) : AIRecommend{
        return withContext(Dispatchers.IO) {
            val apiService = retrofit.create(ApiService::class.java)
            val call = apiService.aiRecommend(5,address, isDrink)

            val response = call.execute()
            if (response.isSuccessful) {
                val responseData = response.body()
                // 응답 데이터 로그
                responseData?.let {
                    Log.d("ApiTest", "AI_RESULT: ${it}")
                    if(it is AIResultDto){
                        return@withContext it.data
                    }
                    /*if (place in it) {
                        val resultList = arrayListOf<MemberClass>()
                        for (member in it.data) {
                            resultList.add(MemberClass(member.id, member.userId, member.nickname))
                        }
                        return@withContext resultList
                    }*/
                }
                // 예: responseData를 TextView에 설정하거나, 다른 작업을 수행할 수 있습니다.
            } else {
                // 요청 실패 처리
                Log.d("ERRR", "실패")
            }
            return@withContext AIRecommend(null, null, null)
        }
    }
}