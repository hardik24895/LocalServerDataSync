package com.kpl.fragment

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kpl.R
import com.kpl.activity.InformationActivity
import com.kpl.activity.LoginActivity
import com.kpl.activity.NotificationActivity
import com.kpl.database.*
import com.kpl.dialog.YesNoActionDailog
import com.kpl.extention.invisible
import com.kpl.extention.showAlert
import com.kpl.interfaces.goToActivity
import com.kpl.interfaces.goToActivityAndClearTask
import com.kpl.model.*
import com.kpl.network.CallbackObserver
import com.kpl.network.Networking
import com.kpl.network.addTo
import com.kpl.utils.Constant
import com.kpl.utils.SessionManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.android.synthetic.main.toolbar_with_back_arrow.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SettingFragment : BaseFragment() {

    lateinit var intent: Intent

    var ansArray: ArrayList<SurveyAnswer>? = null
    var surveyArray: ArrayList<Survey>? = null
    var employeeArray: ArrayList<Employee>? = null
    var quesitionArray: ArrayList<Question>? = null
    var projectArray: ArrayList<Project>? = null
    var categoryArray: ArrayList<Category>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_setting, container, false)
        intent = Intent(requireContext(), InformationActivity::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imgBack.invisible()
        txtTitle.text = "Setting"
        clickEvent()
        employeeArray = ArrayList()
        projectArray = ArrayList()
        quesitionArray = ArrayList()
        ansArray = ArrayList()
        surveyArray = ArrayList()
        categoryArray = ArrayList()

        relaySendData.setOnClickListener {
            requireContext().let { GetDataFromDB(it).execute() }

        }
        relayGetData.setOnClickListener {
            requireContext().let { CheckLocalServerExist(it).execute() }

        }

    }

    inner class CheckLocalServerExist(var context: Context) :
        AsyncTask<Void, Void, Boolean>() {
        override fun doInBackground(vararg params: Void?): Boolean? {
            surveyArray?.addAll(appDatabase!!.surveyDao().getAllPendingSurvey())

            return true
        }

        override fun onPostExecute(result: Boolean?) {

            if (surveyArray?.size!! > 0) {
                val dialog = YesNoActionDailog.newInstance(requireContext(),
                    object : YesNoActionDailog.onItemClick {
                        override fun onItemCLicked() {
                            getMasterDataFromServer()
                        }
                    })
                val bundle = Bundle()
                bundle.putString(Constant.TITLE, this@SettingFragment.getString(R.string.app_name))
                bundle.putString(
                    Constant.TEXT,
                    this@SettingFragment.getString(R.string.msg_get_data_from_server)
                )
                dialog.arguments = bundle
                dialog.show(childFragmentManager, "YesNO")
            } else
                getMasterDataFromServer()

        }
    }

    inner class GetDataFromDB(var context: Context) :
        AsyncTask<Void, Void, Boolean>() {
        override fun doInBackground(vararg params: Void?): Boolean? {
            ansArray?.addAll(appDatabase!!.surveyAnswerDao().getAll())
            surveyArray?.addAll(appDatabase!!.surveyDao().getAllPendingSurvey())

            return true
        }

        override fun onPostExecute(result: Boolean?) {

            if (surveyArray?.size!! > 0)
                SendDatatoServer()
        }
    }


    fun clickEvent() {
        relayNotification.setOnClickListener {
            goToActivity<NotificationActivity>()
        }

        relayPrivacy.setOnClickListener {
            intent.putExtra("Title", "Privacy Policy")
            intent.putExtra("Desc", "PrivacyPolicy")
            startActivity(intent)
        }

        relayAboutus.setOnClickListener {
            intent.putExtra("Title", "About Us")
            intent.putExtra("Desc", "AboutUS")
            startActivity(intent)
        }
        relayTerms.setOnClickListener {
            intent.putExtra("Title", "Terms And Condition")
            intent.putExtra("Desc", "TermandCondition")
            startActivity(intent)
        }

        relayLogout.setOnClickListener {
            val dialog = YesNoActionDailog.newInstance(requireContext(),
                object : YesNoActionDailog.onItemClick {
                    override fun onItemCLicked() {
                        //   val mobile=  session.getDataByKey(Constant.MOBILE)
                        //    val code=  session.getDataByKey(Constant.PHONE_CODE)
                        session.clearSession()
                        //  session.storeDataByKey(Constant.USER_ID, mobile)
                        //  session.storeDataByKey(Constant.PHONE_CODE, code)
                        goToActivityAndClearTask<LoginActivity>()
                    }
                })
            val bundle = Bundle()
            bundle.putString(Constant.TITLE, this.getString(R.string.app_name))
            bundle.putString(Constant.TEXT, this.getString(R.string.msg_logout))
            dialog.arguments = bundle
            dialog.show(childFragmentManager, "YesNO")
        }
    }


    fun SendDatatoServer() {

        var result = ""
        showProgressbar()

        try {

            val jsonBody = JSONObject()
            val jsonObject = JSONObject()
            val jsonSurveyArray: JSONArray? = JSONArray()
            val jsonAnswerArray: JSONArray? = JSONArray()


            for (i in 0 until ansArray?.size!!) {
                val jGroup = JSONObject() // /sub Object
                try {

                    jGroup.put("QuestionID", ansArray?.get(i)?.QuestionID)
                    jGroup.put("Answer", ansArray?.get(i)?.Answer)
                    jGroup.put("UserID", session.getDataByKey(SessionManager.SPUserID))
//
                    jsonAnswerArray?.put(jGroup)

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }


            for (i in 0 until surveyArray?.size!!) {
                val jGroup = JSONObject() // /sub Object
                try {
                    jGroup.put("ProjectID", surveyArray?.get(i)?.ProjectID)
                    jGroup.put("Title", surveyArray?.get(i)?.Title)
                    jGroup.put("SurveyDate", convertDateFormate(surveyArray?.get(i)?.SurveyDate))
                    jGroup.put("UserID", session.getDataByKey(SessionManager.SPUserID))

                    jsonSurveyArray?.put(jGroup)

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            jsonObject.put("survey", jsonSurveyArray)
            jsonObject.put("surveyanswer", jsonAnswerArray)

            // jsonBody.put("body", jsonObject)

            result = Networking.setParentJsonData("addSurvey", jsonObject)

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        Networking
            .with(requireContext())
            .getServices()
            .SendServeyToServer(Networking.wrapParams(result))//wrapParams Wraps parameters in to Request body Json format
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : CallbackObserver<SendSurverDataToServer>() {
                override fun onSuccess(response: SendSurverDataToServer) {
                    val data = response.data
                    hideProgressbar()
                    if (data != null) {
                        UpdateSurveyStatus(requireContext()).execute()

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

    private fun convertDateFormate(surveyDate: String?): String {


        val input = SimpleDateFormat("dd/MM/yyyy")
        val output = SimpleDateFormat("yyyy-MM-dd")
        try {
            val getAbbreviate = input.parse(surveyDate)    // parse input
            return output.format(getAbbreviate)    // format output
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return ""
    }

    inner class UpdateSurveyStatus(var context: Context) :
        AsyncTask<Void, Void, Boolean>() {
        override fun doInBackground(vararg params: Void?): Boolean? {

            appDatabase!!.surveyDao().uploadDataDone()
            appDatabase!!.surveyAnswerDao().deleteAllReocord();

            return true
        }

        override fun onPostExecute(result: Boolean?) {


        }
    }

    fun getMasterDataFromServer() {
        var result = ""
        showProgressbar()
        try {

            val jsonObject = JSONObject()
            jsonObject.put("Synctime", session.getDataByKey(SessionManager.SPSyncData))

            // jsonBody.put("body", jsonObject)

            result = Networking.setParentJsonData("getInsertMaster", jsonObject)

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        Networking
            .with(requireContext())
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
                            quesitionArray?.let {
                                appDatabase!!.questionDao().insertAllQuestion(it)
                            }
                            categoryArray?.let { appDatabase!!.categoryDao().insertAllCategory(it) }


                            Handler(mainLooper).post {


                                val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                                val currentDate = sdf.format(Date())
                                session.storeDataByKey(SessionManager.SPSyncData, currentDate)

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