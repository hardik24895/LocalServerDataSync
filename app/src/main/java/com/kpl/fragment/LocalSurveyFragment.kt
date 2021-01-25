package com.kpl.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
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

        adapter = SurveyAdapter(requireContext(),surveyArray!!, true)
        recyclerView.adapter =adapter

        swipeRefreshLayout.setOnRefreshListener {

            surveyArray!!.clear()
            getLocalServey()
        }

    }

    private fun getLocalServey() {
        val mainLooper = Looper.getMainLooper()
        Thread(Runnable {
            surveyArray?.clear()
            list = appDatabase?.surveyDao()?.getAllPendingSurvey()!!
            surveyArray?.addAll(list!!)
            Handler(mainLooper).post {
                adapter?.notifyDataSetChanged()
                swipeRefreshLayout.isRefreshing = false
            }
        }).start()
    }




    override fun onResume() {
        super.onResume()
        getLocalServey()
    }

}


