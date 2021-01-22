package com.kpl.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ScrollView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.kpl.R
import com.kpl.adapter.CategoryAdapter
import com.kpl.database.Category
import com.kpl.database.Project
import com.kpl.database.Question
import com.kpl.database.Survey
import com.kpl.interfaces.goToActivity
import com.kpl.utils.NoScrollLinearLayoutManager
import com.kpl.utils.SessionManager
import kotlinx.android.synthetic.main.activity_question_answer.*
import kotlinx.android.synthetic.main.toolbar_with_back_arrow.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList


class QuestionAnswerActivity : BaseActivity() {

    var SurveyID: String? = ""
    var ProjectID: String? = ""
    var Title: String? = ""
    var Address: String? = ""
    var MobileNo: String? = ""
    var Type: String? = ""
    var Status: String? = ""
    var CreatedBy: String? = ""
    var CreatedDate: String? = ""
    var ModifiedBy: String? = ""
    var ModifiedDate: String? = ""
    var intent1: Intent? = null

    var layoutManager: NoScrollLinearLayoutManager? = null

    var adapter: CategoryAdapter? = null

    var categoryArray: ArrayList<Category>? = null

    var list: List<Question>? = null

    var SurveyId: Int? = -1

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

        projectArray = ArrayList()
        categoryArray = ArrayList()
        AddressArray = ArrayList()
        txtAddress.isSelected = true

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

        if (intent.hasExtra("PROJECT_ID")) {
            SurveyId = intent.getStringExtra("PROJECT_ID")?.toInt()
            Log.d("TAG", "onCreate: " + SurveyId)
            ProjectID = intent.getStringExtra("PROJECT_ID")
            txtDate.text = intent.getStringExtra("PROJECT_DATE")
            autoSiteName.setText(intent.getStringExtra("PROJECT_NAME"))
            Thread(Runnable {
                var project: Project = ProjectID?.toInt()?.let { appDatabase?.projectDao()?.getProjectData(it) }!!
                txtAddress.setText(project.Address)
            }).start()

            autoSiteName.isFocusableInTouchMode = false
            txtDate.isFocusableInTouchMode = false
            txtDate.isEnabled = false

        }

        Thread(Runnable {
            appDatabase?.categoryDao()?.getAllCategory()?.let { categoryArray?.addAll(it) }
        }).start()

        val mSnapHelper: SnapHelper = PagerSnapHelper()
        mSnapHelper.attachToRecyclerView(rvQueAns)
        layoutManager = NoScrollLinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        layoutManager!!.setScrollEnabled(false)
        rvQueAns.layoutManager = layoutManager
        adapter = CategoryAdapter(this, categoryArray!!, SurveyId!!)
        rvQueAns.adapter = adapter

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
                        ProjectID = item.ProjectID.toString()
                        txtAddress.setText(item.Address)
                        break
                    }
                }
            }

        updateLabel()


        txtNext.setOnClickListener {
            if (!ProjectID.equals("")) {
                if (layoutManager?.findLastCompletelyVisibleItemPosition()!! < (categoryArray!!.size - 1)) {
                    val scrollToPosition =
                        layoutManager?.scrollToPosition(layoutManager!!.findLastCompletelyVisibleItemPosition() + 1)
                    if (layoutManager!!.findLastCompletelyVisibleItemPosition() < 0) {
                        autoSiteName.visibility = View.VISIBLE
                        txtAddress.visibility = View.VISIBLE
                        txtDate.visibility = View.VISIBLE
                        txtPrevious.visibility = View.INVISIBLE
                        txtNext.setText("Next")
                    } else if (layoutManager!!.findLastCompletelyVisibleItemPosition() == (categoryArray!!.size - 2)) {
                        autoSiteName.visibility = View.GONE
                        txtAddress.visibility = View.GONE
                        txtDate.visibility = View.GONE
                        txtPrevious.visibility = View.VISIBLE

                        txtNext.setText("Submit")
                    } else {
                        txtNext.setText("Next")
                        txtPrevious.visibility = View.VISIBLE
                        autoSiteName.visibility = View.GONE
                        txtAddress.visibility = View.GONE
                        txtDate.visibility = View.GONE
                    }


                } else {
                    if (SurveyId == -1) {
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
                                session.getDataByKey(
                                    SessionManager.SPFirstName,
                                    ""
                                ) + " " + session.getDataByKey(SessionManager.SPLastName, ""),
                                currentDate,
                                ModifiedBy,
                                ModifiedDate,
                                "0"
                            )
                        ).execute()
                    } else {
                        finish()
                    }
                }
                scrollview1?.post(Runnable { scrollview1?.fullScroll(ScrollView.FOCUS_UP) })
            }else{
                Toast.makeText(this@QuestionAnswerActivity,"Please select project",Toast.LENGTH_SHORT).show()

            }

        }

        txtPrevious.setOnClickListener {
            if (layoutManager?.findLastCompletelyVisibleItemPosition()!! > 0) {
                val scrollToPosition =
                    layoutManager?.scrollToPosition(layoutManager!!.findLastCompletelyVisibleItemPosition() - 1)

                if (layoutManager?.findLastCompletelyVisibleItemPosition() == 1) {
                    autoSiteName.visibility = View.VISIBLE
                    txtAddress.visibility = View.VISIBLE
                    txtDate.visibility = View.VISIBLE
                    txtPrevious.visibility = View.INVISIBLE
                } else {
                    txtPrevious.visibility = View.VISIBLE
                    autoSiteName.visibility = View.GONE
                    txtAddress.visibility = View.GONE
                    txtDate.visibility = View.GONE
                }

            }
            scrollview1?.post(Runnable { scrollview1?.fullScroll(ScrollView.FOCUS_UP) })

        }

        getProject(this@QuestionAnswerActivity).execute()


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


    inner class AddSurvey(var context: QuestionAnswerActivity, var survey: Survey) :
        AsyncTask<Void, Void, Long>() {
        override fun doInBackground(vararg params: Void?): Long {

            var surveyId = context.appDatabase!!.surveyDao().insertSurvey(survey)

            context.appDatabase!!.surveyAnswerDao().updaterecord(surveyId.toInt())

            return surveyId
        }

        override fun onPostExecute(result: Long?) {
            Log.d("TAG", "onPostExecute: " + result.toString())
            finish()
        }
    }


}

