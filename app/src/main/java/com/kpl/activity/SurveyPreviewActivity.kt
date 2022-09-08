package com.kpl.activity

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SimpleOnItemTouchListener
import androidx.recyclerview.widget.SnapHelper
import com.kpl.R
import com.kpl.adapter.OnlineCategoryAdapter
import com.kpl.database.Category
import com.kpl.database.Question
import com.kpl.extention.showAlert
import com.kpl.model.OnlineServeyDataModel
import com.kpl.model.SurveyAnswerData
import com.kpl.network.CallbackObserver
import com.kpl.network.Networking
import com.kpl.network.addTo
import com.kpl.utils.NoScrollLinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_question_answer.*
import kotlinx.android.synthetic.main.activity_question_answer.scrollview1
import kotlinx.android.synthetic.main.activity_question_answer.txtNext
import kotlinx.android.synthetic.main.activity_question_answer.txtPrevious
import kotlinx.android.synthetic.main.activity_survey_preview.*
import kotlinx.android.synthetic.main.toolbar_with_back_arrow.*
import org.json.JSONException
import org.json.JSONObject


class SurveyPreviewActivity : BaseActivity() {

    var ProjectID: String? = null
    var Title: String? = null
    var Address: String? = null
    var MobileNo: String? = null
    var Type: String? = null
    var Status: String? = null
    var CreatedBy: String? = null
    var CreatedDate: String? = null
    var ModifiedBy: String? = null
    var ModifiedDate: String? = null
    var layoutManager: NoScrollLinearLayoutManager? = null
    var adapter: OnlineCategoryAdapter? = null
    var categoryArray: ArrayList<Category>? = null
    var surveyAnswerArray: ArrayList<SurveyAnswerData>? = null
    var list: List<Question>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey_preview)

        txtTitle.setText("Survey Preview")

        imgBack.setOnClickListener {
            finish()
        }

        categoryArray = ArrayList()
        surveyAnswerArray = ArrayList()

        Thread(Runnable {
            appDatabase?.categoryDao()?.getAllCategory()?.let { categoryArray?.addAll(it) }

        }).start()

        intent.getStringExtra("PROJECT_ID")?.toInt()?.let { getOnlineServeyAnswer(it) }


        txtNext.setOnClickListener {
            if (layoutManager?.findLastCompletelyVisibleItemPosition()!! < (categoryArray!!.size - 1)) {
                val scrollToPosition =
                    layoutManager?.scrollToPosition(layoutManager!!.findLastCompletelyVisibleItemPosition() + 1)

                if (layoutManager!!.findLastCompletelyVisibleItemPosition() < 0) {
                    txtPrevious.visibility = View.INVISIBLE
                    txtNext.setText("Next")
                } else if (layoutManager!!.findLastCompletelyVisibleItemPosition() == (categoryArray!!.size - 2)) {
                    txtPrevious.visibility = View.VISIBLE
                    txtNext.setText("close")
                } else {
                    txtNext.setText("Next")
                    txtPrevious.visibility = View.VISIBLE
                }


            } else {
                finish()
            }
            scrollview1?.post(Runnable { scrollview1?.fullScroll(ScrollView.FOCUS_UP) })
        }

        txtPrevious.setOnClickListener {
            if (layoutManager?.findLastCompletelyVisibleItemPosition()!! > 0) {
                val scrollToPosition =
                    layoutManager?.scrollToPosition(layoutManager!!.findLastCompletelyVisibleItemPosition() - 1)

                if (layoutManager?.findLastCompletelyVisibleItemPosition() == 1) {
                    txtPrevious.visibility = View.INVISIBLE
                } else {
                    txtPrevious.visibility = View.VISIBLE
                }

            }
            scrollview1?.post(Runnable { scrollview1?.fullScroll(ScrollView.FOCUS_UP) })

        }
    }

    fun getOnlineServeyAnswer(SurveyId: Int) {
        var result = ""
        showProgressbar()
        try {

            val jsonObject = JSONObject()
            jsonObject.put("PageSize", "-1")
            jsonObject.put("CurrentPage", "1")
            jsonObject.put("SurveyID", SurveyId)

            // jsonBody.put("body", jsonObject)

            result = Networking.setParentJsonData("getSurveyAnswer", jsonObject)

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        Networking
            .with(this)
            .getServices()
            .GetOnlineServeyList(Networking.wrapParams(result))//wrapParams Wraps parameters in to Request body Json format
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : CallbackObserver<OnlineServeyDataModel>() {
                override fun onSuccess(response: OnlineServeyDataModel) {
                    val data = response.data
                    hideProgressbar()
                    if (data != null) {
                        Log.d("TAG", "onSuccess: " + data.toString())

                        surveyAnswerArray?.addAll(data)
                        val mSnapHelper: SnapHelper = PagerSnapHelper()
                        mSnapHelper.attachToRecyclerView(rvQueAnsonline)
                        layoutManager = NoScrollLinearLayoutManager(
                            this@SurveyPreviewActivity,
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        layoutManager!!.setScrollEnabled(false)
                        rvQueAnsonline.layoutManager = layoutManager
                        rvQueAnsonline.isEnabled =false
                        rvQueAnsonline.addOnItemTouchListener(object : SimpleOnItemTouchListener() {
                            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                                return false
                            }
                        })
                        adapter = OnlineCategoryAdapter(this@SurveyPreviewActivity, categoryArray!!, surveyAnswerArray!!
                        )
                        rvQueAnsonline.adapter = adapter


                    } else {
                        showAlert(getString(R.string.something_went_wrong))
                    }
                }

                override fun onFailed(code: Int, message: String) {
                    showAlert(message)
                    hideProgressbar()
                }

            }).addTo(autoDisposable)
    }


}

