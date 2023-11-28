package com.example.myapplication.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityIntroBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class IntroActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth // 현재 인증상태 확인 !

    private lateinit var binding : ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        auth = Firebase.auth        // 현재 인증상태 확인 !!

        binding = DataBindingUtil.setContentView(this, R.layout.activity_intro)


        //로그인 버튼을 클릭할 시에 액티비티 !!
        binding.loginBtn.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java) // intent를 이용하여 만약에 클릭됬으면 로그인액티비티로 이동
            startActivity(intent)
        }

        //회원가입 버튼을 클릭할 시에 액티비티 !!
        binding.joinBtn.setOnClickListener {
            val intent = Intent(this, JoinActivity::class.java) // intent를 이용하여 만약에 클릭됬으면 회원가입 액티비티로 이동
            startActivity(intent)

        }

        binding.noCountBtn.setOnClickListener {
            auth.signInAnonymously()
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        Toast.makeText(this, "환영합니다", Toast.LENGTH_LONG).show()

                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)

                    } else {
                        Toast.makeText(this, "로그인 실패", Toast.LENGTH_LONG).show()
                    }
                }
        }


    }
}