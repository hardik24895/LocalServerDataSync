package com.kpl.activity

import android.os.AsyncTask
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kpl.R
import com.kpl.database.AppDatabase
import com.kpl.database.Question
import com.kpl.database.Survey
import com.kpl.database.Employee
import com.kpl.interfaces.goToActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    var appDatabase: AppDatabase? = null
    var quesitionList: ArrayList<Question>? = null
    var surveyList: ArrayList<Survey>? = null
    var employeeList: ArrayList<Employee>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        quesitionList = ArrayList()
        surveyList = ArrayList()
        employeeList = ArrayList()


        appDatabase = AppDatabase.getDatabase(this)!!
        //setStaticData()

        txtLogin.setOnClickListener {
            UserLogin(this, edtUsername.text.toString(), edtPassword.text.toString()).execute()


        }


    }

    private fun setStaticData() {

        quesitionList!!.add(
            Question(
                1,
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                "S",
                "1,2,3,4",
                "123",
                "123",
                "213",
                "123",
                "123"
            )
        )
        quesitionList!!.add(
            Question(
                2,
                "Sed dignissim et nulla sed varius.",
                "M",
                "1,2,3,4,5,6",
                "123",
                "123",
                "213",
                "123",
                "123"
            )
        )
        quesitionList!!.add(
            Question(
                3,
                "Ut ornare metus nec quam molestie iaculis.",
                "E",
                "",
                "123",
                "123",
                "213",
                "123",
                "123"
            )
        )
        quesitionList!!.add(
            Question(
                4,
                "Morbi dignissim non metus ac facilisis.",
                "S",
                "1,2,3",
                "123",
                "123",
                "213",
                "123",
                "123"
            )
        )
        quesitionList!!.add(
            Question(
                5,
                "Donec elit nibh, accumsan a facilisis vel, aliquet ac ex.",
                "S",
                "1,2,3,4,5",
                "123",
                "123",
                "213",
                "123",
                "123"
            )
        )
        quesitionList!!.add(
            Question(
                6,
                "Ut ornare metus nec quam molestie iaculis.",
                "M",
                "1,2,3,4,5,6,7,8,9",
                "123",
                "123",
                "213",
                "123",
                "123"
            )
        )
        quesitionList!!.add(
            Question(
                7,
                "Morbi dignissim non metus ac facilisis.",
                "E",
                "",
                "123",
                "123",
                "213",
                "123",
                "123"
            )
        )
        quesitionList!!.add(
            Question(
                8,
                "Sed dignissim et nulla sed varius.",
                "E",
                "",
                "123",
                "123",
                "213",
                "123",
                "123"
            )
        )
        quesitionList!!.add(
            Question(
                9,
                "Donec elit nibh, accumsan a facilisis vel, aliquet ac ex.",
                "M",
                "1,2,3",
                "123",
                "123",
                "213",
                "123",
                "123"
            )
        )
        quesitionList!!.add(
            Question(
                10,
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                "S",
                "1,2,3,4",
                "123",
                "123",
                "213",
                "123",
                "123"
            )
        )
        quesitionList!!.add(
            Question(
                11,
                "Morbi dignissim non metus ac facilisis.",
                "M",
                "1,2,3,4,5,6,7",
                "123",
                "123",
                "213",
                "123",
                "123"
            )
        )

        surveyList!!.add(
            Survey(
                1,
                1,
                "Servey 1",
                "09/01/2021 10:55:00",
                1,
                "123",
                "213",
                "123",
                "123",
                "123"
            )
        )
        surveyList!!.add(
            Survey(
                2,
                2,
                "Servey 2",
                "09/01/2021 10:55:00",
                1,
                "123",
                "213",
                "123",
                "123",
                "123"
            )
        )
        surveyList!!.add(
            Survey(
                3,
                3,
                "Servey 3",
                "09/01/2021 10:55:00",
                1,
                "123",
                "213",
                "123",
                "123",
                "123"
            )
        )
        surveyList!!.add(
            Survey(
                4,
                4,
                "Servey 4",
                "09/01/2021 10:55:00",
                1,
                "123",
                "213",
                "123",
                "123",
                "123"
            )
        )
        surveyList!!.add(
            Survey(
                5,
                5,
                "Servey 5",
                "09/01/2021 10:55:00",
                1,
                "123",
                "213",
                "123",
                "123",
                "123"
            )
        )
        surveyList!!.add(
            Survey(
                6,
                6,
                "Servey 6",
                "09/01/2021 10:55:00",
                1,
                "123",
                "213",
                "123",
                "123",
                "123"
            )
        )

//        userList!!.add(
//            User(
//                1,
//                1,
//                "test@gmail.com",
//                "123456",
//                "John",
//                "Deo",
//                "9999999999",
//                "Ahmebabad",
//                "Employee",
//                "123",
//                "123",
//                "123",
//                "123",
//                "123"
//            )
//        )
//        userList!!.add(
//            User(
//                2,
//                1,
//                "test@gmail.com",
//                "123456",
//                "Smith",
//                "Jonson",
//                "8888888888",
//                "Ahmebabad",
//                "Employee",
//                "123",
//                "123",
//                "123",
//                "123",
//                "123"
//            )
//        )
//        userList!!.add(
//            User(
//                3,
//                1,
//                "test@gmail.com",
//                "123456",
//                "Test",
//                "User",
//                "7777777777",
//                "Ahmebabad",
//                "Employee",
//                "123",
//                "123",
//                "123",
//                "123",
//                "123"
//            )
//        )
//        userList!!.add(
//            User(
//                4,
//                1,
//                "test@gmail.com",
//                "123456",
//                "Test1",
//                "User",
//                "6666666666",
//                "Ahmebabad",
//                "Employee",
//                "123",
//                "123",
//                "123",
//                "123",
//                "123"
//            )
//        )



        InsertTaskQuestion(this, quesitionList!!).execute()
        InsertTaskSurvey(this, surveyList!!).execute()
     //   InsertTaskUser(this, employeeList!!).execute()

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

//    class InsertTaskUser(var context: LoginActivity, var employeeList: ArrayList<Employee>) :
//        AsyncTask<Void, Void, Boolean>() {
//        override fun doInBackground(vararg params: Void?): Boolean {
//            context.appDatabase!!.employeeDao().insertAllUser(employeeList)
//            return true
//        }
//
//        override fun onPostExecute(bool: Boolean?) {
//            if (bool!!) {
//                Toast.makeText(context, "Added to Database", Toast.LENGTH_LONG).show()
//            }
//        }
//
//    }

    class UserLogin(var context: LoginActivity, var contact: String, var password: String) :
        AsyncTask<Void, Void, Employee>() {
        override fun doInBackground(vararg params: Void?): Employee? {

            if (context.appDatabase!!.employeeDao().checkUser(contact) == null) {
                return null
            } else {

                return context.appDatabase!!.employeeDao().checkUser(contact)
            }
        }

        override fun onPostExecute(employee: Employee?) {

            if (employee !== null) {
                Toast.makeText(context, "Login success" , Toast.LENGTH_LONG).show()
                context.goToActivity<HomeActivity>()
            } else {
                Toast.makeText(context, "Login failed", Toast.LENGTH_LONG).show()
            }
        }

    }

}


