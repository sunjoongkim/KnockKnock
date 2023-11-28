package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.myapplication.auth.IntroActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class SplashActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth // 현재 인증상태 확인 !

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        auth = Firebase.auth        // 현재 인증상태 확인 !!

        // 만약에 로그인을 했으면, 그다음번에 다시 앱을 들어와도 로그인창이 아닌, 메인창으로 들어오는 코드이다.
        // 로그아웃을 하면, 이제 다시 로그인을 해야하니 인트로로 들어오는 코드이다.
        if(auth.currentUser?.uid == null ){    //만약에 유저 로그인 값이 null이면, 인트로(즉 로그인회원가입하는곳)으로 가는거고,
            Log.d("SplashActivity", "null")
            Handler().postDelayed({
                startActivity(Intent(this, IntroActivity::class.java))
                finish()
            }, 2000)
        }
        else{
            Log.d("SplashActivity","not null") // not null 즉 유저가 로그인 되있다는 뜻이면, 굳이 인트로창에서 다시 로그인하지않아도 됨
            Handler().postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 2000)

        }

    }
}