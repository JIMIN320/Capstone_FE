package com.example.whenandwhere

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Accept : AppCompatActivity(), acceptAdapter.ButtonClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: acceptAdapter
    private var itemList = mutableListOf<acceptClass>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accept)

        val backButton = findViewById<ImageView>(R.id.arrowleft)
        backButton.setOnClickListener{
            val intent = Intent(this, GroupSetting_leader::class.java)
            startActivity(intent)
        }

        // RecyclerView 초기화
        recyclerView = findViewById(R.id.acceptlist)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 어댑터 초기화
        adapter = acceptAdapter(itemList, this)
        recyclerView.adapter = adapter

        // 데이터 추가 예시
        itemList.add(acceptClass("Item 1"))
        itemList.add(acceptClass("Item 2"))
        itemList.add(acceptClass("Item 3"))
        adapter.notifyDataSetChanged()
    }

    override fun onButtonClick(position: Int) {
        // 버튼 클릭 시 진행될 작업

    }
}