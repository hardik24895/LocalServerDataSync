package com.kpl.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kpl.R
import com.kpl.database.Survey
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.row_survey.*

class SurveyAdapter(
    private val mContext: Context,
    var list: MutableList<Survey> = mutableListOf()
) : RecyclerView.Adapter<SurveyAdapter.ItemHolder>() {

    override fun getItemCount(): Int {

        Log.e("TAG", "getItemCount: 123456 " + list.size)
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(LayoutInflater.from(mContext).inflate(R.layout.row_survey, parent, false))
    }


    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val data = list[position]
        holder.bindData(mContext, data)
    }


    class ItemHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bindData(context: Context, data: Survey) {
           // txtSurveyTitle = containerView?.findViewById(R.id.txtSurveyTitle)

            txtSurveyDate.text =data.CreatedDate
            txtSurveyTitle.text =data.Title.toString()
            if (data.Status.equals("0"))
             ivStatus.setImageResource(R.drawable.ic_timer)
            else
                ivStatus.setImageResource(R.drawable.ic_done)


        }

    }
}