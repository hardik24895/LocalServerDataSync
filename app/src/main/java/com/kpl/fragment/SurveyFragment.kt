package com.kpl.fragment

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.kpl.R
import com.kpl.activity.SiteDetailActivity
import com.kpl.adapter.SurveyAdapter
import com.kpl.database.AppDatabase
import com.kpl.database.Question
import com.kpl.database.Survey
import com.kpl.extention.invisible
import com.kpl.extention.visible
import com.kpl.interfaces.goToActivity
import kotlinx.android.synthetic.main.fragment_servey.*
import kotlinx.android.synthetic.main.toolbar_with_back_arrow.*


class SurveyFragment : BaseFragment() {

    private var adapter: SurveyAdapter? = null
    lateinit var surveyArray: ArrayList<Survey>
    var appDatabase: AppDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_servey, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imgBack.invisible()
        txtTitle.text = "Survey"
        imgAdd.visible()
        surveyArray = ArrayList()
        imgAdd.setOnClickListener {
            goToActivity<SiteDetailActivity>()
        }

        appDatabase = AppDatabase.getDatabase(requireContext())!!
        var layoutmanger = LinearLayoutManager(requireContext())
        rvSurvey.layoutManager = layoutmanger

        adapter = SurveyAdapter(requireContext(), surveyArray)
        rvSurvey.adapter = adapter
      //  GetDataFromDB(requireContext()).execute();
    }


//    class GetDataFromDB(var context: Context) :
//        AsyncTask<Void, Void, List<Survey>>() {
//        override fun doInBackground(vararg params: Void?): List<Survey>? {
//
//
//            return context.appDatabase.questionDao()?.getAllQuestion()
//
//        }
//
//
//        override fun onPostExecute(bool: List<Survey>) {
//
//            context.list = bool
//            context.queAnsArray?.addAll(context.list!!)
//
//            context.adapter?.notifyDataSetChanged()
//
//
//        }
//
//    }


}