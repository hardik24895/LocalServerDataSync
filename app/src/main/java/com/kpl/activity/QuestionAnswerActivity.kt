package com.kpl.activity

import android.os.AsyncTask
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.kpl.R
import com.kpl.adapter.QuestionAnswerAdapter
import com.kpl.database.AppDatabase
import com.kpl.database.Question
import kotlinx.android.synthetic.main.activity_question_answer.*
import kotlinx.android.synthetic.main.toolbar_with_back_arrow.*


class QuestionAnswerActivity : AppCompatActivity() {

    private var adapter: QuestionAnswerAdapter? = null
    var queAnsArray: ArrayList<Question>? = null
    var appDatabase: AppDatabase? = null
    var list: List<Question>? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_answer)

        txtTitle.setText("Question Answer")

        imgBack.setOnClickListener {
            finish()
        }
        appDatabase = AppDatabase.getDatabase(this)!!
        queAnsArray = ArrayList()



        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvQueAns.setLayoutManager(layoutManager)
        rvQueAns.layoutManager = layoutManager
        adapter = QuestionAnswerAdapter(this, queAnsArray!!)
        rvQueAns.adapter = adapter

        GetDataFromDB(this).execute()

    }

    class GetDataFromDB(var context: QuestionAnswerActivity) :
        AsyncTask<Void, Void, List<Question>>() {
        override fun doInBackground(vararg params: Void?): List<Question>? {

            return context.appDatabase?.questionDao()?.getAllQuestion()

        }


        override fun onPostExecute(bool: List<Question>) {

            context.list =bool
            context.queAnsArray?.addAll(context.list!!)

            context.adapter?.notifyDataSetChanged()


        }

    }


}

