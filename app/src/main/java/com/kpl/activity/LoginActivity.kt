package com.kpl.activity

import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.kpl.R
import com.kpl.database.AppDatabase
import com.kpl.database.Question
import com.kpl.database.Survey
import com.kpl.interfaces.goToActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    var appDatabase: AppDatabase? = null
    var quesitionList: ArrayList<Question>? = null
    var surveyList: ArrayList<Survey>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        quesitionList = ArrayList()
        surveyList = ArrayList()

        appDatabase = AppDatabase.getDatabase(this)!!

        txtLogin.setOnClickListener {


            quesitionList!!.add(Question(1, "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", "S", "1,2,3,4", "123", "123", "213", "123", "123"))
            quesitionList!!.add(Question(2, "Sed dignissim et nulla sed varius.", "M", "1,2,3,4,5,6", "123", "123", "213", "123", "123"))
            quesitionList!!.add(Question(3, "Ut ornare metus nec quam molestie iaculis.", "E", "", "123", "123", "213", "123", "123"))
            quesitionList!!.add(Question(4, "Morbi dignissim non metus ac facilisis.", "S", "1,2,3", "123", "123", "213", "123", "123"))
            quesitionList!!.add(Question(5, "Donec elit nibh, accumsan a facilisis vel, aliquet ac ex.", "S", "1,2,3,4,5", "123", "123", "213", "123", "123"))
            quesitionList!!.add(Question(6, "Ut ornare metus nec quam molestie iaculis.", "M", "1,2,3,4,5,6,7,8,9", "123", "123", "213", "123", "123"))
            quesitionList!!.add(Question(7, "Morbi dignissim non metus ac facilisis.", "E", "", "123", "123", "213", "123", "123"))
            quesitionList!!.add(Question(8, "Sed dignissim et nulla sed varius.", "E", "", "123", "123", "213", "123", "123"))
            quesitionList!!.add(Question(9, "Donec elit nibh, accumsan a facilisis vel, aliquet ac ex.", "M", "1,2,3", "123", "123", "213", "123", "123"))
            quesitionList!!.add(Question(10, "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", "S", "1,2,3,4", "123", "123", "213", "123", "123"))
            quesitionList!!.add(Question(11, "Morbi dignissim non metus ac facilisis.", "M", "1,2,3,4,5,6,7", "123", "123", "213", "123", "123"))


            surveyList!!.add(Survey(1,"1","Servey 1","09/01/2021 10:55:00"))
            surveyList!!.add(Survey(2,"2","Servey 2","09/01/2021 10:55:00"))
            surveyList!!.add(Survey(3,"3","Servey 3","09/01/2021 10:55:00"))
            surveyList!!.add(Survey(4,"4","Servey 4","09/01/2021 10:55:00"))
            surveyList!!.add(Survey(5,"5","Servey 5","09/01/2021 10:55:00"))
            surveyList!!.add(Survey(6,"6","Servey 6","09/01/2021 10:55:00"))


            InsertTaskQuestion(this, quesitionList!!).execute()
            InsertTaskSurvey(this, surveyList!!).execute()
            goToActivity<HomeActivity>()
          //goToActivity<QuestionAnswerActivity>()
        }


    }


    class InsertTaskQuestion(var context: LoginActivity, var quesitionList: ArrayList<Question>) :
        AsyncTask<Void, Void, Boolean>() {
        override fun doInBackground(vararg params: Void?): Boolean {
            context.appDatabase!!.questionDao().insertAllQuestion(quesitionList)
            return true
        }

        override fun onPostExecute(bool: Boolean?) {
            if (bool!!) {
                Toast.makeText(context, "Added to Database", Toast.LENGTH_LONG).show()
            }
        }

    }
    class InsertTaskSurvey(var context: LoginActivity, var surveyList: ArrayList<Survey>) :
        AsyncTask<Void, Void, Boolean>() {
        override fun doInBackground(vararg params: Void?): Boolean {
            context.appDatabase!!.surveyDao().insertAllSurvey(surveyList)
            return true
        }

        override fun onPostExecute(bool: Boolean?) {
            if (bool!!) {
                Toast.makeText(context, "Added to Database", Toast.LENGTH_LONG).show()
            }
        }

    }


}


