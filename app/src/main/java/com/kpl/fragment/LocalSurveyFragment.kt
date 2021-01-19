package com.kpl.fragment

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.kpl.R
import com.kpl.adapter.SurveyAdapter
import com.kpl.database.Survey
import kotlinx.android.synthetic.main.reclerview_swipelayout.*


class LocalSurveyFragment : BaseFragment() {

    var adapter: SurveyAdapter? = null
    var surveyArray: ArrayList<Survey>? = null
    var list: List<Survey>? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.reclerview_swipelayout, container, false)


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout.isRefreshing = true
        surveyArray = ArrayList()
        var layoutmanger = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutmanger

        adapter = SurveyAdapter(requireContext(),surveyArray!!)
        recyclerView.adapter =adapter

        GetDataFromDB().execute()


        swipeRefreshLayout.setOnRefreshListener {

            surveyArray!!.clear()
            GetDataFromDB().execute()

        }

    }

    inner class GetDataFromDB : AsyncTask<Context, Void, List<Survey>>() {
        override fun doInBackground(vararg params: Context?): List<Survey> {

            return appDatabase?.surveyDao()?.getAllSurvey()!!
        }

        override fun onPostExecute(result: List<Survey>?) {
            surveyArray?.clear()
            list = result
            surveyArray?.addAll(list!!)
            adapter?.notifyDataSetChanged()
            swipeRefreshLayout.isRefreshing = false
        }

    }

    override fun onResume() {
        super.onResume()
        GetDataFromDB().execute()
    }

}


