package com.example.myapplication.board2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.myapplication.R
import org.w3c.dom.Text

// boardList 변수 생섷하고 아까 만들었떤 BoardModel 불러오기
class BoardListLVAdapter2(val boardList : MutableList<BoardModel2>) : BaseAdapter() {
    private val searchList: MutableList<BoardModel2> = mutableListOf()
    private var search: String = ""


    fun setSearch(search: String) {
        this.search = search
        searchList.clear()
        for (item in boardList) {
            if (item.rent == search) {
                searchList.add(item)
            }
        }
    }

    override fun getCount(): Int {
        return boardList.size      //만들어지는 만큼 계속 확장
    }

    override fun getItem(position: Int): Any {
        return boardList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {


        var converView = convertView
        if(converView == null){
            converView = LayoutInflater.from(parent?.context).inflate(R.layout.board_list_item2, parent, false)
        }

        val title = converView?.findViewById<TextView>(R.id.titleArea)
        val content = converView?.findViewById<TextView>(R.id.contentArea)
        val time = converView?.findViewById<TextView>(R.id.timeArea)
        val mprice = converView?.findViewById<TextView>(R.id.mpriceArea)
        val yprice = convertView?.findViewById<TextView>(R.id.ypriceArea)
        val renum = convertView?.findViewById<TextView>(R.id.renumArea)
        val rent = convertView?.findViewById<TextView>(R.id.rentTypeArea)
        val building = convertView?.findViewById<TextView>(R.id.buildingTypeArea)
        val fee = convertView?.findViewById<TextView>(R.id.feeArea)
        val adress = convertView?.findViewById<TextView>(R.id.adressArea)
        val floor1 = convertView?.findViewById<TextView>(R.id.floorArea1)
        val floor2 = convertView?.findViewById<TextView>(R.id.floorArea2)
        val complexname = convertView?.findViewById<TextView>(R.id.complexnameArea)
        val roomnumber = convertView?.findViewById<TextView>(R.id.roomnumberArea)
        val bathnumber = convertView?.findViewById<TextView>(R.id.bathnumberArea)
        val temp = convertView?.findViewById<TextView>(R.id.tempArea)
        val acreageArea1 = convertView?.findViewById<TextView>(R.id.acreageArea1)
        val acreageArea2 = convertView?.findViewById<TextView>(R.id.acreageArea2)
        val parking = convertView?.findViewById<TextView>(R.id.parkingArea)
        val tenant = convertView?.findViewById<TextView>(R.id.tenantArea)
        val tel = convertView?.findViewById<TextView>(R.id.telArea)

        title?.text = boardList[position].title
        content?.text = boardList[position].content
        time?.text = boardList[position].time
        mprice?.text = boardList[position].mprice
        yprice?.text = boardList[position].yprice
        renum?.text = boardList[position].renum
        rent?.text = boardList[position].rent
        building?.text = boardList[position].building
        fee?.text = boardList[position].fee
        adress?.text = boardList[position].adress
        complexname?.text = boardList[position].complexname
        roomnumber?.text = boardList[position].roomnumber
        floor1?.text = boardList[position].floor1
        floor2?.text = boardList[position].floor2
        bathnumber?.text = boardList[position].bathnumber
        temp?.text = boardList[position].temp
        acreageArea1?.text = boardList[position].acreageArea1
        acreageArea2?.text = boardList[position].acreageArea2
        tenant?.text = boardList[position].tenant
        tel?.text = boardList[position].tel
        // 검색 결과를 보여줄 때는 검색어와 일치하는 아이템만 표시
        if (boardList[position].rent == search) {
            searchList.add(boardList[position])
        }



        return converView!!
    }

}