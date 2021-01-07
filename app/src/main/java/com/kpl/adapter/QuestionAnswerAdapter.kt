package com.kpl.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kpl.R
import com.kpl.interfaces.OnPosClickListener
import kotlinx.android.extensions.LayoutContainer

class QuestionAnswerAdapter(
    private val mContext: Context,
    var list: MutableList<String> = mutableListOf(),
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
        holder.bindData(mContext, data)
    }

    /* interface OnItemSelected {
         fun onItemSelect(position: Int, data: Datum)
     }*/

    class ItemHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bindData(
            context: Context,
            data: String
        ) {
            var txtNum = containerView.findViewById<TextView>(R.id.txtNum)
            txtNum.setText("" + (getAdapterPosition() + 1) + ".")

        }


    }
}