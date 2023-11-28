package com.example.myapplication.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityJoinBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class JoinActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth // 현재 인증상태 확인 !

    private lateinit var binding : ActivityJoinBinding // 바인딩 기능 불러오기 , xml파일에서 <layout>태그로 전체 감싸주기

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        auth = Firebase.auth        // 현재 인증상태 확인 !!

        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)

        binding.joinBtn.setOnClickListener {  // 회원가입 버튼을 클릭시 나올 액티비티

            var isGoToJoin = true

            val email = binding.emailArea.text.toString()
            val password1 = binding.passwordArea1.text.toString()
            val password2 = binding.passwordArea2.text.toString()     // 이메일, 패스워드, 패스워드확인 입력칸 정의

            // 값이 비어있는지 확인한다.
            if(email.isEmpty()){      // 이메일 칸에 값이 비었으면 이메일을 입력해달라고 하고 회원가입이 되지않는다.
                Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_LONG).show()
                isGoToJoin = false

            }
            if(password1.isEmpty()){
                Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }
            if(password2.isEmpty()){
                Toast.makeText(this, "비밀번호를 확인해주세요", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }

            //만약 비번1과 비번2가 동일하지않다면, 비밀번호를 똑같이 입력해달라는 토스트 창을 띄운다.
            if(!password1.equals(password2)){
                Toast.makeText(this, "비밀번호를 똑같이 입력해주세요", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }

            // 비밀번호를 6자 이하로 입력할 시에 6자 이상 입력해달라고 하는 토스트 창을 띄운다.
            if(password1.length < 6){
                Toast.makeText(this, "비밀번호를 6자 이상 입력해주세요", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }

            if(isGoToJoin){

                auth.createUserWithEmailAndPassword(email, password1) // 아이디는 이메일 형식이여야 하며, 비밀번호는 8자?이상이여야 함
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // 가입에 성공할 시, 해당 로그를 띄웁니다.

                            Toast.makeText(this, "성공", Toast.LENGTH_LONG).show()

                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                            startActivity(intent)

                        } else {
                            // 가입에 실패할 시, 해당 로그를 띄웁니다.

                            Toast.makeText(this, "실패", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }





    }
}