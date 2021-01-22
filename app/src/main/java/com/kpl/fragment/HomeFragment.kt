package com.kpl.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kpl.R
import com.kpl.adapter.ProjectAdapter
import com.kpl.adapter.SurveyAdapter
import com.kpl.database.Survey
import com.kpl.extention.invisible
import com.kpl.extention.showAlert
import com.kpl.extention.visible
import com.kpl.interfaces.LoadMoreListener
import com.kpl.model.GetServeyDataModel
import com.kpl.model.ProjectAssignDataModel
import com.kpl.model.ProjectDataItem
import com.kpl.network.CallbackObserver
import com.kpl.network.Networking
import com.kpl.network.addTo
import com.kpl.utils.SessionManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.reclerview_swipelayout.*
import kotlinx.android.synthetic.main.toolbar_with_back_arrow.*
import org.json.JSONException
import org.json.JSONObject


class HomeFragment : BaseFragment() {


    var adapter: ProjectAdapter? = null
    var projectDataArray: MutableList<ProjectDataItem> = mutableListOf()
    var list: List<Survey>? = null
    var page: Int = 1
    var hasNextPage: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imgBack.invisible()
        txtTitle.text = "HOME"

        projectDataArray = ArrayList()
        page = 1
        hasNextPage = true
        swipeRefreshLayout.isRefreshing = true
        setupRecyclerView()
        recyclerView.isLoading = true
        getProjectList(page)

        recyclerView.setLoadMoreListener(object : LoadMoreListener {
            override fun onLoadMore() {
                if (hasNextPage && !recyclerView.isLoading) {
                    progressbar.visible()
                    getProjectList(++page)
                }
            }
        })

        swipeRefreshLayout.setOnRefreshListener {
            page = 1
            projectDataArray!!.clear()
            hasNextPage = true
            recyclerView.isLoading = true
            adapter?.notifyDataSetChanged()
            getProjectList(page)
        }

    }

    fun getProjectList(page: Int) {
        var result = ""
        // showProgressbar()
        try {

            val jsonObject = JSONObject()
            jsonObject.put("PageSize", "10")
            jsonObject.put("CurrentPage", page)
            jsonObject.put("UserID", session.getDataByKey(SessionManager.SPUserID))
            jsonObject.put("UserID", -1)

            // jsonBody.put("body", jsonObject)

            result = Networking.setParentJsonData("getHomeProjectAssign", jsonObject)

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        Networking
            .with(requireContext())
            .getServices()
            .GetAssignProjectList(Networking.wrapParams(result))//wrapParams Wraps parameters in to Request body Json format
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : CallbackObserver<ProjectAssignDataModel>() {
                override fun onSuccess(response: ProjectAssignDataModel) {
                    val data = response.data
                    hideProgressbar()

                    if (projectDataArray.size > 0) {
                        progressbar.invisible()
                    }
                    swipeRefreshLayout?.isRefreshing = false
                    if (data != null) {
                        Log.d("TAG", "onSuccess: " + data.toString())

                        projectDataArray.addAll(data)


                        adapter?.notifyItemRangeInserted(
                            projectDataArray.size.minus(response.data.size),
                            projectDataArray.size
                        )

                        hasNextPage = if (response.rowcount != null) {
                            projectDataArray.size < response.rowcount.toInt()
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

    private fun setupRecyclerView() {
        var layoutmanger = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutmanger
        adapter = ProjectAdapter(requireContext(), projectDataArray)
        recyclerView.adapter = adapter
    }

    private fun refreshData(msg: String?) {
        recyclerView.setLoadedCompleted()
        swipeRefreshLayout.isRefreshing = false
        adapter?.notifyDataSetChanged()

        if (projectDataArray?.size!! > 0) {
            tvInfo.invisible()
            recyclerView.visible()
        } else {
            tvInfo.text = msg
            tvInfo.visible()
            recyclerView.invisible()
        }
    }
}