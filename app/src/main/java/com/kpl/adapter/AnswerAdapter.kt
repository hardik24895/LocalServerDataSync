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
import com.kpl.utils.Constant
import kotlinx.android.extensions.LayoutContainer
import java.text.SimpleDateFormat
import java.util.*


class AnswerAdapter(
    private val mContext: Context,
    var list: List<String>? = mutableListOf(),
    var type: String,
    var QueId: String,
    var data: Question,
    var SurveyId: Int,
    var recview: RecyclerView,
    var appDatabase: AppDatabase? = null,
    var surveyAnswer: SurveyAnswer? = null,
    var filledAns: String? = "",

    ) : RecyclerView.Adapter<AnswerAdapter.ItemHolder>() {
    private var lastRadioPosition: Int = -1

    override fun getItemCount(): Int {
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

        val data = list?.get(position)
        holder.bindData(mContext)

        if (type.equals(Constant.typeSigleSelection)) {
            holder.rbOption?.setText(data.toString())
            holder.rbOption?.isChecked = position == lastRadioPosition

            holder.rbOption?.setOnClickListener {
                AddData(holder.rbOption!!.getText().toString(), true, true)
                recview.post(Runnable {
                    notifyItemChanged(lastRadioPosition)
                    notifyItemChanged(position)
                    lastRadioPosition = position

                })


            }

//
//            holder.rbOption?.setOnClickListener {
//
//                val copyOfLastCheckedPosition: Int = lastRadioPosition
//                lastRadioPosition = position
//
//                Handler().postDelayed({
//                    notifyDataSetChanged()
//                    //   notifyItemChanged(copyOfLastCheckedPosition)
//                    //   notifyItemChanged(lastRadioPosition)
//                }, 100)
//            }

        } else if (type.equals(Constant.typeMutliSelection)) {
            holder.cbOption?.setText(data.toString())
            holder.cbOption?.setOnClickListener {
                Log.e("TAG", "onBindViewHolder: 123    " + holder.cbOption!!.isChecked)

                AddData(holder.cbOption!!.getText().toString(), holder.cbOption!!.isChecked, false)
            }
        } else if (type.equals(Constant.typeEdit)) {
            holder.edtOption?.setText(filledAns)

            holder.edtOption?.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    AddData(s.toString(), true, true)
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                }
            })
        }

        //Set Answer
        val mainLooper = Looper.getMainLooper()
        Thread(Runnable {
            val result = appDatabase!!.surveyAnswerDao().checkRecordExist(SurveyId, QueId)
            Handler(mainLooper).post {
                if (result != null)
                    if (type.equals(Constant.typeSigleSelection)) {

                        if (result.Answer.toString().equals(holder.rbOption?.text.toString())) {
                              lastRadioPosition = position
                            holder.rbOption?.setChecked(true)
                        } else
                            holder.rbOption?.setChecked(false)
                    } else if (type.equals(Constant.typeMutliSelection)) {

                        val strs = result.Answer.toString().split(",").toTypedArray()
                        for (iteam in strs) {

                            if (iteam.equals(data.toString())) {
                                holder.cbOption?.setChecked(true)
                            }
                        }

                    } else if (type.equals(Constant.typeEdit)) {
                        holder.edtOption?.setText(result.Answer?.toString())
                    }
            }
        }).start()
    }


    private fun AddData(answer: String, isadded: Boolean, isReplace: Boolean) {
        val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        val currentDate = sdf.format(Date())
        AddAndTODatabase(
            mContext,
            isadded,
            isReplace,
            SurveyAnswer(null, SurveyId, QueId, answer, "", currentDate, "", currentDate, "1")
        ).execute()
    }


    inner class AddAndTODatabase(var mContext: Context, var isAdded: Boolean, var isReplace: Boolean, var surveyAnswer: SurveyAnswer) :
        AsyncTask<Void, Void, Boolean>() {
        override fun doInBackground(vararg params: Void?): Boolean {


            var existAns: SurveyAnswer? = null
            existAns = appDatabase!!.surveyAnswerDao().checkRecordExist(
                surveyAnswer.SurveyID,
                surveyAnswer.QuestionID.toString()
            )

            if (existAns == null) {
                appDatabase!!.surveyAnswerDao().insert(surveyAnswer)

                return true
            } else {
                if (isReplace) {
                    appDatabase!!.surveyAnswerDao().updaterecord(
                        surveyAnswer.SurveyID,
                        surveyAnswer.QuestionID.toString(),
                        surveyAnswer.Answer.toString()
                    )
                } else {
                    var addedStr: String
                    if (isAdded) {

                        if (existAns.Answer.toString().isEmpty()) {
                            addedStr = existAns.Answer.toString()
                        } else {
                            addedStr = existAns.Answer.toString() + ","
                        }

                        appDatabase!!.surveyAnswerDao().updaterecord(
                            surveyAnswer.SurveyID,
                            surveyAnswer.QuestionID.toString(),
                            addedStr + surveyAnswer.Answer.toString().replace(",,", ",")
                        )
                    } else {
                        appDatabase!!.surveyAnswerDao().updaterecord(
                            surveyAnswer.SurveyID,
                            surveyAnswer.QuestionID.toString(),
                            existAns.Answer.toString()
                                .replace("," + surveyAnswer.Answer.toString(), "")
                                .replace(surveyAnswer.Answer.toString(), "")
                                .replace(",,", "")
                        )
                    }
                }
                return false
            }

        }

        override fun onPostExecute(result: Boolean?) {
            if (result!!)
                Log.d("TAG", "onPostExecute: DATA Addedd")
            else
                Log.d("TAG", "onPostExecute: DATA Exist")
        }
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

