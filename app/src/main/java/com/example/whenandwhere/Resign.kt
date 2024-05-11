package com.example.whenandwhere

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Resign : AppCompatActivity(), resignAdapter.ButtonClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: resignAdapter
    private var itemList = mutableListOf<resignClass>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resign)

        val backButton = findViewById<ImageView>(R.id.arrowleft)
        backButton.setOnClickListener {
            val intent = Intent(this, GroupSetting_leader::class.java)
            startActivity(intent)
        }

        // RecyclerView 초기화
        recyclerView = findViewById(R.id.memberlist)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 어댑터 초기화
        adapter = resignAdapter(itemList, this)
        recyclerView.adapter = adapter

        // 데이터 추가 예시
        itemList.add(resignClass(1,"Item 1"))
        itemList.add(resignClass(12,"Item 2"))
        itemList.add(resignClass(2,"Item 3"))
        adapter.notifyDataSetChanged()
    }

    override fun onButtonClick(position: Int) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.resign_popup, null)
        val alertDialogBuilder = AlertDialog.Builder(this).setView(dialogView)
        val alertDialog = alertDialogBuilder.create()

        val confirmTextView = dialogView.findViewById<TextView>(R.id.confirmTextView)
        confirmTextView.text = "정말 내보내시겠습니까?"

        // Positive 버튼 클릭 리스너 설정
        dialogView.findViewById<Button>(R.id.yesButton).setOnClickListener {
            alertDialog.dismiss()
            // "예" 버튼을 클릭할 때 수행할 작업 추가

            // 아이템 삭제
            itemList.removeAt(position)
            adapter.notifyItemRemoved(position)
        }

        // Negative 버튼 클릭 리스너 설정
        dialogView.findViewById<Button>(R.id.noButton).setOnClickListener {
            alertDialog.dismiss()
            // "아니요" 버튼을 클릭할 때 수행할 작업 추가
        }

        // AlertDialog를 화면에 표시
        alertDialog.show()
    }
}