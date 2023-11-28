package com.example.myapplication.adress.search

import android.content.ContentValues
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.board2.BoardInsideActivity2
import com.example.myapplication.board2.BoardModel2
import com.example.myapplication.databinding.ItemSearchResultBinding
import com.example.myapplication.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class SearchResultsAdapter(private var dataList: List<BoardModel2>, private var keyList: List<String>) : RecyclerView.Adapter<SearchResultsAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemSearchResultBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model : BoardModel2, key : String) {
            val title = binding.titleArea
            val content = binding.contentArea
            val type = binding.typeArea
            val time = binding.timeArea

            title.text = model.title
            content.text = model.content
            time.text = model.time

            val building = if (model.building != "" && model.building != "매매유형을 선택해주세요") {
                " / " + model.building
            } else {
                ""
            }
            val renum = if (model.renum != "" && model.renum != "건물유형을 선택해주세요") {
                " / " + model.renum
            } else {
                ""
            }

            type.text = if (model.rent != "지역을 선택해주세요") {
                model.rent + building + renum
            } else {
                ""
            }

            binding.itemView.setOnClickListener {
                val intent = Intent(binding.root.context, BoardInsideActivity2::class.java)
                intent.putExtra("key", key)
                binding.root.context.startActivity(intent)
            }
        }
    }

    // 검색 결과 업데이트 메서드
    fun updateResults(newData: List<BoardModel2>) {
        dataList = newData
        notifyDataSetChanged()
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = dataList[position]
        val key = keyList[position]
        holder.bind(model, key)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount() = dataList.size

}