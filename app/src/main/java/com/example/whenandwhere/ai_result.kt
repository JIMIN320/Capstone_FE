package com.example.whenandwhere

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whenandwhere.databinding.ActivityAiResultBinding
import com.example.whenandwhere.databinding.ActivityGrouplistBinding

class ai_result : AppCompatActivity() {
    private lateinit var binding: ActivityAiResultBinding
    private var count: Int = 1 //추천 받은 횟수

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAiResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 전달된 값을 받아옵니다
        val booleanValue = intent.getBooleanExtra("booleanValue", false) //음주여부
        val selectedPlace = intent.getStringExtra("SELECTED_PLACE")  //선택된 중간 장소

        // 로그로 전달된 값을 확인합니다
        Log.d("ai_result", "Boolean 값: $booleanValue")
        Log.d("ai_result", "선택된 장소: $selectedPlace")

        val back: ImageView = findViewById(R.id.arrowleft)

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
}