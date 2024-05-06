package com.example.whenandwhere

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class EditPlace : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_place)

        val editButton1 = findViewById<Button>(R.id.editButton1)
        val editButton2 = findViewById<Button>(R.id.editButton2)

        val editButton4 = findViewById<Button>(R.id.editButton4)
        val button = findViewById<Button>(R.id.button)
        val arrowLeft = findViewById<ImageView>(R.id.arrowleft)
        val editButton3 = findViewById<Button>(R.id.editButton3)
        val slidingUpPanelLayout = findViewById<SlidingUpPanelLayout>(R.id.main_frame)

        val transportationCar = findViewById<ImageButton>(R.id.Transportation_Car)
        val transportationBus = findViewById<ImageButton>(R.id.transportation_Bus)
        val imageViewTrans1 = findViewById<ImageView>(R.id.imageView_trans1)
        val imageViewTrans2 = findViewById<ImageView>(R.id.imageView_trans2)

        editButton1.setOnClickListener {
            slidingUpPanelLayout.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
        }

        editButton2.setOnClickListener {
            slidingUpPanelLayout.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
        }

        // arrowLeft 클릭 시 동작 정의
        arrowLeft.setOnClickListener {
            val intent = Intent(this, Grouphome::class.java)
            startActivity(intent)
        }

        transportationCar.setOnClickListener {
            imageViewTrans1.setImageResource(R.drawable.car_orange)
            transportationCar.setImageResource(R.drawable.car_orange)
            transportationBus.setImageResource(R.drawable.bus_black)
        }

        transportationBus.setOnClickListener {
            imageViewTrans1.setImageResource(R.drawable.bus_orange)
            transportationCar.setImageResource(R.drawable.car_black)
            transportationBus.setImageResource(R.drawable.bus_orange)
        }

        transportationCar.setOnClickListener {
            imageViewTrans2.setImageResource(R.drawable.car_orange)
            transportationCar.setImageResource(R.drawable.car_orange)
            transportationBus.setImageResource(R.drawable.bus_black)
        }

        transportationBus.setOnClickListener {
            imageViewTrans2.setImageResource(R.drawable.bus_orange)
            transportationCar.setImageResource(R.drawable.car_black)
            transportationBus.setImageResource(R.drawable.bus_orange)
        }

        editButton3.setOnClickListener {

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

        button.setOnClickListener {
            // button을 클릭했을 때 수행할 작업을 여기에 작성

        }
    }
}