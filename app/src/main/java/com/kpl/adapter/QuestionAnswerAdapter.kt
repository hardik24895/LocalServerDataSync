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
    var SurveyId: Int,
) : RecyclerView.Adapter<QuestionAnswerAdapter.ItemHolder>() {


    override fun getItemCount(): Int {
        Log.d("TAG", "getItemCount: "+list.size)
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
        holder.txtQuestion?.setText(data.Question)

        var stringArray = data.Questionoption?.split(",")

        val layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        holder.rvAns?.layoutManager = layoutManager
        var adapter = AnswerAdapter(mContext, stringArray, data.Type.toString(),data.QuestionID.toString(),data,SurveyId, holder.rvAns!!)
        holder.rvAns?.adapter = adapter

    }


    class ItemHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        var txtNum: TextView? = null
        var txtQuestion: TextView? = null
        var rvAns: RecyclerView? = null


        fun bindData(context: Context) {
            txtNum = containerView.findViewById(R.id.txtNum)
            txtQuestion = containerView.findViewById(R.id.txtQuestion)
            rvAns = containerView.findViewById(R.id.rvAns)


        }

    }
}