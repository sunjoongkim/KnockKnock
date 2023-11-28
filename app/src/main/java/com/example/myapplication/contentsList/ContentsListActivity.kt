package com.example.myapplication.contentsList


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.utils.FBAuth
import com.example.myapplication.utils.FBRef
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class ContentsListActivity : AppCompatActivity() {

    lateinit var myRef : DatabaseReference

    val bookmarkIdList = mutableListOf<String>() //북마크 찜하면 넣을 리스트이며 키값을 넣어줌

    lateinit var rvAdapter: ContentRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contents_list)

        // items( = 리스트뷰의 아이템1개 {사진, 제목, 북마크로 1세트 묶음} itemKeyList (북마크 들) 선언
        // 그리고 items랑 북마크는 계속해서 반복될것이니까 배열리스트에 담아주고, items는 콘텐츠 즉 제목,사진,링크가 필요하니까 ContentModel과 연결
        val items = ArrayList<ContentModel>()
        val itemKeyList = ArrayList<String>()

        // rvAdapter로 items와 itemKeyList를 넘겨주는 코드이다. 이 코드를 넘겨주었으면 rvAdapter 에 가서 받는 코드도 적어야한다.
        val rvAdapter = ContentRVAdapter(baseContext, items, itemKeyList, bookmarkIdList)

        // 데이터베이스 연결
        val database = Firebase.database

        // 카테고리라는 코드를 만들어서 카테고리1 ( ALL버튼부분) 카테고리2 (COOK버튼부분) 이런식으로 나누어는것
        val category = intent.getStringExtra("category")


        //위에서 만든 카테고리가 카테고리1이라면 contents 라는 데이터베이스를 만들고 그안에 데이터베이스 값을 저장한다 (item)
        if (category == "category1") {
            myRef = database.getReference("contents") // contents라는 데이터베이스 테이블 생성하는 코드
        } else if (category == "category2") {
            myRef = database.getReference("contents2")


            //myRef2라는 items와 contents2 라는 데이터베이스안에 값을 넣는다. 한번 넣으면 끝이라서 두번넣으면 두번넣어지니 조심
            //"제목","사진링크","링크"순으로 myRef2에 값을 집어 넣어서 화면에 띄우고, 데이터베이스안에는 해당값들을 넣는 구조이다.
/*
            val myRef2 = database.getReference("contents2")


            myRef2.push().setValue(
                ContentModel("서울인구 천만인데 내년 새집 1만가구…","https://wimg.mk.co.kr/news/cms/202311/10/news-p.v1.20231110.333adece8da24e2893ee460181afaef6_P1.png","https://www.mk.co.kr/news/realestate/10871892")
            )
            myRef2.push().setValue(
                ContentModel("집 보러 오는 사람이 없어요…로열층 급매도 안 팔린다","https://img1.daumcdn.net/thumb/R658x0.q70/?fname=https://t1.daumcdn.net/news/202311/11/ked/20231111105803616fkjc.jpg","https://v.daum.net/v/20231111105801616"))

            myRef2.push().setValue(
                ContentModel("서울 아파트 시장에도 찬바람…“우리단지는 올랐나 내렸나","https://img1.daumcdn.net/thumb/R658x0.q70/?fname=https://t1.daumcdn.net/news/202311/11/Edaily/20231111070004865dtfa.jpg","https://v.daum.net/v/20231111070003396"))

            myRef2.push().setValue(
                ContentModel("내년 서울 아파트 입주물량 역대 최저…청약과열 예고","https://img2.daumcdn.net/thumb/R658x0.q70/?fname=https://t1.daumcdn.net/news/202311/11/kedtv/20231111075906693zdzi.jpg","https://v.daum.net/v/20231111075905915"))
         //
            myRef2.push().setValue(
                ContentModel("LH, 공공임대주택 입주자 손해 보상 속도 높인다","https://wimg.mk.co.kr/news/cms/202311/10/news-p.v1.20231110.6e40b4818da243d2b84227317e84f639_P1.jpg","https://www.mk.co.kr/news/realestate/10871841"))

            myRef2.push().setValue(
                ContentModel("오를 땐 ‘왕창’ 내릴 땐 ‘찔끔’…서울 아파트값 37년간 얼마나 올랐나 봤더니","https://wimg.mk.co.kr/news/cms/202311/10/news-p.v1.20231110.1c1376a26f424d5d9ba4cffa84305298_P1.jpg","https://www.mk.co.kr/news/realestate/10871220"))

            myRef2.push().setValue(
                ContentModel("[MK 부동산 단신]성수역세권 준공업지역 오피스빌딩 개발 용지 매각","https://wimg.mk.co.kr/news/cms/202311/10/news-p.v1.20231110.c102be1185cc4a1689a9bad721371465_P1.jpg","https://www.mk.co.kr/news/realestate/10871208"))

            myRef2.push().setValue(
                ContentModel("대한민국 최대 1만2032세대 독립 도시를 선점할 ‘포레온 스테이션5’ 공급 임박","https://wimg.mk.co.kr/news/cms/202311/09/news-p.v1.20231109.ac9f4bc0941b475da40d4a2c043f6144_P1.jpg","https://www.mk.co.kr/news/realestate/10870649"))

            myRef2.push().setValue(
                ContentModel("서울아파트 전셋값 올 최대상승 매매는 강북 이어 노원도 하락","https://wimg.mk.co.kr/news/cms/202311/10/20231110_01160123000001_L00.jpg","https://www.mk.co.kr/news/realestate/10870756"))

            myRef2.push().setValue(
                ContentModel("아파트 거래 씨가 말랐다...콧대높던 강남도 ‘휘청’","https://wimg.mk.co.kr/news/cms/202311/09/news-p.v1.20231109.9892ce79b1da49d3959d96e2bb5d8ea6_P1.jpg","https://www.mk.co.kr/news/realestate/10870948"))

            myRef2.push().setValue(
                ContentModel("줄지 않는 전세사기 불안감에…소형아파트 임대차 계약 절반 월세","https://wimg.mk.co.kr/news/cms/202311/09/news-p.v1.20231109.525c2fb2d9584ba49364cc622e8b2ede_P1.jpeg","https://www.mk.co.kr/news/realestate/10870644"))
*/
        }


        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (dataModel in dataSnapshot.children) {
                    Log.d("ContentsListActivity", dataModel.toString())
                    Log.d("ContentsListActivity", dataModel.key.toString())
                    val item = dataModel.getValue(ContentModel::class.java)
                    items.add(item!!)
                    itemKeyList.add(dataModel.key.toString())
                }
                rvAdapter.notifyDataSetChanged()
                Log.d("ContentListActivity", items.toString())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("ContentsListActivity", "loadPost:onCancelled", databaseError.toException())
            }
        }
        myRef.addValueEventListener(postListener)


        val rv: RecyclerView = findViewById(R.id.rv)

        rv.adapter = rvAdapter

        rv.layoutManager = GridLayoutManager(this, 1)

        getBookmarkData()

    }


    // 북마크 기능 지정
    private fun getBookmarkData(){

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (dataModel in dataSnapshot.children) {
                    bookmarkIdList.add(dataModel.key.toString())
                }
                Log.d("ContentListActivity",  bookmarkIdList.toString())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("ContentsListActivity", "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.bookmarkRef.child(FBAuth.getUid()).addValueEventListener(postListener)

    }
}















 /*
        rvAdapter.itemClick = object : ContentRVAdapter.ItemClick{

            override fun onClick(view: View, position: Int) {
                Toast.makeText(baseContext,items[position].title, Toast.LENGTH_LONG).show()

                var intent = Intent(this@ContentsListActivity, ContentShowActivity::class.java)
                intent.putExtra("url",items[position].webUrl)// url 넘기기
                startActivity(intent)
            }
        } */


/*
               items.add(ContentModel("서울인구 천만인데 내년 새집 1만가구…","https://wimg.mk.co.kr/news/cms/202311/10/news-p.v1.20231110.333adece8da24e2893ee460181afaef6_P1.png","https://www.mk.co.kr/news/realestate/10871892"))
               items.add(ContentModel("title2","https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FznKK4%2Fbtq665AUWem%2FRUawPn5Wwb4cQ8BetEwN40%2Fimg.png","https://philosopher-chan.tistory.com/1236"))
               items.add(ContentModel("title3","https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fbtig9C%2Fbtq65UGxyWI%2FPRBIGUKJ4rjMkI7KTGrxtK%2Fimg.png","https://philosopher-chan.tistory.com/1237"))
               items.add(ContentModel("title4","https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcOYyBM%2Fbtq67Or43WW%2F17lZ3tKajnNwGPSCLtfnE1%2Fimg.png","https://philosopher-chan.tistory.com/1238"))
               items.add(ContentModel("title5","https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fekn5wI%2Fbtq66UlN4bC%2F8NEzlyot7HT4PcjbdYAINk%2Fimg.png","https://philosopher-chan.tistory.com/1239"))
               items.add(ContentModel("title6","https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F123LP%2Fbtq65qy4hAd%2F6dgpC13wgrdsnHigepoVT1%2Fimg.png","https://philosopher-chan.tistory.com/1240"))
               items.add(ContentModel("title7","https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fl2KC3%2Fbtq64lkUJIN%2FeSwUPyQOddzcj6OAkPKZuk%2Fimg.png","https://philosopher-chan.tistory.com/1241"))
               items.add(ContentModel("title8","https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FmBh5u%2Fbtq651yYxop%2FX3idRXeJ0VQEoT1d6Hln30%2Fimg.png","https://philosopher-chan.tistory.com/1242"))
        */

