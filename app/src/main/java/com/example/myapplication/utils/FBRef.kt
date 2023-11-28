package com.example.myapplication.utils

import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class FBRef {

    companion object{
        //데이터베이스에 테이블명을 생성, 지정하는 코드

        val database = Firebase.database

        val category1 = database.getReference("contents")
        val category2 = database.getReference("contents2")


        val bookmarkRef = database.getReference("bookmark_list") // 데이터베이스에 bookmark_list(북마크 데베)라는 테이블 생성

        // 커뮤니티 게시판 데베 생성
        val boardRef = database.getReference("board") // 데이터베이스에 board(게시판 데베)라는 테이블 생성

        val commentRef = database.getReference("comment") // 데이터베이스에 comment(댓글 데베)라는 테이블 생성

        val rellistRef = database.getReference("Rel_list") // 데이터베이스에 Rel_list(매물 데베)라는 테이블 생성


        val likeRef = database.getReference("like_list") // 매물 찜 리스트 생성
        // 전체 매물 게시판 데베 생성
        val boardRef2 = database.getReference("board2")


    }
}