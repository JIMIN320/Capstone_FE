package com.example.whenandwhere

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class EditPlace : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_place)


        val resultButton = findViewById<Button>(R.id.resultbutton)

        val editButton4 = findViewById<Button>(R.id.editButton4)
        val savebutton = findViewById<Button>(R.id.savebutton)
        val arrowLeft = findViewById<ImageView>(R.id.arrowleft)
        val slidingUpPanelLayout = findViewById<SlidingUpPanelLayout>(R.id.main_frame)

        val transportationCar = findViewById<ImageButton>(R.id.Transportation_Car)
        val transportationBus = findViewById<ImageButton>(R.id.transportation_Bus)

        val titleTextView = findViewById<TextView>(R.id.title)




        // arrowLeft 클릭 시 동작 정의
        arrowLeft.setOnClickListener {
            val intent = Intent(this, Grouphome::class.java)
            startActivity(intent)
        }

        fun showInputDialog() {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("출발하는 곳 입력")

            val input = EditText(this)
            builder.setView(input)

            builder.setPositiveButton("확인") { dialog, _ ->
                val text = input.text.toString()
                // 여기서 입력된 텍스트를 사용할 수 있음
                dialog.dismiss()
            }

            builder.setNegativeButton("취소") { dialog, _ ->
                dialog.cancel()
            }

            builder.show()
        }

        editButton4.setOnClickListener {
            // editButton4을 클릭했을 때 수행할 작업을 여기에 작성
        }

        savebutton.setOnClickListener {
            // button을 클릭했을 때 수행할 작업을 여기에 작성
        }
        resultButton.setOnClickListener {
            val intent = Intent(this, TimeResultActivity::class.java)
            startActivity(intent)
        }
    }
}