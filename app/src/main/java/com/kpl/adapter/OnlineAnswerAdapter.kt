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
import android.widget.ArrayAdapter
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
import tech.hibk.searchablespinnerlibrary.SearchableItem
import tech.hibk.searchablespinnerlibrary.SearchableSpinner

import java.util.*


class OnlineAnswerAdapter(
    private val mContext: Context,
    var list: List<String>? = mutableListOf(),
    var type: String,
    var QueId: String,
    var que: Question,
    var surveyAnswerArray: ArrayList<SurveyAnswerData>,
    var appDatabase: AppDatabase? = null,


    ) : RecyclerView.Adapter<OnlineAnswerAdapter.ItemHolder>() {
    private var lastRadioPosition: Int = -1
    var optionAray: ArrayList<String> = ArrayList()
    var adapterOption: ArrayAdapter<String>? = null
    var itens: List<SearchableItem>? = null
    override fun getItemCount(): Int {
        return list?.size!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        var view: View? = null

        appDatabase = AppDatabase.getDatabase(mContext)!!

        if (type.equals(Constant.typeSigleSelection) || type.equals(Constant.typeSigleSelectionWithImage)) {
            view = LayoutInflater.from(mContext)
                .inflate(R.layout.row_spinner, parent, false)
        } else if (type.equals(Constant.typeMutliSelection) || type.equals(Constant.typeMutliSelectionWithImage)) {
            view = LayoutInflater.from(mContext).inflate(R.layout.row_answer_checkbox_disable, parent, false)
        } else if (type.equals(Constant.typeEdit) || type.equals(Constant.typeNumeric)|| type.equals(Constant.typeEditWithImage) || type.equals(Constant.typeDatePicker) || type.equals(Constant.typeTimePicker)) {
            view = LayoutInflater.from(mContext).inflate(R.layout.row_answer_edittext_disable, parent, false)
        }else if (type.equals(Constant.typeImageView)) {
            view = LayoutInflater.from(mContext).inflate(R.layout.row_answer_image, parent, false)
        } else if (type.equals(Constant.typeDropDown)) {

        }

        return ItemHolder(view)
    }


    override fun onBindViewHolder(holder: ItemHolder, pos: Int) {
        val data = list?.get(pos)
        holder.bindData(mContext)


        if (type.equals(Constant.typeSigleSelection) || type.equals(Constant.typeSigleSelectionWithImage)) {
         //   holder.rbOption?.setText(data.toString())

            optionAray.add(mContext.getString(R.string.select_answer))
            var list = que.Questionoption?.split(",")
            list?.let { optionAray.addAll(it) }
            var myList: MutableList<SearchableItem> = mutableListOf()


            for (items in optionAray.indices) {

                if(items==0){
                    myList.add(
                        SearchableItem(
                            0,
                            mContext.getString(R.string.select_answer)
                        )
                    )
                }else{
                    myList.add(
                        SearchableItem(
                            items.toLong(),
                            optionAray.get(items )
                        )
                    )
                }


            }


            itens = myList

            adapterOption = ArrayAdapter(
                mContext,
                R.layout.custom_spinner,
                optionAray
            )
            holder.spinner?.setAdapter(adapterOption)

            holder.spinner?.isEnabled = false
            holder.spinner?.isEnabled = false

        } else if (type.equals(Constant.typeMutliSelection) || type.equals(Constant.typeMutliSelectionWithImage)) {
            holder.cbOption?.setText(data.toString())
        }
        else if (type.equals(Constant.typeDropDown)) {

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
                        for ( i in optionAray.indices){
                            if (surveyAnswerArray.get(position).answer.toString().equals(optionAray.get(i))) {
                                holder.spinner?.setSelection(i)
                                break
                            }
                        }
                    } else if (type.equals(Constant.typeMutliSelection) || type.equals(Constant.typeMutliSelectionWithImage)) {

                        val strs = surveyAnswerArray.get(position).answer.toString().split(",")
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
                    else if (type.equals(Constant.typeDropDown)) {

                    }
                }
            }
        }).start()

    }


    inner class ItemHolder(override val containerView: View?) :
        RecyclerView.ViewHolder(containerView!!),
        LayoutContainer {
       // var rbOption: RadioButton? = null
        var cbOption: CheckBox? = null
        var edtOption: EditText? = null
        var spinner: SearchableSpinner? = null
        var view: View? = null


        fun bindData(context: Context) {
            view = containerView?.findViewById(R.id.view)
            spinner = containerView?.findViewById(R.id.spinner)
            cbOption = containerView?.findViewById(R.id.cbOption)
            edtOption = containerView?.findViewById(R.id.edtOption)


        }


    }

}

