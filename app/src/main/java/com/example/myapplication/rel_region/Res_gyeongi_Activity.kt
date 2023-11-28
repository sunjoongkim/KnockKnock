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
import com.example.myapplication.databinding.ActivityMonthSeoulBinding
import com.example.myapplication.databinding.ActivityResGyeongiBinding
import com.example.myapplication.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class Res_gyeongi_Activity : AppCompatActivity() {
    private lateinit var binding: ActivityResGyeongiBinding
    private val boardDataList = mutableListOf<BoardModel2>()
    private lateinit var boardRVAdapter: BoardListLVAdapter2
    private val boardKeyList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_res_gyeongi)
        boardRVAdapter = BoardListLVAdapter2(boardDataList)
        binding.relListView.adapter = boardRVAdapter

        binding.relListView.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this@Res_gyeongi_Activity, BoardInsideActivity2::class.java)
            intent.putExtra("key", boardKeyList[position])
            startActivity(intent)
        }

        getFBBoardData()
    }

    private fun getFBBoardData() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                boardDataList.clear()
                boardKeyList.clear()

                for (dataModel in dataSnapshot.children) {
                    val item = dataModel.getValue(BoardModel2::class.java)

                    Log.d("BoardData", "item: $item")

                    if (item != null && item.building == "매매" && item.rent == "경기") {
                        boardDataList.add(item)
                        boardKeyList.add(dataModel.key.toString())
                    } else {
                        Log.d("BoardData", "Filtered out: renum=${item?.renum}, rent=${item?.rent}")
                    }
                }

                boardRVAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.boardRef2.addValueEventListener(postListener)
    }
}