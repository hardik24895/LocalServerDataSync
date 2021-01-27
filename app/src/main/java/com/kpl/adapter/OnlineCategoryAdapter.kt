package com.kpl.adapter

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kpl.R
import com.kpl.activity.LoginActivity
import com.kpl.database.AppDatabase
import com.kpl.database.Category
import com.kpl.database.Question
import com.kpl.interfaces.goToActivity
import com.kpl.model.SurveyAnswerData
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.row_category.*


class OnlineCategoryAdapter(
    private val mContext: Context,
    var listCategory: MutableList<Category> = mutableListOf(),
    var surveyAnswerArray: ArrayList<SurveyAnswerData>,
    var appDatabase: AppDatabase? = null
) : RecyclerView.Adapter<OnlineCategoryAdapter.ItemHolder>() {


    override fun getItemCount(): Int {
        Log.d("TAG", "getItemCount: " + listCategory.size)
        return listCategory.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        appDatabase = AppDatabase.getDatabase(mContext)!!
        return ItemHolder(
            LayoutInflater.from(mContext).inflate(
                R.layout.row_category,
                parent, false
            )
        )
    }


    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        val data = listCategory[position]
        holder.bindData(mContext)
        holder.txtNum?.setText("" + (position + 1) + ".")
        holder.txtQuestion?.setText(data.Category)
        val queAnsArray: ArrayList<Question> = ArrayList()

        val mainLooper = Looper.getMainLooper()
        Thread(Runnable { Log.e("TAG", "onBindViewHolder: Cat Id : " + data.CategoryID.toString())

            data.CategoryID.toString().let { appDatabase?.questionDao()?.getCategoryWiseQuestion(it)?.let { queAnsArray.addAll(it) } }

            Handler(mainLooper).post {
                val layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
                holder.rvAns?.layoutManager = layoutManager
                var adapter = OnlineQuestionAnswerAdapter(mContext, queAnsArray,surveyAnswerArray)
                holder.rvAns?.adapter = adapter
            }
        }).start()


    }


    class ItemHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        var txtNum: TextView? = null
        var txtQuestion: TextView? = null
        var rvAns: RecyclerView? = null
        var scrollview: ScrollView? = null


        fun bindData(context: Context) {
            txtNum = containerView.findViewById(R.id.txtNum)
            txtQuestion = containerView.findViewById(R.id.txtQuestion)
            rvAns = containerView.findViewById(R.id.rvAns)
            scrollview = containerView.findViewById(R.id.scrollview)


        }

    }
}