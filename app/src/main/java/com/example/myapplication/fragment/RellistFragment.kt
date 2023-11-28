package com.example.myapplication.fragment

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.myapplication.R
import com.example.myapplication.TermActivity
import com.example.myapplication.board2.BoardInsideActivity2
import com.example.myapplication.board2.BoardListLVAdapter2
import com.example.myapplication.board2.BoardModel2
import com.example.myapplication.board2.BoardWriteActivity
import com.example.myapplication.contentsList.ContentModel
import com.example.myapplication.databinding.FragmentRellistBinding
import com.example.myapplication.databinding.FragmentStoreBinding
import com.example.myapplication.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class RellistFragment : Fragment() {
    private lateinit var binding: FragmentRellistBinding
    private val boardDataList = mutableListOf<BoardModel2>()
    private lateinit var boardRVAdapter : BoardListLVAdapter2
    private val boardKeyList = mutableListOf<String>()
    private val searchList = mutableListOf<BoardModel2>()
    private var search = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        // inflater -  container -
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_rellist, container, false)

        // 어댑터연결!! boardRVadapter라는 변수를 생성해서 그 안에 찐 어댑터 연결하고 보드리스트뷰(리스트뷰아이디)랑 연결해서
        // 안에 item같은거 연결하는것임
        boardRVAdapter = BoardListLVAdapter2(boardDataList)
        binding.boardListView.adapter = boardRVAdapter

        binding.boardListView.setOnItemClickListener { parent, view, position, id ->

            //방법 1번째
            //보드리스트 1개 , 1개 를 클릭했을시 내부를 들어가는 코드!
            //클릭하면 보드내부로 인텐트 이동
            /*val intent = Intent(context, BoardInsideActivity2::class.java)
            //내부에 값을 하나하나 전달하기!, 값을 전달하고, Inside코드에서 값을 받는 코드도 해줘야됨 !
            intent.putExtra("title",boardDataList[position].title)
            intent.putExtra("content",boardDataList[position].content)
            intent.putExtra("time",boardDataList[position].time)
            startActivity(intent) */

            //방법 2번째 - 위에서는 하나하나 불러왔다면 이번엔 키값으로 한번에 받아올수있다는 장점이있으며,
            // 아래에서 dataModel.key를 선언해주고 받아와야한다. 그리고 private val boardKeyList = mutableListOf<String>()도 선언
            val intent = Intent(context, BoardInsideActivity2::class.java)
            intent.putExtra("key",boardKeyList[position])
            startActivity(intent)

        }



        // ------------------------------------------클릭 리스너 --------------------------------------
        binding.writeBtn.setOnClickListener {
            val intent = Intent(context, BoardWriteActivity::class.java)
            startActivity(intent)
        }


        binding.homeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_rellistFragment_to_homeFragment)

        }
        binding.bookmarkTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_rellistFragment_to_bookmarkFragment2)

        }
        binding.talkTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_rellistFragment_to_talkFragment)

        }
        binding.tipTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_rellistFragment_to_tipFragment)

        }
        binding.storeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_rellistFragment_to_storeFragment)

        }

        getFBBoardData()

        return binding.root
        // 검색 뷰 설정
        val searchView: SearchView = binding.root.findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // 검색 버튼을 눌렀을 때 처리
                // 여기에서는 가상의 데이터를 추가하고 업데이트
                search = query.orEmpty()
                updateSearchResults()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // 검색어가 변경될 때 처리
                // 여기에서는 실시간으로 검색 결과를 업데이트하는 로직 추가 가능
                search = newText.orEmpty()
                updateSearchResults()
                return true
            }
        })
    }

    private fun updateSearchResults() {
        // 검색 결과를 업데이트하고 어댑터에 전달
        val filteredList = if (search.isEmpty()) {
            // 검색어가 비어있을 경우 전체 리스트를 사용
            boardDataList
        } else {
            // 검색어가 있을 경우 boardDataList에서 검색 결과를 찾아서 searchList에 저장
            searchList.clear()
            for (item in boardDataList) {
                if (item.rent == search) {
                    searchList.add(item)
                }
            }
            searchList
        }

        boardRVAdapter.setSearch(search) // 어댑터에 검색어 설정
        boardRVAdapter.notifyDataSetChanged() // 어댑터 갱신
    }

    private fun getFBBoardData(){

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                boardDataList.clear()
                for (dataModel in dataSnapshot.children) {

                    dataModel.key

                    // item 변수를 생성하고 title , content등을 담는다 그리고 위에서 넣어주면 끝
                    val item = dataModel.getValue(BoardModel2::class.java)
                    boardDataList.add(item!!)
                    boardKeyList.add(dataModel.key.toString())
                }
                //최신순으로 게시글 리스트 뒤집기
                boardKeyList.reverse()
                boardDataList.reverse()

                boardRVAdapter.notifyDataSetChanged()


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.boardRef2.addValueEventListener(postListener)

    }
}