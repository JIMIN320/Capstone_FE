package com.example.whenandwhere

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RadioButton
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

        back.setOnClickListener{
            val intent = Intent(this, EditPlace::class.java)
            startActivity(intent)
        }

        binding.finish.setOnClickListener{
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.alcohol,null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)

            // AlertDialog 인스턴스 생성 및 표시
            alertDialog = mBuilder.show()

            val yesBtn = mDialogView.findViewById<RadioButton>(R.id.radioButton5)
            yesBtn.setOnClickListener {
                alertDialog?.dismiss() // 다이얼로그 닫기
                moveToNextActivity(true) // 다음 액티비티로 이동하면서 true 값을 전달
            }

            val noBtn = mDialogView.findViewById<RadioButton>(R.id.radioButton6)
            noBtn.setOnClickListener {
                alertDialog?.dismiss() // 다이얼로그 닫기
                moveToNextActivity(false) // 다음 액티비티로 이동하면서 false 값을 전달
            }
        }

    }

    // 다음 액티비티로 이동하면서 값을 전달하는 함수
    private fun moveToNextActivity(value: Boolean) {
        val intent = Intent(this, ai_result::class.java).apply {
            putExtra("booleanValue", value) // boolean 값을 인텐트에 추가
        }
        startActivity(intent) // 다음 액티비티로 이동
    }
}