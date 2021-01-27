package com.kpl.activity

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.kpl.R
import com.kpl.database.Project
import com.kpl.database.Survey
import com.kpl.utils.SessionManager
import kotlinx.android.synthetic.main.activity_site_detail.*
import kotlinx.android.synthetic.main.toolbar_with_back_arrow.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList


class SiteDetailActivity : BaseActivity() {

    var SurveyID: String? = null
    var ProjectID: String? = null
    var CompanyName: String? = null
    var Title: String? = null
    var Address: String? = null
    var MobileNo: String? = null
    var Type: String? = null
    var Status: String? = null
    var CreatedBy: String? = null
    var CreatedDate: String? = null
    var ModifiedBy: String? = null
    var ModifiedDate: String? = null
    var intent1: Intent? = null

    val myCalendar: Calendar = Calendar.getInstance()
    var projectArray: ArrayList<Project>? = null
    var AddressArray: ArrayList<String>? = null
    var adapterSiteName: ArrayAdapter<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_site_detail)
//        txtTitle.setText("Site")
//        imgBack.setOnClickListener { finish() }
//        txtSubmit.setOnClickListener {
//
//
//            val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
//            val currentDate = sdf.format(Date())
//            AddSurvey(
//                this,
//                Survey(
//                    null,
//                    ProjectID,
//                    Title,
//                    txtDate.text.toString(),
//                    session.getDataByKey(SessionManager.SPUserID, ""),
//                    CreatedBy,
//                    currentDate,
//                    ModifiedBy,
//                    ModifiedDate,
//                    Status
//                )
//            ).execute()
//
//
//        }
//        projectArray = ArrayList()
//        AddressArray = ArrayList()
//        txtAddress.isSelected = true
//
//        adapterSiteName = ArrayAdapter(this, R.layout.custom_spinner, AddressArray!!)
//        autoSiteName.setAdapter(adapterSiteName)
//        autoSiteName.threshold = 0
//        autoSiteName.onItemClickListener =
//            OnItemClickListener { parent, arg1, position, arg3 ->
//
//
//                val list: List<String> =
//                    Pattern.compile(", ").split(parent.getItemAtPosition(position).toString())
//                        .toList()
//
//                for (item in projectArray!!) {
//                    if (list.get(0).equals(item.CompanyName) && list.get(1).equals(item.Title)) {
//                        ProjectID = item.ProjectID.toString()
//                        CompanyName = item.CompanyName
//                        Title = item.Title
//                        Address = item.Address
//                        MobileNo = item.MobileNo
//                        Type = item.Type
//                        Status = item.Status
//                        CreatedBy = item.CreatedBy
//                        CreatedDate = item.CreatedDate
//                        ModifiedBy = item.ModifiedBy
//                        ModifiedDate = item.ModifiedDate
//
//                        txtAddress.setText(item.Address)
//                        break
//                    }
//                }
//
//
//            }
//
//        updateLabel()
//
//        getProject(this).execute()
//
//
//        txtDate.setOnClickListener {
//
//
//            val datePickerDialog = DatePickerDialog(
//                this@SiteDetailActivity,
//                date,
//                myCalendar.get(Calendar.YEAR),
//                myCalendar.get(Calendar.MONTH),
//                myCalendar.get(Calendar.DAY_OF_MONTH)
//            )
//            //  datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
//            datePickerDialog.show()
//
//        }
//
//    }
//
//
//    inner class AddSurvey(var context: SiteDetailActivity, var survey: Survey) :
//        AsyncTask<Void, Void, Long>() {
//        override fun doInBackground(vararg params: Void?): Long {
//
//
//            return context.appDatabase!!.surveyDao().insertSurvey(survey)
//        }
//
//        override fun onPostExecute(result: Long?) {
//
//            intent1 = Intent(context, QuestionAnswerActivity::class.java)
//            intent1!!.putExtra("SurveyId", result.toString())
//            intent1!!.putExtra("ProjectID", ProjectID)
//            intent1!!.putExtra("CompanyName", CompanyName)
//            intent1!!.putExtra("Title", Title)
//            intent1!!.putExtra("Address", Address)
//            intent1!!.putExtra("MobileNo", MobileNo)
//            intent1!!.putExtra("Type", Type)
//            intent1!!.putExtra("Status", Status)
//            intent1!!.putExtra("CreatedBy", CreatedBy)
//            intent1!!.putExtra("CreatedDate", CreatedDate)
//            intent1!!.putExtra("ModifiedBy", ModifiedBy)
//            intent1!!.putExtra("ModifiedDate", ModifiedDate)
//            startActivity(intent1)
//            Animatoo.animateCard(context)
//        }
//    }
//
//    var date =
//        OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
//            myCalendar.set(Calendar.YEAR, year)
//            myCalendar.set(Calendar.MONTH, monthOfYear)
//            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
//            updateLabel()
//        }
//
//    private fun updateLabel() {
//        val myFormat = "dd/MM/yyyy"
//        val sdf = SimpleDateFormat(myFormat, Locale.US)
//        txtDate.setText(sdf.format(myCalendar.time))
//    }
//
//
//    inner class getProject(var context: SiteDetailActivity) : AsyncTask<Void, Void, List<Project>>() {
//        override fun doInBackground(vararg params: Void?): List<Project> {
//
//            return context.appDatabase!!.projectDao().getAllProject()
//
//        }
//
//        override fun onPostExecute(project: List<Project>?) {
//
//            if (project !== null) {
//                projectArray?.addAll(project)
//                for (list in project) {
//                    AddressArray?.add(list.CompanyName.toString() + ", " + list.Title.toString())
//                }
//                Log.d("TAG", "onPostExecute: " + projectArray?.size)
//                context.adapterSiteName?.notifyDataSetChanged()
//
//            }
//        }
    }

}


