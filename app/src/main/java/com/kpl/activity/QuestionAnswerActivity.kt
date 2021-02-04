package com.kpl.activity


import android.app.DatePickerDialog
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.kpl.adapter.QuestionAnswerAdapter
import com.kpl.database.*
import com.kpl.utils.NoScrollLinearLayoutManager
import com.kpl.utils.SessionManager
import com.yalantis.ucrop.UCrop
import kotlinx.android.synthetic.main.activity_question_answer.*
import kotlinx.android.synthetic.main.toolbar_with_back_arrow.*
import tech.hibk.searchablespinnerlibrary.SearchableDialog
import tech.hibk.searchablespinnerlibrary.SearchableItem
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class QuestionAnswerActivity : BaseActivity() {


    override fun onHolderDataItem(
        holder1: QuestionAnswerAdapter.ItemHolder?,
        surveyAnswer1: SurveyAnswer
    ) {
        holder = holder1!!
        surveyAnswer = surveyAnswer1!!

    }

    fun getImageOption() {


    }

    companion object {
        var holder: QuestionAnswerAdapter.ItemHolder? = null
        var surveyAnswer: SurveyAnswer? = null
    }


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
    var itens: List<SearchableItem>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_answer)

        getImageOption()
        txtTitle.setText("Question Answer")

        imgBack.setOnClickListener {
            finish()
        }

        spinner.isEnabled = false
        spinner.isClickable = false

        projectArray = ArrayList()
        categoryArray = ArrayList()
        AddressArray = ArrayList()


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
        Handler(Looper.getMainLooper()).postDelayed({
            if (intent.hasExtra("PROJECT_NAME") != null) {

                val spinnerPosition: Int = adapterSiteName!!.getPosition(
                    intent.getStringExtra("PROJECT_NAME").toString()
                )
                Log.d("myPos", "" + spinnerPosition)
                // autoSiteName?.setOnItemSelectedListener(mOnItemSelectedListener)
                // autoSiteName?.setSelectedItem(spinnerPosition)
                if (spinnerPosition != -1)
                    spinner?.setSelection(spinnerPosition)
                // else
                //  spinner.invisible()
                //spinner.nothingSelectedText = "Project Title"

                // spinner?.setSelection(-1)


                //autoSiteName.setOnItemSelectedListener(object  : AdapterView.OnItemSelectedListener)
            } else {
                //spinner?.setSelection(0)
            }
        }, 100)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position != -1) {
                    txtAddress.setText(projectArray?.get(position)?.Address)

                    Title =
                        projectArray!!.get(position).CompanyName.toString() + ", " + projectArray!!.get(
                            position
                        ).Title.toString()

                    if (position == 0) {
                        ProjectID = ""
                    } else {
                        ProjectID = projectArray!!.get(position).ProjectID.toString()
                    }
                }

            }
        }

        linlaySp?.setOnClickListener {

            if (!intent.hasExtra("PROJECT_ID"))
                SearchableDialog(this,
                    itens!!,
                    "Project Title",
                    { item, _ ->
                        //Toast.makeText(this@QuestionAnswerActivity, item.title, Toast.LENGTH_SHORT).show()
                        spinner.setSelection(item.id.toInt())
                    }).show()

        }
        view.setOnClickListener {
            if (!intent.hasExtra("PROJECT_ID"))
                SearchableDialog(this,
                    itens!!,
                    "Project Title",
                    { item, _ ->
                        // Toast.makeText(this@QuestionAnswerActivity, item.title, Toast.LENGTH_SHORT).show()
                        spinner.setSelection(item.id.toInt())
                    }).show()
        }


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
            ProjectID = intent.getStringExtra("PROJECT_ID")
            txtDate.text = intent.getStringExtra("PROJECT_DATE")
            // autoSiteName.setText(intent.getStringExtra("PROJECT_NAME"))


            var mainloop = Looper.getMainLooper()
            Thread(Runnable {

                var project: Project =
                    ProjectID?.toInt()?.let { appDatabase?.projectDao()?.getProjectData(it) }!!
                Handler(mainloop).post {
                    txtAddress.setText(project.Address)
                }
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
                        linlaySp.visibility = View.VISIBLE
                        txtAddress.visibility = View.VISIBLE
                        txtDate.visibility = View.VISIBLE
                        txtPrevious.visibility = View.INVISIBLE
                        txtNext.setText("Next")
                    } else if (layoutManager!!.findLastCompletelyVisibleItemPosition() == (categoryArray!!.size - 2)) {
                        linlaySp.visibility = View.GONE
                        txtAddress.visibility = View.GONE
                        txtDate.visibility = View.GONE
                        txtPrevious.visibility = View.VISIBLE

                        txtNext.setText("Submit")
                    } else {
                        txtNext.setText("Next")
                        txtPrevious.visibility = View.VISIBLE
                        linlaySp.visibility = View.GONE
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
                    spinner.visibility = View.VISIBLE
                    linlaySp.visibility = View.VISIBLE
                    txtDate.visibility = View.VISIBLE
                    txtPrevious.visibility = View.INVISIBLE
                } else {
                    txtPrevious.visibility = View.VISIBLE
                    spinner.visibility = View.GONE
                    linlaySp.visibility = View.GONE
                    txtDate.visibility = View.GONE
                }

            }
            scrollview1?.post(Runnable { scrollview1?.fullScroll(ScrollView.FOCUS_UP) })

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            RESULT_OK -> {

                var resultUri = UCrop.getOutput(data!!)
                var obj = QuestionAnswerAdapter.MyImageSelected()
                obj.onImageItemCilck(holder, resultUri,surveyAnswer!!,this)


                getProject(this@QuestionAnswerActivity,surveyAnswer!!,resultUri.toString()).execute()


            }

            RESULT_CANCELED -> {
            }
        }
    }

    inner class getProject(var context: QuestionAnswerActivity, var mySurveyAnswer: SurveyAnswer, var result: String) : AsyncTask<Void, Void, Boolean>() {
        override fun doInBackground(vararg params: Void?): Boolean {


            var existAns: SurveyAnswer? = null
            Companion.surveyAnswer!!.Image = result.toString()
            existAns = appDatabase?.surveyAnswerDao()?.checkRecordExist(Companion.surveyAnswer!!.SurveyID, Companion.surveyAnswer!!.QuestionID.toString())

            if (existAns == null) {
                appDatabase!!.surveyAnswerDao().insert(Companion.surveyAnswer!!)

            } else {
                appDatabase!!.surveyAnswerDao().updateImage(Companion.surveyAnswer!!.SurveyID, Companion.surveyAnswer!!.QuestionID.toString(), result.toString())
            }


            return true

        }

        override fun onPostExecute(result: Boolean?) {


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

            projectArray?.add(
                Project(
                    -1, "Project Title", "",
                    "", "", "", "", "", "", "", ""
                )
            )
            projectArray?.addAll(appDatabase!!.projectDao().getAllProject())


            for (list in projectArray!!.indices) {
                AddressArray?.add(
                    projectArray!!.get(list).CompanyName.toString() + ", " + projectArray!!.get(list).Title.toString()
                )
            }

            var myList: MutableList<SearchableItem> = mutableListOf()
            for (items in AddressArray!!.indices) {
                myList.add(SearchableItem(items.toLong(), AddressArray!!.get(items).toString()))
            }
            itens = myList



            adapterSiteName = ArrayAdapter(this, R.layout.custom_spinner, AddressArray!!)
            spinner.setAdapter(adapterSiteName)

        }).start()

    }


}

