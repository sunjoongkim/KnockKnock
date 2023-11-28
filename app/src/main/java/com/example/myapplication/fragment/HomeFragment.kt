package com.example.myapplication.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Banner_Inside
import com.example.myapplication.R
import com.example.myapplication.adress.WebViewActivity
import com.example.myapplication.board2.BoardWriteActivity
import com.example.myapplication.contentsList.BookmarkRVAdapter
import com.example.myapplication.contentsList.ContentModel
import com.example.myapplication.contentsList.ContentsListActivity
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.rel_build.Rel_Random_Activity
import com.example.myapplication.rel_region.Jeonse_seoul_Activity
import com.example.myapplication.rel_region.Rel_Category_Activity
import com.example.myapplication.relboard.RelWriteActivity
import com.example.myapplication.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding

    private val TAG = HomeFragment::class.java.simpleName

    val bookmarkIdList = mutableListOf<String>()
    val items = ArrayList<ContentModel>()
    val itemKeyList = ArrayList<String>()

    lateinit var rvAdapter : BookmarkRVAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("HomeFragment", "OnCreateView")


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)


        binding.eventbanner1.setOnClickListener {
            val intent = Intent(context, Banner_Inside::class.java)
            startActivity(intent)
        }
        binding.textViewTop2.setOnClickListener {
            val intent = Intent(context, Rel_Random_Activity::class.java)
            startActivity(intent)
        }

        binding.textViewTop4.setOnClickListener {
            val intent = Intent(context, BoardWriteActivity::class.java)
            startActivity(intent)
        }

        binding.newsBtn.setOnClickListener {
            val intent = Intent(context, ContentsListActivity::class.java)
            intent.putExtra("category","category2")
            startActivity(intent)
        }


        binding.btnRellist.setOnClickListener {
            val intent = Intent(context, Rel_Category_Activity::class.java)
            startActivity(intent)
        }

        binding.RelwriteBtn.setOnClickListener {
            val intent = Intent(context, BoardWriteActivity::class.java)
            startActivity(intent)
        }
        binding.commuBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_talkFragment)
        }

        binding.relTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_rellistFragment)
        }


        binding.textViewTop3.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_talkFragment)
        }




        binding.likepageBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_bookmarkFragment2)
        }



        binding.tipTap.setOnClickListener {

            it.findNavController().navigate(R.id.action_homeFragment_to_tipFragment)
        }

        binding.bookmarkTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_bookmarkFragment2)

        }
        binding.searchBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_tipFragment)
        }

        binding.talkTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_talkFragment)

        }
        binding.storeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_storeFragment)

        }
        rvAdapter = BookmarkRVAdapter(requireContext(), items, itemKeyList, bookmarkIdList)

        val rv : RecyclerView = binding.mainRV
        rv.adapter = rvAdapter

        rv.layoutManager = GridLayoutManager(requireContext(), 1)

        getCategoryData()
        // Inflate the layout for this fragment
        return binding.root
    }
    private fun getCategoryData(){
        // 찜목록 가져오는법 !!
        // 1. 전체 카테고리에 있는 콘텐츠를 전부 가져온다.
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (dataModel in dataSnapshot.children) {
                    val item = dataModel.getValue(ContentModel::class.java)

                    items.add(item!!)
                    itemKeyList.add(dataModel.key.toString())

                    }
                rvAdapter.notifyDataSetChanged()
            }

                override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("ContentsListActivity", "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.category1.addValueEventListener(postListener)
        FBRef.category2.addValueEventListener(postListener)

    }



}