package com.kpl.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import com.kpl.R
import com.kpl.database.*
import com.kpl.extention.showAlert
import com.kpl.interfaces.goToActivity
import com.kpl.model.*
import com.kpl.network.CallbackObserver
import com.kpl.network.Networking
import com.kpl.network.addTo
import com.kpl.utils.SessionManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Splashctivity : BaseActivity() {

    var employeeArray: ArrayList<Employee>? = null
    var categoryArray: ArrayList<Category>? = null
    var quesitionArray: ArrayList<Question>? = null
    var surveyArray: ArrayList<Survey>? = null
    var projectArray: ArrayList<Project>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashctivity)

        employeeArray = ArrayList()
        categoryArray = ArrayList()
        quesitionArray = ArrayList()
        surveyArray = ArrayList()
        projectArray = ArrayList()


        if (session.isLoggedIn) {

            Handler().postDelayed(Runnable {
                goToActivity<HomeActivity>()
                finish()
            },2500)

        } else {
            getMasterDataFromServer()
        }
    }

    fun getMasterDataFromServer() {
        var result = ""
        showProgressbar()
        try {


            val jsonObject = JSONObject()
            jsonObject.put("Synctime", "2001-01-01 10:00:00")

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

                        if (!data.employee.isEmpty())
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
                        if (!data.project.isEmpty())
                            for (iteam in data.project.indices) {
                                val project: ProjectItem = data.project.get(iteam)
                                projectArray?.add(
                                    Project(
                                        project.projectID?.toInt(),
                                        project.companyName.toString(),
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
                        if (!data.question.isEmpty())
                            for (iteam in data.question.indices) {
                                val question: QuestionItem = data.question.get(iteam)
                                quesitionArray?.add(
                                    Question(
                                        question.questionID?.toInt(),
                                        question.question.toString(),
                                        question.categoryID.toString(),
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

                        if (!data.category.isEmpty())
                            for (iteam in data.category.indices) {
                                val category: CategoryItem = data.category.get(iteam)
                                categoryArray?.add(
                                    Category(
                                        category.categoryID?.toInt(),
                                        category.parentID?.toInt(),
                                        category.category.toString(),
                                        category.createdBy.toString(),
                                        category.createdDate.toString(),
                                        category.modifiedBy.toString(),
                                        category.modifiedDate.toString(),
                                        category.status.toString()
                                    )
                                )
                            }


                        val mainLooper = Looper.getMainLooper()
                        Thread(Runnable {
                            employeeArray?.let { appDatabase!!.employeeDao().insertAllUser(it) }
                            projectArray?.let { appDatabase!!.projectDao().insertAllProject(it) }
                            quesitionArray?.let { appDatabase!!.questionDao().insertAllQuestion(it) }
                            categoryArray?.let { appDatabase!!.categoryDao().insertAllCategory(it) }

                            val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                            val currentDate = sdf.format(Date())
                            session.storeDataByKey(SessionManager.SPSyncData,currentDate)

                            Handler(mainLooper).post {


                                goToActivity<LoginActivity>()
                                finish()
                            }
                        }).start()


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

}







