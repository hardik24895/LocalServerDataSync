package com.kpl.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.kpl.R
import com.kpl.activity.QuestionAnswerActivity
import com.kpl.adapter.SurveyAdapter
import com.kpl.database.Survey
import com.kpl.extention.invisible
import com.kpl.extention.visible
import com.kpl.interfaces.goToActivity
import kotlinx.android.synthetic.main.fragment_servey.*
import kotlinx.android.synthetic.main.toolbar_with_back_arrow.*


class SurveyFragment : BaseFragment() {

    var adapter: SurveyAdapter? = null
    var surveyArray: ArrayList<Survey>? = null
    var list: List<Survey>? = null


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
            goToActivity<QuestionAnswerActivity>()
        }

        setupViewPager(viewPager)
    }

//    inner class GetDataFromDB : AsyncTask<Context, Void, List<Survey>>() {
//        override fun doInBackground(vararg params: Context?): List<Survey> {
//
//            return appDatabase?.surveyDao()?.getAllSurvey()!!
//        }
//
//        override fun onPostExecute(result: List<Survey>?) {
//            list = result
//            surveyArray?.addAll(list!!)
//            adapter?.notifyDataSetChanged()
//        }
//
//    }

    private fun setupViewPager(viewpager: ViewPager) {
        var adapter = ViewPagerAdapter(getChildFragmentManager())

        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        tabLayout.addTab(tabLayout.newTab().setText("Local"))
        tabLayout.addTab(tabLayout.newTab().setText("Server"))

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        // LoginFragment is the name of Fragment and the Login
        // is a title of tab

        // setting adapter to view pager.
        viewpager.setAdapter(adapter)
    }

    class ViewPagerAdapter : FragmentPagerAdapter {

        // objects of arraylist. One is of Fragment type and
        // another one is of String type.*/
        private final var fragmentList1: ArrayList<Fragment> = ArrayList()
        private final var fragmentTitleList1: ArrayList<String> = ArrayList()

        // this is a secondary constructor of ViewPagerAdapter class.
        public constructor(supportFragmentManager: FragmentManager) : super(supportFragmentManager)

        // returns which item is selected from arraylist of fragments.


        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> {
                    LocalSurveyFragment()
                }
                1 -> {
                    OnlineSurveyFragment()
                }

                else -> getItem(position)
            }
            // returns which item is selected from arraylist of titles.


            // this function adds the fragment and title in 2 separate  arraylist.
            fun addFragment(fragment: Fragment, title: String) {
                fragmentList1.add(fragment)
                fragmentTitleList1.add(title)
            }
        }

        override fun getCount(): Int {
            return 2
        }

    }
}


