package com.example.myapplication.contentsList

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.utils.FBAuth
import com.example.myapplication.utils.FBRef

// items 값을 받아오고 ArrayList (ContentModel이라는 kt파일에 제목, 사진링크, 링크 순으로 )에 저장하고 리사이클뷰생성
class BookmarkRVAdapter(val context : Context,
                       val items : ArrayList<ContentModel>,
                       var keyList : ArrayList<String>,
                       val bookmarkIdList : MutableList<String>)
    : RecyclerView.Adapter<BookmarkRVAdapter.Viewholder>() {

    // 리사이클뷰를 '디자인' 하는 xml파일과 연결한다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkRVAdapter.Viewholder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.content_rv_item, parent, false)

        Log.d("ContentRVAdapter", keyList.toString())
        Log.d("ContentRVAdapter", bookmarkIdList.toString())
        return Viewholder(v)
    }

    override fun onBindViewHolder(holder: BookmarkRVAdapter.Viewholder, position: Int) {
        holder.bindItems(items[position], keyList[position])
        /*      if(itemClick != null){
           holder.itemView.setOnClickListener{v->      // 클릭하면 url로 가는것
               itemClick?.onClick(v, position)
           }
       } */

    }
    override fun getItemCount(): Int {
        return items.size
    }

    // 클릭하면 뜰 창 지정하고, 버튼 선언하는 곳?
    inner class Viewholder(itemView : View) : RecyclerView.ViewHolder(itemView){

        fun bindItems(item : ContentModel, key : String){

            // 아이템 뷰 클릭하면 뜰 창을 지정해준다.
            itemView.setOnClickListener{

                // 1. 클릭하면 토스트 창 띄우기
                Toast.makeText(context, item.title, Toast.LENGTH_SHORT).show()

                // 2. 클릭하면 contentModel에서 지정한 webUrl을 가져와서 링크를 연결시킨다.
                val intent = Intent(context, ContentShowActivity::class.java)
                intent.putExtra("url",item.webUrl)
                itemView.context.startActivity(intent)
            }

            // 해당 디자인xml파일에있는 리사이클뷰의 버튼들 선언하기 !!! 제목, 이미지, 북마크
            var contentTitle = itemView.findViewById<TextView>(R.id.textArea)
            var imageViewArea = itemView.findViewById<ImageView>(R.id.imageArea)
            var bookmarkArea = itemView.findViewById<ImageView>(R.id.bookmarkArea)


            // 키리스트에 북마크가 포함되어있다면 북마크의 컬러를 거믛게 칠해주고,
            if(bookmarkIdList.contains(key)){
                bookmarkArea.setImageResource(R.drawable.bookmark_color)
            }else{ // 아니라면 원래대로 하얗게
                bookmarkArea.setImageResource(R.drawable.bookmark_white)
            }




            contentTitle.text = item.title

            Glide.with(context)
                .load(item.imageUrl)
                .into(imageViewArea)

        }

    }
}