package com.kpl.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kpl.R
import kotlinx.android.extensions.LayoutContainer


class AnswerAdapter(
    private val mContext: Context,
    var list: List<String>? = mutableListOf(),
    var type: String,
) : RecyclerView.Adapter<AnswerAdapter.ItemHolder>() {


    override fun getItemCount(): Int {
        Log.e("TAG", "getItemCount: " + list?.size!!)
        return list?.size!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        var view: View? = null

        if (type.equals("S")) {
            view = LayoutInflater.from(mContext)
                .inflate(R.layout.row_answer_radiobutton, parent, false)
        } else if (type.equals("M")) {
            view =
                LayoutInflater.from(mContext).inflate(R.layout.row_answer_checkbox, parent, false)
        } else if (type.equals("E")) {
            view =
                LayoutInflater.from(mContext).inflate(R.layout.row_answer_edittext, parent, false)
        }

        return ItemHolder(view)
    }


    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        Log.e("TAG", "getItemCount: postion " + position)


        val data = list?.get(position)
        holder.bindData(mContext)
        if (type.equals("S")) {
            holder.rbOption?.setText(data.toString())
        } else if (type.equals("M")) {
            holder.cbOption?.setText(data.toString())
        } else if (type.equals("E")) {

        }


    }


    class ItemHolder(override val containerView: View?) : RecyclerView.ViewHolder(containerView!!),
        LayoutContainer {
        var rbOption: RadioButton? = null
        var cbOption: CheckBox? = null
        var edtOption: EditText? = null


        fun bindData(context: Context) {
            rbOption = containerView?.findViewById(R.id.rbOption)
            cbOption = containerView?.findViewById(R.id.cbOption)
            edtOption = containerView?.findViewById(R.id.edtOption)


        }

    }
}