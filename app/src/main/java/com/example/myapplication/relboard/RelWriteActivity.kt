package com.example.myapplication.relboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityRelWriteBinding
import com.example.myapplication.utils.FBAuth
import com.example.myapplication.utils.FBRef

class RelWriteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRelWriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rel_write)


        binding.writeBtn.setOnClickListener {

            val title = binding.titleArea.text.toString()
            val content = binding.contentArea.text.toString()
            val uid = FBAuth.getUid()
            val time = FBAuth.getTime() // 시간 가져오기

            FBRef.rellistRef
                .child(FBAuth.getUid())
                .push()
                .setValue(RelModel(title, content, uid, time))

            Toast.makeText(this,"개인 매물 작성 완료", Toast.LENGTH_LONG).show()

            finish()
        }
    }
}