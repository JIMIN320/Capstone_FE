package com.example.whenandwhere

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import editplaceAdapter

class EditPlace : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_place)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)


        val resultButton = findViewById<Button>(R.id.resultbutton)
        resultButton.setOnClickListener {
            val intent = Intent(this, TimeResultActivity::class.java)
            startActivity(intent)
        }

        val arrowLeft = findViewById<ImageView>(R.id.arrowleft)
        arrowLeft.setOnClickListener {
            val intent = Intent(this, Grouphome::class.java)
            startActivity(intent)
        }
        val items = listOf(
            editplaceClass("홍길동", "출발지를 입력하세요", true),
            editplaceClass("김철수", "출발지를 입력하세요", false)
        )


        val adapter = editplaceAdapter(
            items,
            onInputButtonClickListener = { item ->
                // 주소 검색 액티비티 시작

            },
            onIconToggleClickListener = { item ->
                // 아이콘 상태 변경 시 추가 동작
            }
        )

        recyclerView.adapter = adapter
    }
}

