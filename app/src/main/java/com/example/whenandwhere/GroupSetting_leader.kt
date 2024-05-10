package com.example.whenandwhere

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import com.example.whenandwhere.databinding.ActivityAiResultBinding
import com.example.whenandwhere.databinding.ActivityGroupSettingLeaderBinding

class GroupSetting_leader : AppCompatActivity() {
    private lateinit var binding: ActivityGroupSettingLeaderBinding
    private var alertDialog: AlertDialog? = null // AlertDialog 인스턴스를 저장할 변수 추가

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroupSettingLeaderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val back: ImageView = findViewById(R.id.arrowleft)

        back.setOnClickListener{
            val intent = Intent(this, Grouphome::class.java)
            startActivity(intent)
        }

        val exitGroup: Button = findViewById(R.id.exitGroupBtn)
        exitGroup.setOnClickListener{

        }

        binding.exitGroupBtn.setOnClickListener{
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.exit_popup,null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)

            // AlertDialog 인스턴스 생성 및 표시
            alertDialog = mBuilder.show()

            val yesBtn = mDialogView.findViewById<RadioButton>(R.id.radioButton5)
            yesBtn.setOnClickListener {
                alertDialog?.dismiss() // 다이얼로그 닫기

            }

            val noBtn = mDialogView.findViewById<RadioButton>(R.id.radioButton6)
            noBtn.setOnClickListener {
                alertDialog?.dismiss() // 다이얼로그 닫기

            }

        }

    }
}