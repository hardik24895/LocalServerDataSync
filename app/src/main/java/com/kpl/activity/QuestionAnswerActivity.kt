package com.kpl.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
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
import com.kpl.utils.NoScrollLinearLayoutManager
import com.kpl.utils.SessionManager
import gr.escsoft.michaelprimez.searchablespinner.interfaces.OnItemSelectedListener
import kotlinx.android.synthetic.main.activity_question_answer.*
import kotlinx.android.synthetic.main.toolbar_with_back_arrow.*
import java.text.SimpleDateFormat
import java.util.*
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
    var selectedPos: Int = -1
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


        getProject()


//        autoSiteName?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//            }
//
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                Log.d("TAG", "onItemSelected: " + position)
//
//                txtAddress.setText(projectArray!!.get(position).Address)
//                ProjectID = projectArray!!.get(position).ProjectID.toString()
//                Title = projectArray!!.get(position).Title.toString()
//
//
//            }
//
//        }





        if (intent.hasExtra("PROJECT_ID")) {
            SurveyId = intent.getStringExtra("PROJECT_ID")?.toInt()
            Log.d("TAG", "onCreate: " + SurveyId)
            ProjectID = intent.getStringExtra("PROJECT_ID")
            txtDate.text = intent.getStringExtra("PROJECT_DATE")
            // autoSiteName.setText(intent.getStringExtra("PROJECT_NAME"))


            Thread(Runnable {
                var project: Project =
                    ProjectID?.toInt()?.let { appDatabase?.projectDao()?.getProjectData(it) }!!
                txtAddress.setText(project.Address)
            }).start()

            //    autoSiteName.isFocusableInTouchMode = false
            txtDate.isFocusableInTouchMode = false
            txtDate.isEnabled = false


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

                        val mainLooper = Looper.getMainLooper()
                        Thread(Runnable {
                            val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                            val currentDate = sdf.format(Date())
                            var surveyId = appDatabase!!.surveyDao().insertSurvey(
                                Survey(
                                    null,
                                    ProjectID?.toInt()!!,
                                    Title,
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
                            )
                            appDatabase!!.surveyAnswerDao().updaterecord(surveyId.toInt())

                            Handler(mainLooper).post {
                                finish()
                            }

                        }).start()


                    } else {
                        finish()
                    }
                }
                scrollview1?.post(Runnable { scrollview1?.fullScroll(ScrollView.FOCUS_UP) })
            } else {
                Toast.makeText(
                    this@QuestionAnswerActivity,
                    "Please select project",
                    Toast.LENGTH_SHORT
                ).show()

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

    }
    val mOnItemSelectedListener: OnItemSelectedListener = object : OnItemSelectedListener {
        override fun onItemSelected(view: View?, position: Int, id: Long) {
          Toast.makeText(this@QuestionAnswerActivity, "Item on position " + position + " : " + " Selected", Toast.LENGTH_SHORT).show()
        }

        override fun onNothingSelected() {
         //   Toast.makeText(this@QuestionAnswerActivity, "Nothing Selected", Toast.LENGTH_SHORT).show()
        }
    }

    var date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
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


    private fun getProject() {
        val mainLooper = Looper.getMainLooper()
        Thread(Runnable {

            projectArray?.addAll(appDatabase!!.projectDao().getAllProject())


            for (list in projectArray!!.indices) {
                AddressArray?.add(
                    projectArray!!.get(list).CompanyName.toString() + ", " + projectArray!!.get(list).Title.toString()
                )
            }
            adapterSiteName = ArrayAdapter(this, R.layout.custom_spinner, AddressArray!!)
            autoSiteName.setAdapter(adapterSiteName)
            autoSiteName.setOnItemSelectedListener(mOnItemSelectedListener)

            Handler(mainLooper).post {

                if (intent.hasExtra("PROJECT_NAME") != null) {
                    Log.e("TAG", "getProject: 123   "+ intent.getStringExtra("PROJECT_NAME").toString())
                    val spinnerPosition: Int = adapterSiteName!!.getPosition(intent.getStringExtra("PROJECT_NAME").toString())
                    autoSiteName.setSelectedItem(spinnerPosition)


                }

                /* if (intent.hasExtra("PROJECT_ID")) {
                    for (item in projectArray!!.indices) {
                        if (projectArray!!.get(item).ProjectID.toString().equals(
                                intent.getStringExtra(
                                    "PROJECT_ID"
                                )
                            )
                        ) {
                            selectedPos = item
                            break
                        }
                    }
                }
                autoSiteName.setSelectedItem = 2
                autoSiteName.setSelectedItem(2)*/


            }

        }).start()

    }
//
//    inner class getProject(var context: QuestionAnswerActivity) :
//        AsyncTask<Void, Void, List<Project>>() {
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
//    }


//    inner class AddSurvey(var context: QuestionAnswerActivity, var survey: Survey) :
//        AsyncTask<Void, Void, Long>() {
//        override fun doInBackground(vararg params: Void?): Long {
//
//            var surveyId = context.appDatabase!!.surveyDao().insertSurvey(survey)
//
//            context.appDatabase!!.surveyAnswerDao().updaterecord(surveyId.toInt())
//
//            return surveyId
//        }
//
//        override fun onPostExecute(result: Long?) {
//            Log.d("TAG", "onPostExecute: " + result.toString())
//
//        }
//    }


}

