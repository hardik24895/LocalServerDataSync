package com.kpl.activity

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.kpl.R
import com.kpl.database.Employee
import com.kpl.database.Project
import com.kpl.database.Question
import com.kpl.database.Survey
import com.kpl.extention.showAlert
import com.kpl.interfaces.goToActivity
import com.kpl.model.EmployeeItem
import com.kpl.model.GetMasterDataModel
import com.kpl.model.ProjectItem
import com.kpl.model.QuestionItem
import com.kpl.network.CallbackObserver
import com.kpl.network.Networking
import com.kpl.network.addTo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONException
import org.json.JSONObject

class Splashctivity : BaseActivity() {

    var employeeArray: ArrayList<Employee>? = null
    var quesitionArray: ArrayList<Question>? = null
    var surveyArray: ArrayList<Survey>? = null
    var projectArray: ArrayList<Project>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashctivity)

        employeeArray = ArrayList()
        quesitionArray = ArrayList()
        surveyArray = ArrayList()
        projectArray = ArrayList()


        if (session.isLoggedIn) {
            goToActivity<HomeActivity>()
            finish()
        } else {
            getMasterDataFromServer()
        }
    }

    fun getMasterDataFromServer() {
        var result = ""
        showProgressbar()
        try {

            val jsonBody = JSONObject()
            val jsonObject = JSONObject()
            jsonObject.put(
                "Synctime",
                "2021-01-01 10:00:00"
            )

            // jsonBody.put("body", jsonObject)

            result = Networking.setParentJsonData("getInsertMaster", jsonObject)

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        Networking
            .with(this)
            .getServices()
            .getMasterData(Networking.wrapParams(result))//wrapParams Wraps parameters in to Request body Json format
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : CallbackObserver<GetMasterDataModel>() {
                override fun onSuccess(response: GetMasterDataModel) {
                    val data = response.data
                    hideProgressbar()
                    if (data != null) {
                        Log.d("TAG", "onSuccess: " + data.toString())

                        for (iteam in data.employee.indices) {
                            val emp: EmployeeItem = data.employee.get(iteam)
                            employeeArray?.add(
                                Employee(
                                    emp.userID?.toInt(),
                                    emp.roleID?.toInt(),
                                    emp.emailID.toString(),
                                    emp.password.toString(),
                                    emp.firstName.toString(),
                                    emp.lastName.toString(),
                                    emp.mobileNo.toString(),
                                    emp.address.toString(),
                                    emp.userType.toString(),
                                    emp.isDeleted.toString(),
                                    emp.createdBy.toString(),
                                    emp.createdDate.toString(),
                                    emp.modifiedBy.toString(),
                                    emp.modifiedDate.toString(),
                                    emp.status.toString()
                                )
                            )
                        }

                        for (iteam in data.project.indices) {
                            val project: ProjectItem = data.project.get(iteam)
                            projectArray?.add(
                                Project(
                                    project.projectID?.toInt(),
                                    project.CompanyName?.toString(),
                                    project.title.toString(),
                                    project.address.toString(),
                                    project.mobileNo.toString(),
                                    project.type.toString(),
                                    project.status.toString(),
                                    project.createdBy.toString(),
                                    project.createdDate.toString(),
                                    project.modifiedBy.toString(),
                                    project.modifiedDate.toString()
                                )
                            )
                        }

                        for (iteam in data.question.indices) {
                            val question: QuestionItem = data.question.get(iteam)
                            quesitionArray?.add(
                                Question(
                                    question.questionID?.toInt(),
                                    question.question.toString(),
                                    question.questionoption.toString(),
                                    question.type.toString(),
                                    question.createdBy.toString(),
                                    question.createdDate.toString(),
                                    question.modifiedBy.toString(),
                                    question.modifiedDate.toString(),
                                    question.status.toString()
                                )
                            )
                        }

                        employeeArray?.let {
                            projectArray?.let { it1 ->
                                quesitionArray?.let { it2 ->
                                    InsertTaskUser(
                                        this@Splashctivity,
                                        it, it1, it2
                                    ).execute()
                                }
                            }
                        }
                        //projectArray?.let { InsertTaskUser(this@Splashctivity, it).execute() }

                        goToActivity<LoginActivity>()
                        finish()
                    } else {
                        showAlert(getString(R.string.something_went_wrong))
                    }
                }

                override fun onFailed(code: Int, message: String) {
                    showAlert(message)
                    hideProgressbar()
                }

            }).addTo(autoDisposable)
    }

    class InsertTaskUser(
        var context: Splashctivity,
        var employeeList: ArrayList<Employee>,
        var projectList: ArrayList<Project>,
        var questionList: ArrayList<Question>
    ) :
        AsyncTask<Void, Void, Boolean>() {
        override fun doInBackground(vararg params: Void?): Boolean {
            context.appDatabase!!.employeeDao().insertAllUser(employeeList)
            context.appDatabase!!.projectDao().insertAllProject(projectList)
            context.appDatabase!!.questionDao().insertAllQuestion(questionList)
            return true
        }

        override fun onPostExecute(bool: Boolean?) {
            if (bool!!) {
                //Toast.makeText(context, "Added to Database", Toast.LENGTH_LONG).show()
            }
        }

    }
}







