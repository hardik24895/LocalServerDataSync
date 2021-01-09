package com.kpl.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.kpl.R
import com.kpl.adapter.NotificationAdapter
import com.kpl.adapter.QuestionAnswerAdapter
import kotlinx.android.synthetic.main.activity_notification.*
import kotlinx.android.synthetic.main.activity_question_answer.*
import kotlinx.android.synthetic.main.toolbar_with_back_arrow.*

class NotificationActivity : AppCompatActivity() {


    private var adapter: NotificationAdapter? = null
    lateinit var notificationArray: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        txtTitle.setText("Notifications")

        imgBack.setOnClickListener {
            finish()
        }
        notificationArray = ArrayList()
        setData()

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvNotification.layoutManager = layoutManager
        adapter = NotificationAdapter(this, notificationArray)
        rvNotification.adapter = adapter

    }


    private fun setData() {
        notificationArray.clear()
        notificationArray.add("")
        notificationArray.add("")
        notificationArray.add("")
        notificationArray.add("")
        notificationArray.add("")
        notificationArray.add("")
        notificationArray.add("")
        notificationArray.add("")
        notificationArray.add("")
    }


}