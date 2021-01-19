package com.kpl.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.kpl.R
import com.kpl.adapter.SurveyAdapter
import com.kpl.database.Survey
import com.kpl.extention.invisible
import com.kpl.extention.showAlert
import com.kpl.extention.visible
import com.kpl.interfaces.LoadMoreListener
import com.kpl.model.*
import com.kpl.network.CallbackObserver
import com.kpl.network.Networking
import com.kpl.network.addTo
import com.kpl.utils.SessionManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_servey.rvSurvey
import kotlinx.android.synthetic.main.reclerview_swipelayout.*
import org.json.JSONException
import org.json.JSONObject


class OnlineSurveyFragment : BaseFragment() {

    var adapter: SurveyAdapter? = null
    var surveyArray: MutableList<Survey> = mutableListOf()
    var list: List<Survey>? = null
    var page: Int = 1
    var hasNextPage: Boolean = true

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

        surveyArray = ArrayList()
        page = 1
        hasNextPage = true
        swipeRefreshLayout.isRefreshing = true
        setupRecyclerView()
        recyclerView.isLoading = true
        getOnlinrSurvey(page)

        recyclerView.setLoadMoreListener(object : LoadMoreListener {
            override fun onLoadMore() {
                if (hasNextPage && !recyclerView.isLoading) {
                    progressbar.visible()
                    getOnlinrSurvey(++page)
                }
            }
        })

        swipeRefreshLayout.setOnRefreshListener {
            page = 1
            surveyArray!!.clear()
            hasNextPage = true
            recyclerView.isLoading = true
            adapter?.notifyDataSetChanged()
            getOnlinrSurvey(page)
        }


    }

    private fun setupRecyclerView() {
        var layoutmanger = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutmanger
        adapter = SurveyAdapter(requireContext(), surveyArray!!)
        recyclerView.adapter = adapter
    }


    fun getOnlinrSurvey(page: Int) {
        var result = ""
       // showProgressbar()
        try {

            val jsonObject = JSONObject()
            jsonObject.put("PageSize", "10")
            jsonObject.put("CurrentPage", page)
            jsonObject.put("UserID", session.getDataByKey(SessionManager.SPUserID))

            // jsonBody.put("body", jsonObject)

            result = Networking.setParentJsonData("getSurvey", jsonObject)

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        Networking
            .with(requireContext())
            .getServices()
            .GetSurveyDataFromServer(Networking.wrapParams(result))//wrapParams Wraps parameters in to Request body Json format
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : CallbackObserver<GetServeyDataModel>() {
                override fun onSuccess(response: GetServeyDataModel) {
                    val data = response.data
                    hideProgressbar()

                    if (surveyArray!!.size > 0) {
                        progressbar.invisible()
                    }
                    swipeRefreshLayout?.isRefreshing = false
                    if (data != null) {
                        Log.d("TAG", "onSuccess: " + data.toString())
                        for (iteam in data.indices) {
                            var survey: Survey? = Survey(data.get(iteam).surveyID?.toInt(), data.get(iteam).projectID?.toInt()!!, data.get(iteam).title, data.get(iteam).surveyDate, data.get(iteam).userID, "", "", "", "", "1")
                            surveyArray.add(survey!!)
                        }

                        adapter?.notifyItemRangeInserted(surveyArray!!.size.minus(response.data.size), surveyArray!!.size)

                        hasNextPage = if (response.rowcount != null) { surveyArray!!.size < response.rowcount.toInt()
                        } else {
                            false
                        }

                    } else {
                        hasNextPage = false
                    }
                    refreshData(getString(R.string.no_data_found))
                }

                override fun onFailed(code: Int, message: String) {
                    showAlert(message)
                    hideProgressbar()
                }

            }).addTo(autoDisposable)
    }

    private fun refreshData(msg: String?) {
        recyclerView.setLoadedCompleted()
        swipeRefreshLayout.isRefreshing = false
        adapter?.notifyDataSetChanged()

        if (surveyArray?.size!! > 0) {
            tvInfo.invisible()
            recyclerView.visible()
        } else {
            tvInfo.text = msg
            tvInfo.visible()
            recyclerView.invisible()
        }
    }


}




