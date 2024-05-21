package com.example.whenandwhere

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class EditPlace : AppCompatActivity() {
    val editPlaceList = ArrayList<editplaceClass>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_place)

        val memberList = intent.getStringArrayListExtra("memberNicknameList")
        val memberIdList = intent.getIntegerArrayListExtra("memberIdList")

        val resultButton = findViewById<Button>(R.id.resultbutton)
        resultButton.setOnClickListener {
            val intent = Intent(this, TimeResultActivity::class.java)
            startActivity(intent)
        }

        val arrowLeft = findViewById<ImageView>(R.id.arrowleft)
        arrowLeft.setOnClickListener {
            val intent = Intent(this, ScheduleTitle::class.java)
            startActivity(intent)
        }

        // api 실행 및 그룹 리스트 매핑시키기
        lifecycleScope.launch {
            if (!memberList.isNullOrEmpty()) {
                for (member in memberList) {
                    editPlaceList.add(editplaceClass(member,"", true))
                }
            }
            val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
            recyclerView.layoutManager = LinearLayoutManager(this@EditPlace)
            val adapter = editplaceAdapter(editPlaceList)
            recyclerView.adapter = adapter

        }
    }
}
