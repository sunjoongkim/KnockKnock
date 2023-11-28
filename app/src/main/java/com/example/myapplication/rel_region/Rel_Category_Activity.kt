package com.example.myapplication.rel_region

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.rel_build.Apart_gyeongi_Activity
import com.example.myapplication.rel_build.Apart_incheon_Activity
import com.example.myapplication.rel_build.Apart_province_Activity
import com.example.myapplication.rel_build.Apart_seoul_Activity
import com.example.myapplication.rel_build.Complex_Rest_Activity
import com.example.myapplication.rel_build.Complex_cafe_Activity
import com.example.myapplication.rel_build.Complex_convenience_Activity
import com.example.myapplication.rel_build.Complex_helth_Activity
import com.example.myapplication.rel_build.Complex_play_Activity
import com.example.myapplication.rel_build.Complex_shopping_Activity
import com.example.myapplication.rel_build.Office_gyeongi_Activity
import com.example.myapplication.rel_build.Office_incheon_Activity
import com.example.myapplication.rel_build.Office_province_Activity
import com.example.myapplication.rel_build.Office_seoul_Activity
import com.example.myapplication.rel_build.OneRoom_gyeongi_Activity
import com.example.myapplication.rel_build.OneRoom_incheon_Activity
import com.example.myapplication.rel_build.OneRoom_province_Activity
import com.example.myapplication.rel_build.OneRoom_seoul_Activity

class Rel_Category_Activity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rel_category)

        // 월세 지역
        findViewById<TextView>(R.id.Month_seoul).setOnClickListener {
            startActivity(Intent(this, Month_seoul_Activity::class.java))
        }
        findViewById<TextView>(R.id.Month_gyeongi).setOnClickListener {
            startActivity(Intent(this, Month_gyeongi_Activity::class.java))
        }
        findViewById<TextView>(R.id.Month_incheon).setOnClickListener {
            startActivity(Intent(this, Month_incheon_Activity::class.java ))
        }
        findViewById<TextView>(R.id.Month_province).setOnClickListener {
            startActivity(Intent(this, Month_province_Activity::class.java ))
        }


        // 전세 지역
        findViewById<TextView>(R.id.jeonse_seoul).setOnClickListener {
            startActivity(Intent(this, Jeonse_seoul_Activity::class.java ))
        }
        findViewById<TextView>(R.id.jeonse_gyeongi).setOnClickListener {
            startActivity(Intent(this, Jeonse_gyeongi_Activity::class.java ))
        }
        findViewById<TextView>(R.id.jeonse_incheon).setOnClickListener {
            startActivity(Intent(this, Jeonse_incheon_Activity::class.java ))
        }
        findViewById<TextView>(R.id.jeonse_province).setOnClickListener {
            startActivity(Intent(this, Jeonse_province_Activity::class.java ))
        }

        // 매매 지역
        findViewById<TextView>(R.id.res_seoul).setOnClickListener {
            startActivity(Intent(this, Res_seoul_Activity::class.java ))
        }
        findViewById<TextView>(R.id.res_gyeongi).setOnClickListener {
            startActivity(Intent(this, Res_gyeongi_Activity::class.java ))
        }
        findViewById<TextView>(R.id.res_incheon).setOnClickListener {
            startActivity(Intent(this, Res_incheon_Activity::class.java ))
        }
        findViewById<TextView>(R.id.res_province).setOnClickListener {
            startActivity(Intent(this, Res_province_Activity::class.java ))
        }

        //건물유형/지역별
        findViewById<TextView>(R.id.oneroom_seoul).setOnClickListener {
            startActivity(Intent(this, OneRoom_seoul_Activity::class.java ))
        }
        findViewById<TextView>(R.id.oneroom_gyeongi).setOnClickListener {
            startActivity(Intent(this, OneRoom_gyeongi_Activity::class.java ))
        }
        findViewById<TextView>(R.id.oneroom_incheon).setOnClickListener {
            startActivity(Intent(this, OneRoom_incheon_Activity::class.java ))
        }
        findViewById<TextView>(R.id.oneroom_province).setOnClickListener {
            startActivity(Intent(this, OneRoom_province_Activity::class.java ))
        }
        findViewById<TextView>(R.id.apart_seoul).setOnClickListener {
            startActivity(Intent(this, Apart_seoul_Activity::class.java ))
        }
        findViewById<TextView>(R.id.apart_gyeongi).setOnClickListener {
            startActivity(Intent(this, Apart_gyeongi_Activity::class.java ))
        }
        findViewById<TextView>(R.id.apart_incheon).setOnClickListener {
            startActivity(Intent(this, Apart_incheon_Activity::class.java ))
        }
        findViewById<TextView>(R.id.apart_province).setOnClickListener {
            startActivity(Intent(this, Apart_province_Activity::class.java ))
        }
        findViewById<TextView>(R.id.opi_seoul).setOnClickListener {
            startActivity(Intent(this, Office_seoul_Activity::class.java ))
        }
        findViewById<TextView>(R.id.opi_gyeongi).setOnClickListener {
            startActivity(Intent(this, Office_gyeongi_Activity::class.java ))
        }
        findViewById<TextView>(R.id.opi_incheon).setOnClickListener {
            startActivity(Intent(this, Office_incheon_Activity::class.java ))
        }
        findViewById<TextView>(R.id.opi_province).setOnClickListener {
            startActivity(Intent(this, Office_province_Activity::class.java ))
        }
        findViewById<TextView>(R.id.Complex_Rest_Activity).setOnClickListener {
            startActivity(Intent(this, Complex_Rest_Activity::class.java ))
        }
        findViewById<TextView>(R.id.Complex_cafe_Activity).setOnClickListener {
            startActivity(Intent(this, Complex_cafe_Activity::class.java ))
        }
        findViewById<TextView>(R.id.Complex_convenience_Activity).setOnClickListener {
            startActivity(Intent(this, Complex_convenience_Activity::class.java ))
        }
        findViewById<TextView>(R.id.Complex_play_Activity).setOnClickListener {
            startActivity(Intent(this, Complex_play_Activity::class.java ))
        }
        findViewById<TextView>(R.id.Complex_helth_Activity).setOnClickListener {
            startActivity(Intent(this, Complex_helth_Activity::class.java ))
        }
        findViewById<TextView>(R.id.Complex_shopping_Activity).setOnClickListener {
            startActivity(Intent(this, Complex_shopping_Activity::class.java ))
        }

    }
}