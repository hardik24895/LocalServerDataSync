package com.kpl.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kpl.R
import com.kpl.activity.SiteDetailActivity
import com.kpl.adapter.SurveyAdapter
import com.kpl.extention.invisible
import com.kpl.extention.visible
import com.kpl.interfaces.goToActivity
import kotlinx.android.synthetic.main.fragment_servey.*
import kotlinx.android.synthetic.main.toolbar_with_back_arrow.*


class SurveyFragment : BaseFragment() {

    private var adapter: SurveyAdapter? = null
    lateinit var surveyArray: ArrayList<String>

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

        setData()

        var layoutmanger = LinearLayoutManager(requireContext())
        rvSurvey.layoutManager = layoutmanger

        adapter = SurveyAdapter(requireContext(), surveyArray)
        rvSurvey.adapter = adapter

    }

    private fun setData() {
        surveyArray.clear()
        surveyArray.add("Survey 1")
        surveyArray.add("Survey 1")
        surveyArray.add("Survey 1")
        surveyArray.add("Survey 1")
        surveyArray.add("Survey 1")
        surveyArray.add("Survey 1")
        surveyArray.add("Survey 1")

    }
}