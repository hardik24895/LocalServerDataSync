package com.kpl.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kpl.R
import com.kpl.interfaces.goToActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        txtLogin.setOnClickListener {
            goToActivity<HomeActivity>()
        }
    }
}