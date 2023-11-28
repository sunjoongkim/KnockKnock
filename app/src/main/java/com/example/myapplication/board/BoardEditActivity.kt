package com.example.myapplication.board

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityBoardEditBinding
import com.example.myapplication.utils.FBAuth
import com.example.myapplication.utils.FBRef
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.storage

class BoardEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBoardEditBinding

    private lateinit var key: String

    private val TAG = BoardEditActivity::class.java.simpleName

    private lateinit var writerUid : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_edit)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_edit)

        key = intent.getStringExtra("key").toString()
        getBoardData(key)
        getImageData(key)

        binding.editBtn.setOnClickListener {
            editBoardData(key)
        }


    }

    private fun editBoardData(key : String){
        FBRef.boardRef
            .child(key)
            .setValue(
                BoardModel(binding.titleArea.text.toString(),
                binding.contentArea.text.toString(),
                writerUid,
                FBAuth.getTime())

            )
        Toast.makeText(this,"수정완료",Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun getImageData(key : String){
        val storageReference = Firebase.storage.reference.child(key + ".png")

        // ImageView in your Activity
        val imageViewFromFB = binding.imageArea

        storageReference.downloadUrl.addOnCompleteListener(OnCompleteListener { task ->
            if(task.isSuccessful){

                Glide.with(this)
                    .load(task.result)
                    .into(imageViewFromFB)
            }else {

            }
        })
    }
    private fun getBoardData(key: String) {
        val boardRef = FBRef.boardRef.child(key)

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val dataModel = dataSnapshot.getValue(BoardModel::class.java)

                binding.titleArea.setText(dataModel?.title)
                binding.contentArea.setText(dataModel?.content)
                writerUid = dataModel!!.uid
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 데이터 로드 실패 시 메시지 기록
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }

        boardRef.addListenerForSingleValueEvent(postListener)
    }
}