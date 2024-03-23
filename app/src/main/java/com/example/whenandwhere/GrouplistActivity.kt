package com.example.whenandwhere

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
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

        val spanCount = 1// 가로 아이템 수
        val horizontalSpacing = resources.getDimensionPixelSize(R.dimen.horizontal_spacing)
        val verticalSpacing = resources.getDimensionPixelSize(R.dimen.vertical_spacing)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = GroupAdapter(this@GrouplistActivity, groups) // 수정된 부분
        binding.recyclerView.adapter = adapter

        val makeBtn = findViewById<Button>(R.id.makemoim)
        makeBtn.setOnClickListener {
            val intent = Intent(this, NewGroupMake::class.java)
            startActivityForResult(intent, ADD_GROUP_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_GROUP_REQUEST && resultCode == Activity.RESULT_OK) {
            val groupName = data?.getStringExtra("groupName")
            val groupTheme = data?.getStringExtra("groupTheme")
            groups.add(Groups(groupName, groupTheme))
            binding.recyclerView.adapter?.notifyDataSetChanged()
        }
    }
}
