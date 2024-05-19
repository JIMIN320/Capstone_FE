package com.example.whenandwhere

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.whenandwhere.databinding.ActivityMiddleplaceBinding

class middleplace : AppCompatActivity() {
    private lateinit var binding: ActivityMiddleplaceBinding
    private var alertDialog: AlertDialog? = null // AlertDialog 인스턴스를 저장할 변수 추가

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMiddleplaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val back: ImageView = findViewById(R.id.arrowleft)
        back.setOnClickListener {
            val intent = Intent(this, EditPlace::class.java)
            startActivity(intent)
        }
        R.id.place3

        binding.finish.setOnClickListener {
            val placesRadioGroup: RadioGroup = findViewById(R.id.places)
            val selectedId = placesRadioGroup.checkedRadioButtonId
            if (selectedId != -1) {
                val selectedRadioButton: RadioButton = findViewById(selectedId)
                val selectedText = selectedRadioButton.text.toString()
                showAlcoholDialog(selectedText)
            } else {
                // 라디오 버튼이 선택되지 않은 경우 처리
                Toast.makeText(this, "장소를 선택해주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showAlcoholDialog(selectedPlace: String) {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.alcohol, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)

        alertDialog = mBuilder.show()

        val yesBtn = mDialogView.findViewById<RadioButton>(R.id.radioButton5)
        yesBtn.setOnClickListener {
            alertDialog?.dismiss() // 다이얼로그 닫기
            moveToNextActivityWithPlace(selectedPlace, true) // 다음 액티비티로 이동하면서 선택된 장소와 true 값을 전달
        }

        val noBtn = mDialogView.findViewById<RadioButton>(R.id.radioButton6)
        noBtn.setOnClickListener {
            alertDialog?.dismiss() // 다이얼로그 닫기
            moveToNextActivityWithPlace(selectedPlace, false) // 다음 액티비티로 이동하면서 선택된 장소와 false 값을 전달
        }
    }

    // 선택된 장소 텍스트와 boolean 값을 다음 액티비티로 전달하는 함수
    private fun moveToNextActivityWithPlace(selectedPlace: String, value: Boolean) {
        val intent = Intent(this, ai_result::class.java).apply {
            putExtra("SELECTED_PLACE", selectedPlace) // 선택된 장소 텍스트를 인텐트에 추가
            putExtra("booleanValue", value) // boolean 값을 인텐트에 추가
        }
        startActivity(intent) // 다음 액티비티로 이동
    }
}
