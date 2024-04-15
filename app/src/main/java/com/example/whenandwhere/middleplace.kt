package com.example.whenandwhere

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import com.example.whenandwhere.databinding.ActivityMiddleplaceBinding

class middleplace : AppCompatActivity() {
    private lateinit var binding: ActivityMiddleplaceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_middleplace)

        binding = ActivityMiddleplaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.finish.setOnClickListener{
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.alcohol,null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)

            mBuilder.show()

            val yesBtn = mDialogView.findViewById<RadioButton>(R.id.radioButton5)
            yesBtn.setOnClickListener {

            }

            val noBtn = mDialogView.findViewById<RadioButton>(R.id.radioButton6)
            noBtn.setOnClickListener {

            }
        }
    }
}