package com.example.myapplication.rel_region

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.myapplication.R
import com.example.myapplication.board2.BoardInsideActivity2
import com.example.myapplication.board2.BoardListLVAdapter2
import com.example.myapplication.board2.BoardModel2
import com.example.myapplication.databinding.ActivityJeonseProvinceBinding
import com.example.myapplication.databinding.ActivityMonthIncheonBinding
import com.example.myapplication.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class Month_incheon_Activity : AppCompatActivity() {
    private lateinit var binding: ActivityMonthIncheonBinding
    private val boardDataList = mutableListOf<BoardModel2>()
    private lateinit var boardRVAdapter: BoardListLVAdapter2
    private val boardKeyList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_month_incheon)
        boardRVAdapter = BoardListLVAdapter2(boardDataList)
        binding.relListView.adapter = boardRVAdapter

        binding.relListView.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this@Month_incheon_Activity, BoardInsideActivity2::class.java)
            intent.putExtra("key", boardKeyList[position])
            startActivity(intent)
        }


        getFBBoardData()


    }

    private fun getFBBoardData() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                boardDataList.clear()
                for (dataModel in dataSnapshot.children) {

                    // item 변수를 생성하고 title, content 등을 담는다 그리고 위에서 넣어주면 끝
                    val item = dataModel.getValue(BoardModel2::class.java)

                    Log.d("BoardData", "item: $item")

                    if (item != null) {
                        // renum = 건물유형(원룸,투룸,아파트 등) rent = 서울,인천 , build = (월세 전세 매매) 으로바뀜
                        // 여기서 renum이 "서울"이면서 rent가 "전세"인 경우만 리스트에 추가
                        if (item.building == "월세" && item.rent == "인천") {
                            boardDataList.add(item)
                            boardKeyList.add(dataModel.key.toString())
                        } else{
                            Log.d("BoardData", "Filtered out: renum=${item.renum}, rent=${item.rent}")
                        }
                    }
                }
                boardRVAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.boardRef2.addValueEventListener(postListener)
    }
}