package com.example.whenandwhere

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whenandwhere.databinding.ActivityGrouplistBinding

class GrouplistActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGrouplistBinding
    private val ADD_GROUP_REQUEST = 1
    private val groups: ArrayList<Groups> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGrouplistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val grouplist = arrayListOf(
            Groups("하이파이브", "공적인 모임"),
            Groups("뭐시기", "어쩌구"),
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = GroupAdapter(this, grouplist) // 데이터 전달

        val makeBtn = findViewById<Button>(R.id.makemoim)
        makeBtn.setOnClickListener {
            val intent = Intent(this, NewGroupMake::class.java)
            startActivityForResult(intent, ADD_GROUP_REQUEST)
        }
    }
}
