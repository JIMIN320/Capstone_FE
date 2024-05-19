package com.example.whenandwhere

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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
            editplaceClass("홍길동", "", true),
            editplaceClass("김철수", "", false)
        )

        val adapter = editplaceAdapter(items)
        recyclerView.adapter = adapter
    }
}
