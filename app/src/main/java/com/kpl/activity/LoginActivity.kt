package com.kpl.activity

import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.kpl.R
import com.kpl.database.AppDatabase
import com.kpl.database.Employee
import com.kpl.database.Question
import com.kpl.database.Survey
import com.kpl.interfaces.goToActivity
import com.kpl.utils.SessionManager.Companion.KEY_IS_LOGIN
import com.kpl.utils.SessionManager.Companion.SPAddress
import com.kpl.utils.SessionManager.Companion.SPEmailID
import com.kpl.utils.SessionManager.Companion.SPFirstName
import com.kpl.utils.SessionManager.Companion.SPLastName
import com.kpl.utils.SessionManager.Companion.SPMobileNo
import com.kpl.utils.SessionManager.Companion.SPPassword
import com.kpl.utils.SessionManager.Companion.SPRoleID
import com.kpl.utils.SessionManager.Companion.SPUserID
import com.kpl.utils.SessionManager.Companion.SPUserType
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : BaseActivity() {


    var quesitionList: ArrayList<Question>? = null
    var surveyList: ArrayList<Survey>? = null
    var employeeList: ArrayList<Employee>? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        quesitionList = ArrayList()
        surveyList = ArrayList()
        employeeList = ArrayList()
        appDatabase = AppDatabase.getDatabase(this)!!


        txtLogin.setOnClickListener {

            if (isvalid()) {
                UserLogin(this, edtUsername.text.toString(), edtPassword.text.toString()).execute()
            }
        }

    }

    private fun isvalid(): Boolean {
        if (edtUsername.text.toString().isEmpty()) {
            edtUsername.error = "Enter Contact"
            edtUsername.requestFocus()
            return false
        } else if (edtPassword.text.toString().isEmpty()) {
            edtPassword.error = "Enter Password"
            edtPassword.requestFocus()
            return false
        }


        return true
    }


    inner class UserLogin(var context: LoginActivity, var contact: String, var password: String) :
        AsyncTask<Void, Void, Employee>() {
        override fun doInBackground(vararg params: Void?): Employee? {

            if (context.appDatabase!!.employeeDao().checkUser(contact, password) == null) {
                return null
            } else {

                return context.appDatabase!!.employeeDao().checkUser(contact, password)
            }
        }

        override fun onPostExecute(employee: Employee?) {

            if (employee !== null) {

                session.storeDataByKey(SPUserID, employee.UserID.toString())
                session.storeDataByKey(SPRoleID, employee.RoleID.toString())
                session.storeDataByKey(SPEmailID, employee.EmailID.toString())
                session.storeDataByKey(SPPassword, employee.Password.toString())
                session.storeDataByKey(SPFirstName, employee.FirstName.toString())
                session.storeDataByKey(SPLastName, employee.LastName.toString())
                session.storeDataByKey(SPMobileNo, employee.MobileNo.toString())
                session.storeDataByKey(SPAddress, employee.Address.toString())
                session.storeDataByKey(SPUserType, employee.UserType.toString())
                session.storeDataByKey(SPUserType, employee.UserType.toString())
                session.storeDataByKey(KEY_IS_LOGIN, true)

                Toast.makeText(context, "Login success", Toast.LENGTH_LONG).show()

                context.goToActivity<HomeActivity>()
                finish()
            } else {
                Toast.makeText(context, "Login failed", Toast.LENGTH_LONG).show()
            }
        }


    }


}


