package com.example.whenandwhere

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.whenandwhere.R

class login : AppCompatActivity() {
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // 아이디와 비밀번호 입력란 참조
        usernameEditText = findViewById(R.id.username)
        passwordEditText = findViewById(R.id.password)

        // 버튼 참조
        loginButton = findViewById(R.id.login)

        loginButton.setOnClickListener {
            val intent = Intent(this, GrouplistActivity::class.java)
            startActivity(intent)
        }

        // 초기 상태 설정
        updateButtonState()

        // 입력이 변경될 때마다 상태 업데이트
        usernameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateButtonState()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateButtonState()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun updateButtonState() {
        // 아이디와 비밀번호가 입력되었는지 여부에 따라 조건 정의
        val isInputValid =
            !usernameEditText.text.isNullOrBlank() && !passwordEditText.text.isNullOrBlank()

        // 버튼 상태에 따라 배경색 변경
        if (isInputValid) {
            // 입력이 유효한 경우
            loginButton.setBackgroundResource(R.drawable.orangebtn)
            loginButton.isEnabled = true
        } else {
            // 입력이 유효하지 않은 경우
            loginButton.setBackgroundResource(R.drawable.graybtn)
            loginButton.isEnabled = false
        }
    }
}
