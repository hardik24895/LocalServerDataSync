package com.kpl.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kpl.R
import kotlinx.android.synthetic.main.toolbar_with_back_arrow.*

class InformationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)

        txtTitle.text = intent.getStringExtra("Title")
        imgBack.setOnClickListener { finish() }
    }
}