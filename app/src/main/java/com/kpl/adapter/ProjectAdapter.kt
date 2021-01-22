package com.kpl.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.kpl.R
import com.kpl.activity.InformationActivity
import com.kpl.activity.QuestionAnswerActivity
import com.kpl.activity.SurveyPreviewActivity
import com.kpl.database.Survey
import com.kpl.model.ProjectDataItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.row_assign_project.*
import kotlinx.android.synthetic.main.row_survey.*

class ProjectAdapter(
    private val mContext: Context,
    var list: MutableList<ProjectDataItem> = mutableListOf(),
) : RecyclerView.Adapter<ProjectAdapter.ItemHolder>() {

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(LayoutInflater.from(mContext).inflate(R.layout.row_assign_project, parent, false))
    }


    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val data = list[position]
        holder.bindData(mContext, data)


    }


    class ItemHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bindData(context: Context, data: ProjectDataItem) {

            txtProjectTitle.setText(data.title)
            txtProjectCompany.setText(data.companyName)
            txtProjectAddress.setText(data.address)
            txtProjectType.setText(data.type)


        }

    }
}