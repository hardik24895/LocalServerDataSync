package com.kpl.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kpl.R
import com.kpl.database.Question
import kotlinx.android.extensions.LayoutContainer


class QuestionAnswerAdapter(
    private val mContext: Context,
    var list: MutableList<Question> = mutableListOf(),
) : RecyclerView.Adapter<QuestionAnswerAdapter.ItemHolder>() {


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            LayoutInflater.from(mContext).inflate(
                R.layout.row_question_answer,
                parent, false
            )
        )
    }


    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        val data = list[position]
        holder.bindData(mContext)
        holder.txtNum?.setText("" + (position + 1) + ".")
        holder.txtSurveyTitle?.setText(data.Question)

        var stringArray = data.Answer?.split(",")

        val layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        holder.rvAns?.layoutManager = layoutManager
        var adapter = AnswerAdapter(mContext, stringArray, data.Type.toString())
        holder.rvAns?.adapter = adapter

    }


    class ItemHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        var txtNum: TextView? = null
        var txtSurveyTitle: TextView? = null
        var rvAns: RecyclerView? = null


        fun bindData(context: Context) {
            txtNum = containerView.findViewById(R.id.txtNum)
            txtSurveyTitle = containerView.findViewById(R.id.txtSurveyTitle)
            rvAns = containerView.findViewById(R.id.rvAns)


        }

    }
}