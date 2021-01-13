package com.kpl.fragment

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.kpl.R
import com.kpl.activity.SiteDetailActivity
import com.kpl.adapter.SurveyAdapter
import com.kpl.database.Survey
import com.kpl.extention.invisible
import com.kpl.extention.visible
import com.kpl.interfaces.goToActivity
import kotlinx.android.synthetic.main.fragment_local_servey.*
import kotlinx.android.synthetic.main.fragment_servey.*
import kotlinx.android.synthetic.main.fragment_servey.rvSurvey
import kotlinx.android.synthetic.main.toolbar_with_back_arrow.*


class LocalSurveyFragment : BaseFragment() {

    var adapter: SurveyAdapter? = null
    var surveyArray: ArrayList<Survey>? = null
    var list: List<Survey>? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_local_servey, container, false)


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var layoutmanger = LinearLayoutManager(requireContext())
        rvSurvey.layoutManager = layoutmanger

        adapter = SurveyAdapter(requireContext())
        rvSurvey.adapter =adapter



    }

    inner class GetDataFromDB : AsyncTask<Context, Void, List<Survey>>() {
        override fun doInBackground(vararg params: Context?): List<Survey> {

            return appDatabase?.surveyDao()?.getAllSurvey()!!
        }

        override fun onPostExecute(result: List<Survey>?) {
            list = result
            surveyArray?.addAll(list!!)
            adapter?.notifyDataSetChanged()
        }

    }
    private fun setData() {


    }

}


