package com.example.myapplication.fragment

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adress.search.SearchResultsAdapter
import com.example.myapplication.board2.BoardListLVAdapter2
import com.example.myapplication.board2.BoardModel2
import com.example.myapplication.contentsList.ContentsListActivity
import com.example.myapplication.databinding.FragmentTipBinding
import com.example.myapplication.rel_searchview.Apart_Activity
import com.example.myapplication.rel_searchview.Month_Activity
import com.example.myapplication.rel_searchview.Office_Activity
import com.example.myapplication.rel_searchview.OneRoom_Activity
import com.example.myapplication.rel_searchview.Res_Activity
import com.example.myapplication.rel_searchview.Year_Activity
import com.example.myapplication.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [TipFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TipFragment : Fragment() {

    private lateinit var binding : FragmentTipBinding
    private val searchResults: MutableList<BoardModel2> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tip, container, false)

        // 리사이클러뷰 초기화
        val recyclerView: RecyclerView = binding.root.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // 검색 뷰 설정
        val searchView: SearchView = binding.root.findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // 검색 버튼을 눌렀을 때 처리
                fetchFilteredData()

                val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(searchView.windowToken, 0)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // 검색어가 변경될 때 처리
                // 여기에서는 실시간으로 검색 결과를 업데이트하는 로직 추가 가능
                return true
            }
        })



        binding.btnFilterOffice.setOnClickListener {
            val intent = Intent(context, Office_Activity::class.java)
            startActivity(intent)

        }

        binding.btnFilterApart.setOnClickListener {
            val intent = Intent(context, Apart_Activity::class.java)
            startActivity(intent)

        }

        binding.btnFilterOneroom.setOnClickListener {
            val intent = Intent(context, OneRoom_Activity::class.java)
            startActivity(intent)

        }

        binding.btnFilterRent2.setOnClickListener {
            val intent = Intent(context, OneRoom_Activity::class.java)
            startActivity(intent)

        }

        binding.btnFilterRent3.setOnClickListener {
            val intent = Intent(context, Apart_Activity::class.java)
            startActivity(intent)

        }

        binding.btnFilterRent4.setOnClickListener {
            val intent = Intent(context, Office_Activity::class.java)
            startActivity(intent)

        }

        binding.btnFilterRenttype1.setOnClickListener {
            val intent = Intent(context, Month_Activity::class.java)
            startActivity(intent)

        }

        binding.btnFilterRenttype2.setOnClickListener {
            val intent = Intent(context, Year_Activity::class.java)
            startActivity(intent)

        }

        binding.btnFilterRenttype3.setOnClickListener {
            val intent = Intent(context, Res_Activity::class.java)
            startActivity(intent)

        }

        binding.btnFilterRenttype4.setOnClickListener {

        }

        binding.category1.setOnClickListener {
            val intent = Intent(context, ContentsListActivity::class.java) // ALL버튼 눌르면 리사이클뷰리스트화면으로 연결
            intent.putExtra("category","category1") // 카테고리에서 카테고리1로 지정해서 if카테고리1이라면 그 아래 지정한 값이 뜨도록
            startActivity(intent)
        }

        binding.category2.setOnClickListener {
            val intent = Intent(context, ContentsListActivity::class.java)
            intent.putExtra("category","category2")
            startActivity(intent)
        }
        binding.homeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_tipFragment_to_homeFragment)

        }
        binding.bookmarkTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_tipFragment_to_bookmarkFragment2)

        }
        binding.talkTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_tipFragment_to_talkFragment)

        }
        binding.storeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_tipFragment_to_storeFragment)

        }
        binding.relTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_tipFragment_to_rellistFragment)
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun fetchFilteredData() {
        val searchQuery = binding.searchView.query


        FirebaseDatabase.getInstance().reference.child("board2").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val filteredList = snapshot.children.filter {
                        val title = it.child("title").getValue(String::class.java) ?: ""
                        val content = it.child("content").getValue(String::class.java) ?: ""
                        val rent = it.child("rent").getValue(String::class.java) ?: ""

                        title.contains(searchQuery) || content.contains(searchQuery) || rent.contains(searchQuery)
                    }
                    val boardList = mutableListOf<BoardModel2>()
                    val keyList = mutableListOf<String>()

                    filteredList.forEach {
                        if (it != null) {
                            boardList.add(it.getValue(BoardModel2::class.java)!!)
                            keyList.add(it.key!!)
                        }
                    }

                    if (filteredList != null && filteredList.isNotEmpty()) {
                        binding.recyclerView.adapter = SearchResultsAdapter(boardList, keyList)
                    } else {
                        Toast.makeText(context, "검색된 결과가 없습니다.", Toast.LENGTH_SHORT).show()
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "DB Search 오류 입니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

}