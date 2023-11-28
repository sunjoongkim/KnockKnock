package com.example.myapplication.relboard

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.example.myapplication.R

// RelListViewAdapter.kt
class RelListViewAdapter(val relList: MutableList<RelModel>) : BaseAdapter() {

    override fun getCount(): Int {
        return relList.size
    }

    override fun getItem(position: Int): Any {
        return relList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView

       if (view == null) {
            view = LayoutInflater.from(parent?.context).inflate(R.layout.rel_list_item, parent, false)
       }

        val relItem = relList[position]


        // TODO: Bind data to the view
        val title = view?.findViewById<TextView>(R.id.titleArea)
        val content = view?.findViewById<TextView>(R.id.contentArea)

        title!!.text = relList[position].title
        content!!.text = relList[position].content

        return view!!
    }

}
