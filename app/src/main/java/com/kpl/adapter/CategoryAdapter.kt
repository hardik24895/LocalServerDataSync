package com.kpl.adapter

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kpl.R
import com.kpl.database.AppDatabase
import com.kpl.database.Category
import com.kpl.database.Question
import com.kpl.extention.invisible
import com.kpl.extention.visible
import com.kpl.utils.NoScrollLinearLayoutManager
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.row_category.*


class CategoryAdapter(
    private val mContext: Context,
    var listCategory: MutableList<Category> = mutableListOf(),
    var SurveyId: Int,
    var appDatabase: AppDatabase? = null,
    var layoutManager: NoScrollLinearLayoutManager? = null
) : RecyclerView.Adapter<CategoryAdapter.ItemHolder>() {


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


        //if (data.ParentID == 0) {
        val mainLooper = Looper.getMainLooper()
        Thread(Runnable {
            //  Log.e("TAG", "onBindViewHolder: Cat Id : " + data.CategoryID.toString())
            val queAnsArray: ArrayList<Question> = ArrayList()
            data.CategoryID.toString().let {
                appDatabase?.questionDao()?.getCategoryWiseQuestion(it)
                    ?.let { queAnsArray.addAll(it) }
            }


            var categoryArray: ArrayList<Category>? = ArrayList()
            data.CategoryID?.let {
                appDatabase?.categoryDao()?.getSubCategory(it)?.let { categoryArray?.addAll(it) }
            }

            Handler(mainLooper).post {
                val layoutManager =
                    LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
                holder.rvQue?.layoutManager = layoutManager
                var adapter = QuestionAnswerAdapter(mContext, queAnsArray, SurveyId)
                holder.rvQue?.adapter = adapter


                if (categoryArray!!.size > 0) {
                    holder.rvCat.visible()
                    val layoutManager1 =
                        LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
                    holder.rvCat?.layoutManager = layoutManager1
                    var adapter1 = CategoryAdapter(mContext, categoryArray, SurveyId)
                    holder.rvCat?.adapter = adapter1

                } else {
                    holder.rvCat.invisible()
                }
            }
        }).start()
        //    }


    }


    class ItemHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        var txtNum: TextView? = null
        var txtQuestion: TextView? = null
        var rvQue: RecyclerView? = null


        fun bindData(context: Context) {
            txtNum = containerView.findViewById(R.id.txtNum)
            txtQuestion = containerView.findViewById(R.id.txtQuestion)
            rvQue = containerView.findViewById(R.id.rvQue)


        }

    }
}