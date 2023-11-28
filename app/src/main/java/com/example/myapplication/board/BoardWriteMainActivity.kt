package com.example.myapplication.board

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityBoardWriteMainBinding
import com.example.myapplication.utils.FBAuth
import com.example.myapplication.utils.FBRef
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import java.io.ByteArrayOutputStream

class BoardWriteMainActivity : AppCompatActivity() {


    private lateinit var binding : ActivityBoardWriteMainBinding

    private val TAG = BoardWriteMainActivity::class.java.simpleName

    private var isImageUpload = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_write_main)

        //글 다 작성하고 작성완료 버튼 눌를시에 나타나는 액티브 지정
        binding.writeBtn.setOnClickListener {

            // 사용자가 입력한 제목과 내용을 불러들인다.
            val title = binding.titleArea.text.toString()
            var content = binding.contentArea.text.toString()
            var uid = FBAuth.getUid()
            var time = FBAuth.getTime()


            // 만약에 내가 게시글을 클릭했을때, 그 게시글에 대한 정보를 받아와야하는데 이미지 이름에대한 정보를
            // 모르니까 이미지 문서의 이름을 key 라는 값으로 대체해서 이미지에대한 정보를 찾기 쉽게 해놓음.
            val key = FBRef.boardRef.push().key.toString()

            // Firebase Realtime Database에 게시글 정보를 저장
            FBRef.boardRef
                .child(key)
                .setValue(BoardModel(title,content,uid,time))

            // 게시글작성완료라는 창 띄우고 finish로 창 종료
            Toast.makeText(this, "게시글 작성 완료", Toast.LENGTH_SHORT).show()


            //위에서 선언한 key값을 이ㅣ미지가 업로드되었다면 imageUpload(key)로 이미지의 키값을 가져온다.
            if(isImageUpload == true){
                imageUpload(key)
            }
            imageUpload(key)
            finish()


        }

        //이미지 추가 버튼을 누른다면, 갤러리를 열어서 이미지값을 가져온다는 뜻이다.
        binding.imageArea.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery,100)
            isImageUpload = true


        }

    }

    // 파이어베이스 Storage를 사용하여 이미지 업로드
    private fun imageUpload(key : String){

        // Firebase Storage와 관련된 객체 초기화
        val storage = Firebase.storage
        val storageRef = storage.reference
        val mountainsRef = storageRef.child(key + ".png")

        // ImageView에서 Bitmap 추출
        val imageView = binding.imageArea
        imageView.isDrawingCacheEnabled = true
        imageView.buildDrawingCache()
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap

        // Bitmap을 ByteArray로 변환
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        // Firebase Storage에 이미지 업로드
        var uploadTask = mountainsRef.putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }
    }

    // 갤러리에서 이미지를 선택한 후의 결과 처리
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && requestCode == 100){
            binding.imageArea.setImageURI(data?.data)

        }
    }
}