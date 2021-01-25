package com.kpl.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.kpl.R
import kotlinx.android.synthetic.main.activity_information.*
import kotlinx.android.synthetic.main.toolbar_with_back_arrow.*

class InformationActivity : AppCompatActivity() {

    var infoURL: String = "http://societyfy.in/kppl/api/service/getPage?PageName="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)

        txtTitle.text = intent.getStringExtra("Title")
        intent.getStringExtra("Desc")
        imgBack.setOnClickListener { finish() }

        webview.settings.javaScriptEnabled = true
        webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url!!)
                return true
            }
        }
        webview.loadUrl(infoURL + intent.getStringExtra("Desc"))
    }
}