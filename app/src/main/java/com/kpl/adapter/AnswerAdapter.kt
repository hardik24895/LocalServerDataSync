package com.kpl.adapter

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.kpl.R
import com.kpl.database.AppDatabase
import com.kpl.database.SurveyAnswer
import com.kpl.utils.Constant
import kotlinx.android.extensions.LayoutContainer


class AnswerAdapter(
    private val mContext: Context,
    var list: List<String>? = mutableListOf(),
    var type: String,
    var QueId: String,
    var appDatabase: AppDatabase? = null,
    var surveyAnswer: SurveyAnswer? = null,

    ) : RecyclerView.Adapter<AnswerAdapter.ItemHolder>() {


    override fun getItemCount(): Int {
        Log.e("TAG", "getItemCount: " + list?.size!!)
        return list?.size!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        var view: View? = null

        appDatabase = AppDatabase.getDatabase(mContext)!!

        if (type.equals(Constant.typeSigleSelection)) {
            view = LayoutInflater.from(mContext)
                .inflate(R.layout.row_answer_radiobutton, parent, false)
        } else if (type.equals(Constant.typeMutliSelection)) {
            view =
                LayoutInflater.from(mContext).inflate(R.layout.row_answer_checkbox, parent, false)
        } else if (type.equals(Constant.typeEdit)) {
            view =
                LayoutInflater.from(mContext).inflate(R.layout.row_answer_edittext, parent, false)
        }

        return ItemHolder(view)
    }


    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        Log.e("TAG", "getItemCount: postion " + position)


        val data = list?.get(position)
        holder.bindData(mContext)
        if (type.equals(Constant.typeSigleSelection)) {
            holder.rbOption?.setText(data.toString())
        } else if (type.equals(Constant.typeMutliSelection)) {
            holder.cbOption?.setText(data.toString())
        } else if (type.equals(Constant.typeEdit)) {

        }

        holder.rbOption?.setOnClickListener {

            AddAndTODatabase(
                mContext,
                SurveyAnswer(null,1, QueId, holder.rbOption.toString(), "", "", "", "","")
            ).execute()
        }

    }

    inner class AddAndTODatabase(var mContext: Context, var surveyAnswer: SurveyAnswer) :
        AsyncTask<Void, Void, Boolean>() {
        override fun doInBackground(vararg params: Void?): Boolean {
            appDatabase!!.surveyAnswerDao().insert(surveyAnswer)

            return true
        }

        override fun onPostExecute(result: Boolean?) {

            Log.d("TAG", "onPostExecute: DATA Addedd")
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