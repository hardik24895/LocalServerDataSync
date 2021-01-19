package com.kpl.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.kpl.R
import com.kpl.adapter.QuestionAnswerAdapter
import com.kpl.database.Project
import com.kpl.database.Question
import com.kpl.database.Survey
import com.kpl.utils.SessionManager
import kotlinx.android.synthetic.main.activity_question_answer.*
import kotlinx.android.synthetic.main.activity_question_answer.txtDate
import kotlinx.android.synthetic.main.toolbar_with_back_arrow.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList


class QuestionAnswerActivity : BaseActivity() {

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

    var adapter: QuestionAnswerAdapter? = null
    var queAnsArray: ArrayList<Question>? = null

    var list: List<Question>? = null


    val myCalendar: Calendar = Calendar.getInstance()
    var projectArray: ArrayList<Project>? = null
    var AddressArray: ArrayList<String>? = null
    var adapterSiteName: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_answer)

        txtTitle.setText("Question Answer")

        imgBack.setOnClickListener {
            finish()
        }
        queAnsArray = ArrayList()

        txtAns.setOnClickListener {

            val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
            val currentDate = sdf.format(Date())
            AddSurvey(
                this,
                Survey(
                    null,
                    ProjectID?.toInt()!!,
                    autoSiteName.text.toString(),
                    txtDate.text.toString(),
                    session.getDataByKey(SessionManager.SPUserID, ""),
                    session.getDataByKey(SessionManager.SPFirstName, "")+" "+session.getDataByKey(SessionManager.SPLastName, ""),
                    currentDate,
                    ModifiedBy,
                    ModifiedDate,
                    "0"
                )
            ).execute()

//            GlobalScope.launch(Dispatchers.Main) {
//                val result = withContext(Dispatchers.Default) {
//                    appDatabase!!.surveyAnswerDao().checkRecordExist("1", QueId)
//                }
//
//                if (result != null)
//                    if (type.equals(Constant.typeSigleSelection)) {
//
//                        if (result.Answer.toString().equals(data.toString())) {
//                            lastRadioPosition == position
//                            holder.rbOption?.setChecked(true)
//                        } else
//                            holder.rbOption?.setChecked(false)
//                    } else if (type.equals(Constant.typeMutliSelection)) {
//
//                        val strs = result.Answer.toString().split(",").toTypedArray()
//                        for (iteam in strs) {
//
//                            if (iteam.equals(data.toString())) {
//                                holder.cbOption?.setChecked(true)
//                            }
//                        }
//
//                    } else if (type.equals(Constant.typeEdit)) {oiuyt-*63.0
//                        holder.edtOption?.setText(result.Answer?.toString())
//                    }
//
//            }
        }

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvQueAns.setLayoutManager(layoutManager)
        rvQueAns.layoutManager = layoutManager
        adapter = QuestionAnswerAdapter(this, queAnsArray!!)
        rvQueAns.adapter = adapter

        GetDataFromDB(this).execute()



        projectArray = ArrayList()
        AddressArray = ArrayList()
        txtAddress.isSelected = true

        adapterSiteName = ArrayAdapter(this, R.layout.custom_spinner, AddressArray!!)
        autoSiteName.setAdapter(adapterSiteName)
        autoSiteName.threshold = 0
        autoSiteName.onItemClickListener =
            AdapterView.OnItemClickListener { parent, arg1, position, arg3 ->


                val list: List<String> =
                    Pattern.compile(", ").split(parent.getItemAtPosition(position).toString())
                        .toList()

                for (item in projectArray!!) {
                    if (list.get(0).equals(item.CompanyName) && list.get(1).equals(item.Title)) {
                        ProjectID =item.ProjectID.toString()
                        txtAddress.setText(item.Address)
                        break
                    }
                }
            }

        updateLabel()

        getProject(this@QuestionAnswerActivity).execute()


        txtDate.setOnClickListener {


            val datePickerDialog = DatePickerDialog(
                this,
                date,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            )
            //  datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            datePickerDialog.show()

        }

    }


    var date =
        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel()
        }

    private fun updateLabel() {
        val myFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        txtDate.setText(sdf.format(myCalendar.time))
    }


    inner class getProject(var context: QuestionAnswerActivity) :
        AsyncTask<Void, Void, List<Project>>() {
        override fun doInBackground(vararg params: Void?): List<Project> {

            return context.appDatabase!!.projectDao().getAllProject()

        }

        override fun onPostExecute(project: List<Project>?) {

            if (project !== null) {
                projectArray?.addAll(project)
                for (list in project) {
                    AddressArray?.add(list.CompanyName.toString() + ", " + list.Title.toString())
                }
                Log.d("TAG", "onPostExecute: " + projectArray?.size)
                context.adapterSiteName?.notifyDataSetChanged()

            }
        }
    }

    class GetDataFromDB(var context: QuestionAnswerActivity) :
        AsyncTask<Void, Void, List<Question>>() {
        override fun doInBackground(vararg params: Void?): List<Question>? {

            return context.appDatabase?.questionDao()?.getAllQuestion()

        }

        override fun onPostExecute(bool: List<Question>) {

            context.list = bool
            context.queAnsArray?.addAll(context.list!!)

            context.adapter?.notifyDataSetChanged()


        }

    }

    inner class AddSurvey(var context: QuestionAnswerActivity, var survey: Survey) :
        AsyncTask<Void, Void, Long>() {
        override fun doInBackground(vararg params: Void?): Long {

          var surveyId =  context.appDatabase!!.surveyDao().insertSurvey(survey)

            context.appDatabase!!.surveyAnswerDao().updaterecord(surveyId.toInt())

            return surveyId
        }

        override fun onPostExecute(result: Long?) {
            Log.d("TAG", "onPostExecute: "+result.toString())
            finish()
        }
    }


}

