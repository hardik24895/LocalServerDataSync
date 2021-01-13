package com.kpl.activity

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import com.kpl.R
import com.kpl.database.Project
import com.kpl.interfaces.goToActivity
import kotlinx.android.synthetic.main.activity_site_detail.*
import kotlinx.android.synthetic.main.toolbar_with_back_arrow.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList


class SiteDetailActivity : BaseActivity() {

    val myCalendar: Calendar = Calendar.getInstance()
    var projectArray: ArrayList<Project>? = null
    var AddressArray: ArrayList<String>? = null
    var adapterSiteName: ArrayAdapter<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_site_detail)
        txtTitle.setText("Site")
        imgBack.setOnClickListener { finish() }
        txtSubmit.setOnClickListener {
            goToActivity<QuestionAnswerActivity>()
        }
        projectArray = ArrayList()
        AddressArray = ArrayList()
        txtAddress.isSelected =true

        adapterSiteName = ArrayAdapter(this, R.layout.custom_spinner, AddressArray!!)
        autoSiteName.setAdapter(adapterSiteName)
        autoSiteName.threshold = 0
        autoSiteName.onItemClickListener =
            OnItemClickListener { parent, arg1, position, arg3 ->
                val item: Any = parent.getItemAtPosition(position)


                val list: List<String> =
                    Pattern.compile(", ").split(parent.getItemAtPosition(position).toString())
                        .toList()

                for (item in projectArray!!) {
                    if (list.get(0).equals(item.CompanyName) && list.get(1).equals(item.Title)) {
                        txtAddress.setText(item.Address)
                        break
                    }
                }


            }

        updateLabel()

        getProject(this).execute()


        txtDate.setOnClickListener {


            val datePickerDialog = DatePickerDialog(
                this@SiteDetailActivity,
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
        OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
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


    inner class getProject(var context: SiteDetailActivity) :
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


}