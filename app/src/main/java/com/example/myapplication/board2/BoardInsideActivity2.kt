package com.example.myapplication.board2

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.contentsList.LikelistModel
import com.example.myapplication.databinding.ActivityBoardInside2Binding
import com.example.myapplication.databinding.ActivityBoardInsideBinding
import com.example.myapplication.utils.FBAuth
import com.example.myapplication.utils.FBRef
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.storage

class BoardInsideActivity2 : AppCompatActivity() {

    private var isLiked: Boolean = false
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding : ActivityBoardInside2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_inside2)
        sharedPreferences = getPreferences(Context.MODE_PRIVATE)

        /* 1번째 방법 하나하나 받아오기
        val title = intent.getStringExtra("title").toString()
        val content = intent.getStringExtra("content").toString()
        val time = intent.getStringExtra("time").toString()
        val mprice = intent.getStringExtra("mprice").toString()
        val yprice = intent.getStringExtra("yprice").toString()
        val fee = intent.getStringExtra("fee").toString()
        val rent = intent.getStringExtra("rent").toString()
        val building = intent.getStringExtra("building").toString()
        val renum = intent.getStringExtra("renum").toString()

        binding.titleArea.text = title
        binding.contentArea.text = content
        binding.mpriceArea.text = mprice
        binding.ypriceArea.text = yprice
        binding.feeArea.text = fee
        binding.rentTypeArea.text = rent
        binding.renttypeArea2.text = rent */

        // 두번째 방법 key로 한번에 받아오기

        Log.d("BoardInsideActivity2", "onCreate - isLiked: $isLiked")

        val key = intent.getStringExtra("key")
        getBoardData(key.toString())
        getImageData(key.toString())

        val likeButton = binding.btnLike

        isLiked = sharedPreferences.getBoolean(key, false)
        updateLikeButtonImage()

        likeButton.setOnClickListener {
            // 찜하기 상태를 데이터베이스에 저장
            saveLikeToDatabase(key.toString())

            // 찜하기 상태 업데이트 및 이미지 변경
            isLiked = !isLiked
            updateLikeButtonImage()

            // 찜하기 상태를 SharedPreferences에 저장
            with(sharedPreferences.edit()) {
                putBoolean(key, isLiked)
                apply()
            }
        }


    }


    override fun onResume() {
        super.onResume()
        // onResume에서 다시 확인하여 이미지 설정
        updateLikeButtonImage()
    }

    private fun updateLikeButtonImage() {
        val likeButton = binding.btnLike
        // isLiked 변수에 따라 이미지 변경
        if (isLiked) {
            likeButton.setImageResource(R.drawable.filled_heart)
        } else {
            likeButton.setImageResource(R.drawable.blank_heart)
        }
    }

    private fun saveLikeToDatabase(key: String) {
        // 찜하기 데이터 모델 생성
        val likeModel = LikelistModel(likelistIsTrue = true)

        // 데이터베이스 찜하기 레퍼런스
        val likeRef = FBRef.database.getReference("like_list")

        // 동적 리스트뷰를 고려하여 각 항목에 대한 찜하기 정보를 저장
        val userUid = FBAuth.getUid()
        if (userUid != null) {
            // 사용자의 UID를 사용하여 찜하기 정보를 저장
            likeRef.child(userUid).child(key).setValue(likeModel)
                .addOnSuccessListener {
                    // 성공적으로 저장된 경우 처리할 내용
                    Log.d("BoardInsideActivity2", "찜하기 정보가 성공적으로 저장되었습니다.")
                    Toast.makeText(this, "해당 매물을 찜했습니다.", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    // 저장에 실패한 경우 처리할 내용
                    Log.e("BoardInsideActivity2", "찜하기 정보 저장 실패: $it")
                }
        } else {
            // 사용자 UID가 없는 경우에 대한 처리 (예: 로그인이 필요함을 알림)
            Log.e("BoardInsideActivity2", "사용자 UID가 없습니다. 로그인이 필요합니다.")
            // TODO: 사용자에게 로그인이 필요함을 알리는 처리 추가
        }
    }

    private fun getImageData(key: String) {

        val storageReference = Firebase.storage.reference.child(key + ".png")

        val imageView = binding.imageArea

        storageReference.downloadUrl.addOnCompleteListener(OnCompleteListener { task ->
            if(task.isSuccessful){

                Glide.with(this)
                    .load(task.result)
                    .into(imageView)
            } else{

            }
        })
    }


    private fun getBoardData(key : String){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val dataModel = dataSnapshot.getValue(BoardModel2::class.java)

                // renum = 건물유형(원룸,투룸,아파트 등) rent = 서울,인천 , build = (월세 전세 매매) 으로바뀜
                // 여기서 renum이 "서울"이면서 rent가 "전세"인 경우만 리스트에 추가

                binding.titleArea.text = dataModel!!.title
                binding.contentArea.text = dataModel!!.content
                binding.mpriceArea.text = dataModel!!.mprice
                binding.mpriceArea2.text = dataModel!!.mprice
                binding.mpriceArea3.text = dataModel!!.mprice
                binding.ypriceArea.text = dataModel!!.yprice
                binding.ypriceArea2.text = dataModel!!.yprice
                binding.feeArea.text = dataModel!!.fee
                binding.feeArea2.text = dataModel!!.fee
                binding.rentTypeArea.text = dataModel!!.building
                binding.renttypeArea2.text = dataModel!!.building
                binding.adressArea.text = dataModel!!.adress
                binding.floorArea1.text = dataModel!!.floor1
                binding.floorArea2.text = dataModel!!.floor2
                binding.complexnameArea.text = dataModel!!.complexname
                binding.roomnumberArea.text = dataModel!!.roomnumber
                binding.bathnumberArea.text = dataModel!!.bathnumber
                binding.tempArea.text = dataModel!!.temp
                binding.acreageArea1.text = dataModel!!.acreageArea1
                binding.acreageArea2.text = dataModel!!.acreageArea2
                binding.tenantArea.text = dataModel!!.tenant
                binding.telArea.text = dataModel!!.tel


            }

            //에러일시에
            override fun onCancelled(databaseError: DatabaseError) {

            }
        }

        FBRef.boardRef2.child(key).addValueEventListener(postListener)

    }
}