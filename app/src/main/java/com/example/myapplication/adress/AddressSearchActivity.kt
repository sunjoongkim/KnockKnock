package com.example.myapplication.adress

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityAddressSearchBinding
import com.google.firebase.database.DatabaseReference

class AddressSearchActivity : AppCompatActivity() {

    lateinit var myRef : DatabaseReference

    private var webView: WebView? = null
    var TAG = "AddressApiActivity";
    lateinit var activityAddressApiBinding : ActivityAddressSearchBinding

    inner class MyJavaScriptInterface {
        @JavascriptInterface
        fun processDATA(data: String?) {
            val intent = Intent()
            intent.putExtra("address", data)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val blogspot = "https://jusoapi.blogspot.com/2023/11/blog-post.html"

        activityAddressApiBinding = ActivityAddressSearchBinding.inflate(layoutInflater)
        setContentView(activityAddressApiBinding.root)
        activityAddressApiBinding.webView!!.settings.javaScriptEnabled = true
        activityAddressApiBinding.webView!!.addJavascriptInterface(MyJavaScriptInterface(), "Android")
        activityAddressApiBinding.webView!!.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                // 위 웹페이지가 load가 끝나면 코드에서 작성했던 script 을 호출
                view.loadUrl("javascript:sample2_execDaumPostcode();")
            }
        }
        activityAddressApiBinding.webView!!.loadUrl(blogspot)

        Handler(Looper.getMainLooper()).postDelayed({
            activityAddressApiBinding.webView.visibility = View.VISIBLE
        }, 4000)
    }
}