package com.kpl.adapter

import android.content.Context
import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
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
import com.kpl.database.Question
import com.kpl.database.SurveyAnswer
import com.kpl.model.SurveyAnswerData
import com.kpl.utils.Constant
import kotlinx.android.extensions.LayoutContainer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*


class OnlineAnswerAdapter(
    private val mContext: Context,
    var list: List<String>? = mutableListOf(),
    var type: String,
    var QueId: String,
    var data: Question,
    var surveyAnswerArray: ArrayList<SurveyAnswerData>,
    var appDatabase: AppDatabase? = null,


    ) : RecyclerView.Adapter<OnlineAnswerAdapter.ItemHolder>() {
    private var lastRadioPosition: Int = -1

    override fun getItemCount(): Int {
        return list?.size!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        var view: View? = null

        appDatabase = AppDatabase.getDatabase(mContext)!!

        if (type.equals(Constant.typeSigleSelection) || type.equals(Constant.typeSigleSelectionWithImage)) {
            view = LayoutInflater.from(mContext)
                .inflate(R.layout.row_answer_radiobutton_disable, parent, false)
        } else if (type.equals(Constant.typeMutliSelection) || type.equals(Constant.typeMutliSelectionWithImage)) {
            view = LayoutInflater.from(mContext).inflate(R.layout.row_answer_checkbox_disable, parent, false)
        } else if (type.equals(Constant.typeEdit) || type.equals(Constant.typeNumeric)|| type.equals(Constant.typeEditWithImage) || type.equals(Constant.typeDatePicker) || type.equals(Constant.typeTimePicker)) {
            view = LayoutInflater.from(mContext).inflate(R.layout.row_answer_edittext_disable, parent, false)
        }else if (type.equals(Constant.typeImageView)) {
            view = LayoutInflater.from(mContext).inflate(R.layout.row_answer_image, parent, false)
        }

        return ItemHolder(view)
    }


    override fun onBindViewHolder(holder: ItemHolder, pos: Int) {
        val data = list?.get(pos)
        holder.bindData(mContext)


        if (type.equals(Constant.typeSigleSelection) || type.equals(Constant.typeSigleSelectionWithImage)) {
            holder.rbOption?.setText(data.toString())
        } else if (type.equals(Constant.typeMutliSelection) || type.equals(Constant.typeMutliSelectionWithImage)) {
            holder.cbOption?.setText(data.toString())
        }

        val mainLooper = Looper.getMainLooper()
        Thread(Runnable {
            var position: Int = -1
          //  Log.d("TAG", "onBindViewHolder:1   ${data.toString()}")
            for (items in surveyAnswerArray.indices) {
                if (surveyAnswerArray.get(items).questionID.equals(QueId)) {

                    position = items
                    break
                }
            }


            Handler(mainLooper).post {
                if (position != -1) {
                    if (type.equals(Constant.typeSigleSelection) || type.equals(Constant.typeSigleSelectionWithImage)) {
                        if (surveyAnswerArray.get(position).answer.toString()
                                .equals(holder.rbOption?.text.toString())
                        ) {
                            holder.rbOption?.setChecked(true)
                        } else
                            holder.rbOption?.setChecked(false)
                    } else if (type.equals(Constant.typeMutliSelection) || type.equals(Constant.typeMutliSelectionWithImage)) {

                        val strs =
                            surveyAnswerArray.get(position).answer.toString().split(",")
                                .toTypedArray()
                        for (iteam in strs) {

                            if (iteam.equals(data.toString())) {
                                holder.cbOption?.setChecked(true)
                            }
                        }

                    } else if (type.equals(Constant.typeEdit) || type.equals(Constant.typeNumeric)|| type.equals(Constant.typeEditWithImage) || type.equals(
                            Constant.typeDatePicker
                        ) || type.equals(Constant.typeTimePicker)
                    ) {
                        holder.edtOption?.setText(surveyAnswerArray.get(position).answer?.toString())
                    }
                }
            }
        }).start()

    }


    inner class ItemHolder(override val containerView: View?) :
        RecyclerView.ViewHolder(containerView!!),
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

