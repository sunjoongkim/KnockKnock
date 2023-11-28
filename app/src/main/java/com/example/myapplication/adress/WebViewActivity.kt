package com.example.myapplication.adress

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.Toast
import com.example.myapplication.R

class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adress_api) // webView(URL)를 띄우는 xml파일을 연결해준다.

        val getUrl = intent.getStringExtra("url")// 넘어온 url 주소 받기
        Toast.makeText(this,getUrl,Toast.LENGTH_LONG).show()

        val webView : WebView = findViewById(R.id.webView_address)
        webView.loadUrl(getUrl.toString())

    }
}