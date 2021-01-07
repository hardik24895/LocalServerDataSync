package com.kpl.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kpl.R
import com.kpl.adapter.QuestionAnswerAdapter
import kotlinx.android.synthetic.main.activity_question_answer.*
import kotlinx.android.synthetic.main.toolbar_with_back_arrow.*


class QuestionAnswerActivity : AppCompatActivity() {

    private var adapter: QuestionAnswerAdapter? = null
    lateinit var queAnsArray: ArrayList<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_answer)

        txtTitle.setText("Question Answer")

        imgBack.setOnClickListener {
            finish()
        }
        queAnsArray = ArrayList()
        setData()

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
       //val snapHelper: SnapHelper = PagerSnapHelper()
       rvQueAns.setLayoutManager(layoutManager)
       //snapHelper.attachToRecyclerView(rvQueAns)
        rvQueAns.layoutManager = layoutManager
        adapter = QuestionAnswerAdapter(this, queAnsArray)
        rvQueAns.adapter = adapter

    }


    private fun setData() {
        queAnsArray.clear()
        queAnsArray.add("")
        queAnsArray.add("")
        queAnsArray.add("")
        queAnsArray.add("")
        queAnsArray.add("")
        queAnsArray.add("")
        queAnsArray.add("")
        queAnsArray.add("")
        queAnsArray.add("")
    }




}