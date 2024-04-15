package com.example.whenandwhere

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whenandwhere.databinding.ActivityAiResultBinding
import com.example.whenandwhere.databinding.ActivityGrouplistBinding

class ai_result : AppCompatActivity() {
    private lateinit var binding: ActivityAiResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAiResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val courselist = arrayListOf(
            Courses("1","1","1","1","1","1","1","1","1"),
            Courses("1","1","1","1","1","1","1","1","1")
        )

        binding.course.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.course.adapter = CourseAdapter(courselist)

        val selectBtn : Button = findViewById(R.id.select)
        selectBtn.setOnClickListener{
            val intent = Intent(this,moimResult::class.java)
            startActivity(intent)
        }

    }
}