package com.kpl.adapter

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Rect
import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatActivity.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ikovac.timepickerwithseconds.MyTimePickerDialog
import com.kpl.R
import com.kpl.database.AppDatabase
import com.kpl.database.Question
import com.kpl.database.SurveyAnswer
import com.kpl.extention.getValue
import com.kpl.extention.isEmpty
import com.kpl.utils.Constant
import com.kpl.widgets.DigitsInputFilter
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.row_answer_date_picker.*
import kotlinx.android.synthetic.main.row_answer_time_picker.*
import tech.hibk.searchablespinnerlibrary.SearchableDialog
import tech.hibk.searchablespinnerlibrary.SearchableItem
import tech.hibk.searchablespinnerlibrary.SearchableSpinner
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AnswerAdapter(
    private val mContext: Context,
    var list: List<String>? = mutableListOf(),
    var type: String,
    var QueId: String,
    var dataQue: Question,
    var SurveyId: Int,
    var recview: RecyclerView,
    var appDatabase: AppDatabase? = null,
    var surveyAnswer: SurveyAnswer? = null,
    var filledAns: String? = "",
    val myCalendar: Calendar = Calendar.getInstance()
) : RecyclerView.Adapter<AnswerAdapter.ItemHolder>() {
    private var lastRadioPosition: Int = -1
    var optionAray: ArrayList<String> = ArrayList()
    var adapterOption: ArrayAdapter<String>? = null
    var itens: List<SearchableItem>? = null
    override fun getItemCount(): Int {
        return list?.size!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        var view: View? = null
        parent.getFocusedChild()
        appDatabase = AppDatabase.getDatabase(mContext)!!

        if (type.equals(Constant.typeDropDown)) {
            view = LayoutInflater.from(mContext).inflate(R.layout.row_spinner, parent, false)
        } else if (type.equals(Constant.typeMutliSelection) || type.equals(Constant.typeMutliSelectionWithImage)) {
            view =
                LayoutInflater.from(mContext).inflate(R.layout.row_answer_checkbox, parent, false)
        } else if (type.equals(Constant.typeEdit) || type.equals(Constant.typeNumeric) || type.equals(
                Constant.typeEditWithImage
            )
        ) {
            view =
                LayoutInflater.from(mContext).inflate(R.layout.row_answer_edittext, parent, false)
        } else if (type.equals(Constant.typeDatePicker)) {
            view = LayoutInflater.from(mContext)
                .inflate(R.layout.row_answer_date_picker, parent, false)
        } else if (type.equals(Constant.typeTimePicker)) {
            view = LayoutInflater.from(mContext)
                .inflate(R.layout.row_answer_time_picker, parent, false)
        } else if (type.equals(Constant.typeImageView)) {
            view = LayoutInflater.from(mContext).inflate(R.layout.row_answer_image, parent, false)
        } else if (type.equals(Constant.typeSigleSelection) || type.equals(Constant.typeSigleSelectionWithImage)) {
            view = LayoutInflater.from(mContext)
                .inflate(R.layout.row_answer_radiobutton, parent, false)
        }

        return ItemHolder(view)
    }


    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        val data = list?.get(position)
        holder.bindData(mContext)

        if (type.equals(Constant.typeSigleSelection) || type.equals(Constant.typeSigleSelectionWithImage)) {

            holder.rbOption?.setText(data.toString())
            holder.rbOption?.isChecked = position == lastRadioPosition

            holder.rbOption?.setOnClickListener {

                AddData(holder.rbOption!!.getText().toString(), true, true)

                recview.post(Runnable {
                    notifyItemChanged(lastRadioPosition)
                    notifyItemChanged(position)
                    lastRadioPosition = position

                })
                recview.requestFocus()
                holder.rbOption?.requestFocus()
            }

        } else if (type.equals(Constant.typeMutliSelection) || type.equals(Constant.typeMutliSelectionWithImage)) {
            holder.cbOption?.setText(data.toString())
            holder.cbOption?.setOnClickListener {
                AddData(holder.cbOption!!.getText().toString(), holder.cbOption!!.isChecked, false)
            }
        } else if (type.equals(Constant.typeEdit) || type.equals(Constant.typeNumeric) || type.equals(
                Constant.typeEditWithImage
            )
        ) {

            if (type.equals(Constant.typeNumeric)) {

                if (dataQue.DataType.equals("Decimal")) {
                    holder.edtOption?.setInputType(InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL or InputType.TYPE_NUMBER_FLAG_SIGNED)

                    if (!dataQue.Max!!.toString().equals(""))
                        holder.edtOption?.setFilters(
                            arrayOf<InputFilter>(
                                DigitsInputFilter(
                                    10,
                                    2,
                                    dataQue.Max!!.toDouble()
                                )
                            )
                        )

                } else {
                    //    if (!dataQue.Max!!.toString().equals(""))

                    val maxLength = 10
                    val fArray = arrayOfNulls<InputFilter>(1)
                    fArray[0] = LengthFilter(maxLength)
                    holder.edtOption?.setFilters(fArray)

                    holder.edtOption?.setInputType(InputType.TYPE_CLASS_NUMBER)
                }

            } else {
                holder.edtOption?.setInputType(InputType.TYPE_CLASS_TEXT)
            }

            holder.edtOption?.setText(filledAns)

            holder.edtOption?.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {

//                    if (type.equals(Constant.typeNumeric)) {
//                        if (dataQue.DataType.equals("Decimal")) {
//
//
//                        } else {
//                            if()
//                        }
//                    } else
                    AddData(s.toString(), true, true)
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

//                    val format = DecimalFormat("##.##")
//                    val formatted: String = format.format(s)
//                    holder.edtOption?.setText(formatted)
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })
        } else if (type.equals(Constant.typeTimePicker)) {

            holder.edtTimeOption.setOnClickListener {

                var hours = 0
                var min = 0
                var sec = 0

                if (!holder.edtTimeOption.isEmpty()) {

                    var getTime = holder.edtTimeOption.getValue().split(":")
                    hours = getTime[0].toInt()
                    min = getTime[1].toInt()
                    sec = getTime[2].toInt()

                }


                val mTimePicker = MyTimePickerDialog(
                    mContext,
                    { view, hourOfDay, minute, seconds ->
                        holder.edtTimeOption.setText(
                            String.format("%02d", hourOfDay) + ":" + String.format(
                                "%02d",
                                minute
                            ) + ":" + String.format("%02d", seconds)
                        )
                    },
                    hours,
                    min,
                    sec,
                    true
                )
                mTimePicker.show()


            }

        } else if (type.equals(Constant.typeDatePicker)) {

            holder.edtDateOption.setOnClickListener {

                var date =
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        myCalendar.set(Calendar.YEAR, year)
                        myCalendar.set(Calendar.MONTH, monthOfYear)
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        updateLabel(holder.edtDateOption, myCalendar)
                    }


                val datePickerDialog = DatePickerDialog(
                    mContext,
                    date,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)
                )
                //  datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show()

            }

        } else if (type.equals(Constant.typeDropDown)) {
            optionAray.add(mContext.getString(R.string.select_answer))
            var list = dataQue.Questionoption?.split(",")
            list?.let { optionAray.addAll(it) }
            var myList: MutableList<SearchableItem> = mutableListOf()


            for (items in optionAray.indices) {

                if (items == 0) {
                    myList.add(
                        SearchableItem(
                            0,
                            mContext.getString(R.string.select_answer)
                        )
                    )
                } else {
                    myList.add(
                        SearchableItem(
                            items.toLong(),
                            optionAray.get(items)
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



            holder.view?.setOnClickListener {

                SearchableDialog(mContext,
                    itens!!,
                    mContext.getString(R.string.select_answer),
                    { item, _ ->
                        holder.spinner?.setSelection(item.id.toInt())
                    }).show()
            }



            holder.spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position != -1 && optionAray.size > position) {
                        AddData(optionAray.get(position).toString(), true, true)
                    }

                }

            }

        }


        //Set Answer
        val mainLooper = Looper.getMainLooper()
        Thread(Runnable {
            val result = appDatabase!!.surveyAnswerDao().checkRecordExist(SurveyId, QueId)
            Handler(mainLooper).post {
                if (result != null)
                    if (type.equals(Constant.typeSigleSelection) || type.equals(Constant.typeSigleSelectionWithImage)) {

                        if (result.Answer.toString().equals(holder.rbOption?.text.toString())) {
                            lastRadioPosition = position
                            holder.rbOption?.setChecked(true)
                        } else
                            holder.rbOption?.setChecked(false)

                    } else if (type.equals(Constant.typeMutliSelection) || type.equals(Constant.typeMutliSelectionWithImage)) {

                        val strs = result.Answer.toString().split(",").toTypedArray()
                        for (iteam in strs) {

                            if (iteam.equals(data.toString())) {
                                holder.cbOption?.setChecked(true)
                            }
                        }

                    } else if (type.equals(Constant.typeEdit) || type.equals(Constant.typeNumeric) || type.equals(
                            Constant.typeEditWithImage
                        )
                    ) {
                        holder.edtOption?.setText(result.Answer?.toString())
                    } else if (type.equals(Constant.typeDropDown)) {
                        for (i in optionAray.indices) {
                            if (result.Answer.toString().equals(optionAray.get(i))) {
                                holder.spinner?.setSelection(i)
                                break
                            }
                        }
                    }
            }
        }).start()
    }


    private fun updateLabel(editText: EditText, myCalendar: Calendar) {
        val myFormat = "MMMM dd, yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        editText.setText(sdf.format(myCalendar.time))
    }


    private fun AddData(answer: String, isadded: Boolean, isReplace: Boolean) {
        val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        val currentDate = sdf.format(Date())
        AddAndTODatabase(
            mContext,
            isadded,
            isReplace,
            SurveyAnswer(null, SurveyId, QueId, answer, "", "", currentDate, "", currentDate, "1")
        ).execute()
    }


    inner class AddAndTODatabase(
        var mContext: Context,
        var isAdded: Boolean,
        var isReplace: Boolean,
        var surveyAnswer: SurveyAnswer
    ) :
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
        var spinner: SearchableSpinner? = null
        var view: View? = null


        fun bindData(context: Context) {
            rbOption = containerView?.findViewById(R.id.rbOption)
            view = containerView?.findViewById(R.id.view)
            spinner = containerView?.findViewById(R.id.spinner)
            cbOption = containerView?.findViewById(R.id.cbOption)
            edtOption = containerView?.findViewById(R.id.edtOption)


        }


    }


}

