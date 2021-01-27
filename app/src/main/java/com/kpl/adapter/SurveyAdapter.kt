package com.kpl.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.kpl.R
import com.kpl.activity.SurveyPreviewActivity
import com.kpl.database.Survey
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.row_survey.*

class SurveyAdapter(
    private val mContext: Context,
    var list: MutableList<Survey> = mutableListOf(),
    var isLocal: Boolean
) : RecyclerView.Adapter<SurveyAdapter.ItemHolder>() {

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(LayoutInflater.from(mContext).inflate(R.layout.row_survey, parent, false))
    }


    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val data = list[position]
        holder.bindData(mContext, data)
        holder.itemView.setOnClickListener {

            var intent: Intent
            if (isLocal) {
                intent = Intent(mContext, QuestionAnswerActivity::class.java)
                intent.putExtra("PROJECT_ID", data.SurveyID.toString())
                intent.putExtra("PROJECT_DATE", data.SurveyDate.toString())
                intent.putExtra("PROJECT_NAME", data.Title.toString())
            } else {
                intent = Intent(mContext, SurveyPreviewActivity::class.java)
                intent.putExtra("PROJECT_ID", data.SurveyID.toString())
            }
            mContext.startActivity(intent)
            Animatoo.animateCard(mContext)

        }

    }


    class ItemHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bindData(context: Context, data: Survey) {

            txtSurveyDate.text = data.CreatedDate
            txtSurveyTitle.text = data.Title.toString()
            txtSurveyDate.text = data.SurveyDate.toString()
            if (data.Status.equals("0"))
                ivStatus.setImageResource(R.drawable.ic_timer)
            else
                ivStatus.setImageResource(R.drawable.ic_done)


        }

    }
}