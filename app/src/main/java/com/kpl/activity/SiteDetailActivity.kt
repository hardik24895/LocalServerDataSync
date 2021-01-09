package com.kpl.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kpl.R
import com.kpl.interfaces.goToActivity
import kotlinx.android.synthetic.main.activity_site_detail.*
import kotlinx.android.synthetic.main.toolbar_with_back_arrow.*

class SiteDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_site_detail)
        txtTitle.setText("Site")
        imgBack.setOnClickListener { finish() }
        txtSubmit.setOnClickListener {
            goToActivity<QuestionAnswerActivity>()
        }


    }
}