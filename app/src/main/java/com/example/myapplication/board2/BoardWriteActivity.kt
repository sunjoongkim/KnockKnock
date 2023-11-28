package com.example.myapplication.board2

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.NumberPicker
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.example.myapplication.R
import com.example.myapplication.adress.AddressSearchActivity
import com.example.myapplication.contentsList.BookmarkModel
import com.example.myapplication.databinding.ActivityAddressSearchBinding
import com.example.myapplication.databinding.ActivityBoardWriteBinding
import com.example.myapplication.utils.FBAuth
import com.example.myapplication.utils.FBRef
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import java.io.ByteArrayOutputStream

class BoardWriteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBoardWriteBinding

    private val TAG = BoardWriteActivity::class.java.simpleName

    private var isImageUpload = false

    val storage = Firebase.storage



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_write)


        // 주소 입력값을 addressArea 에 작성
        val addressResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {

                val intent = result.data
                val address = intent?.getStringExtra("address")
                if (address != null) {
                    binding.address1Area.visibility = View.VISIBLE
                    binding.address1Area.setText(address)
                    binding.address2Area.setHint("나머지 주소를 입력하세요.")
                }

            }
        }

        // 주소 검색 페이지 이동
        binding.btnSearchAddress.setOnClickListener {
            val intent = Intent(this, AddressSearchActivity::class.java)
            addressResultLauncher.launch(intent)
        }


        // 스피너 유형 추가
        val cities = arrayOf("지역을 선택해주세요","서울", "인천", "경기", "부산", "지방") // 필요한 만큼 도시를 추가합니다

        val cityAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cities)
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.renumArea.adapter = cityAdapter


        // 스피너 유형 추가
        val rents = arrayOf("매매유형을 선택해주세요","월세", "전세", "매매")

        val rentAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, rents)
        rentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.rentTypeArea.adapter = rentAdapter

        val builds = arrayOf("건물유형을 선택해주세요", "원룸", "투룸", "아파트","오피스텔", "빌라" ,"상가")

        val buildAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, builds)
        buildAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.buildingTypeArea.adapter = buildAdapter


        binding.writeBtn.setOnClickListener {

            //요기서 만든 변수 title,content,uid,time에 값을 넣어서 아래에 데이터베이스에 한번에 쏙! 넣어줌
            //1. title 변수 생성 - 제목 / titleArea에 들어오는 값을 받아온다
            var title = binding.titleArea.text.toString()

            //2. content 변수 생성 - 내용
            val content = binding.contentArea.text.toString()

            //3. uid 변수 생성 - 유저 아이디!
            val uid = FBAuth.getUid()

            //4. time 변수 생성 - 시간!
            val time = FBAuth.getTime()

            val mprice = binding.mpriceArea.text.toString()

            val yprice = binding.ypriceArea.text.toString()

            val fee = binding.feeArea.text.toString()

            //6. renum 변수 생성 - 지역
            val renum = binding.renumArea.selectedItem.toString()

            //7. rentType 변수 생성 - 전/월세 유형
            val rent = binding.rentTypeArea.selectedItem.toString()

            //8. buildingType 변수 생성 - 건물 유형
            val building = binding.buildingTypeArea.selectedItem.toString()

            val address = binding.address1Area.text.toString() + " " + binding.address2Area.text.toString()

            val floor1 = binding.floorArea1.text.toString()
            val floor2 = binding.floorArea2.text.toString()

            val complexname = binding.complexnameArea.text.toString()

            val roomnumber = binding.roomnumberArea.text.toString()

            val bathnumber = binding.bathnumberArea.text.toString()

            val temp = binding.tempArea.text.toString()

            val acreageArea1 = binding.acreageArea1.text.toString()
            val acreageArea2 = binding.acreageArea2.text.toString()

            val tenant = binding.tenantArea.text.toString()

            val tel = binding.telArea.text.toString()

            val key = FBRef.boardRef2.push().key.toString()

            //boardRef2 데이터베이스 테이블 안에 key값 넣고(key값을 만들어주는 이유: title , content, uid 이런게 너무 많으니까
            //얘내 여러개를 묶어서 나중에 key라는 값으로 한번에 정의할수있으니 좀 간단하게 할수있겠쥬??) 그리고 그 하위에 title,content등 입력
            FBRef.boardRef2
                .child(key) // 랜덤Key값 생성
                .setValue(BoardModel2(title,content,uid,time,mprice,yprice,fee,renum,rent,building, address, floor1, floor2, complexname, roomnumber, bathnumber, temp, acreageArea1, acreageArea2, tenant, tel))

            Toast.makeText(this, "개인 매물 작성 완료", Toast.LENGTH_SHORT).show()

            if(isImageUpload == true){
                imageUpload(key)
            }

            
            finish()
        }

        binding.imageArea.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 100)
            isImageUpload = true


        }

    }
    private fun imageUpload(key : String){

        val storageRef = storage.reference
        val mountainsRef = storageRef.child(key + ".png")


        val imageView = binding.imageArea
        imageView.isDrawingCacheEnabled = true
        imageView.buildDrawingCache()
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = mountainsRef.putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && requestCode == 100){
            // 이미지를 imageArea에 집어넣는다
            binding.imageArea.setImageURI(data?.data)
        }
    }
}