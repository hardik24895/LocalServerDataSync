package com.kpl.adapter

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kpl.R
import com.kpl.activity.QuestionAnswerActivity
import com.kpl.database.AppDatabase
import com.kpl.database.Question
import com.kpl.database.SurveyAnswer
import com.kpl.ui.dialog.ImagePickerBottomSheetDialog
import com.kpl.utils.Constant
import com.kpl.utils.Constant.SelectedImagePosition
import com.kpl.utils.Constant.typeEditWithImage
import com.kpl.utils.Constant.typeImageView
import com.kpl.utils.Constant.typeMutliSelectionWithImage
import com.kpl.utils.Constant.typeSigleSelectionWithImage
import com.kpl.utils.SessionManager
import com.yalantis.ucrop.UCrop
import kotlinx.android.extensions.LayoutContainer
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class QuestionAnswerAdapter(
    private val mContext: Context,
    var list: MutableList<Question> = mutableListOf(),
    var SurveyId: Int,
    var sessionManager: SessionManager? = null,
    var appDatabase: AppDatabase? = null
) : RecyclerView.Adapter<QuestionAnswerAdapter.ItemHolder>() {


    override fun getItemCount(): Int {
        Log.d("TAG", "getItemCount: " + list.size)
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        sessionManager = SessionManager(mContext)
        appDatabase = AppDatabase.getDatabase(mContext)
        return ItemHolder(
            LayoutInflater.from(mContext).inflate(
                R.layout.row_question_answer,
                parent, false
            )
        )
    }


    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        val data = list[position]
        holder.bindData(mContext)
        holder.txtNum?.setText("" + (position + 1) + ".")
        holder.txtQuestion?.setText(data.Question)

        //holder?.imgUrl?.setImageURI()

        var stringArray = data.Questionoption?.split(",")
        if (data.Type.equals(Constant.typeDropDown) ){
            stringArray = listOf("")
        }

        val layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        holder.rvAns?.layoutManager = layoutManager
        var adapter = AnswerAdapter(mContext, stringArray, data.Type.toString(), data.QuestionID.toString(), data, SurveyId, holder.rvAns!!)
        holder.rvAns?.adapter = adapter
        if (data.Type.equals(typeEditWithImage) || data.Type.equals(typeSigleSelectionWithImage)
            || data.Type.equals(typeMutliSelectionWithImage) || data.Type.equals(typeImageView)
        ) {
            holder.imgUrl?.setVisibility(View.VISIBLE)

            val mainLooper = Looper.getMainLooper()
            Thread(Runnable {

                var ImagePath: String? = ""

                ImagePath = appDatabase?.surveyAnswerDao()
                    ?.getImagePath(SurveyId, data.QuestionID.toString())

                Handler(mainLooper).post {


                    if (!ImagePath.equals("") && !ImagePath.equals(null)) {
                        var selectedimageName =
                            ImagePath?.replace("file:///storage/emulated/0/.kpl/", "")
                        val file = File(
                            Environment.getExternalStorageDirectory().toString() + "/.kpl/",
                            selectedimageName
                        )
                        holder.imgUrl?.setImageURI(Uri.parse(file.toString()))
                    }

                }

            }).start()


        } else {
            holder.imgUrl?.setVisibility(View.GONE)
        }


        holder.imgUrl?.setOnClickListener {

            val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
            val currentDate = sdf.format(Date())



            SelectedImagePosition = data.QuestionID!!
            val dialog = ImagePickerBottomSheetDialog
                .newInstance(mContext,
                    object : ImagePickerBottomSheetDialog.OnModeSelected {
                        override fun onMediaPicked(uri: Uri) {

//
//                            val direct =
//                                File(Environment.getExternalStorageDirectory().toString() + "/.kpl")
//                            if (!direct.exists()) {
//                                val wallpaperDirectory = File(
//                                    Environment.getExternalStorageDirectory().toString() + "/.kpl/"
//                                )
//                                wallpaperDirectory.mkdirs()
//                            }
//                            val destinationUri = Uri.fromFile(
//                                File(
//                                    Environment.getExternalStorageDirectory().toString() + "/.kpl/",
//                                    "IMG_${System.currentTimeMillis()}_user_${
//                                        sessionManager?.getDataByKey(SessionManager.SPUserID)
//                                    }_Que_${data.QuestionID.toString()}.jpg"
//                                )
//                            )


                            val destinationUri = Uri.fromFile(
                                File(
                                    (mContext as QuestionAnswerActivity).cacheDir,
                                    "IMG_" + System.currentTimeMillis()
                                )
                            )
                            Log.d("TAG", "onMediaPicked: "+destinationUri)
                            UCrop.of(uri, destinationUri).withAspectRatio(1f, 1f)
                                .start(mContext as QuestionAnswerActivity)
                        }

                        override fun onError(message: String) {
                            var dialog: AlertDialog? = null
                            dialog = AlertDialog.Builder(mContext, R.style.MyAlertDialogTheme)
                                .setMessage(message)
                                .setCancelable(true)
                                .setPositiveButton(
                                    mContext.getString(R.string.ok)
                                ) { dialog, which -> dialog.dismiss() }
                                .create()

                            dialog?.show()
                        }
                    })
            dialog.show((mContext as QuestionAnswerActivity).supportFragmentManager, "ImagePicker")

            var obj = QuestionAnswerActivity()
            obj.onHolderDataItem(
                holder,
                SurveyAnswer(
                    null,
                    SurveyId,
                    data.QuestionID.toString(),
                    "",
                    "",
                    "",
                    currentDate,
                    "",
                    currentDate,
                    "1"
                )
            )
        }


    }


    class ItemHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        var txtNum: TextView? = null
        var txtQuestion: TextView? = null
        var rvAns: RecyclerView? = null
        var imgUrl: ImageView? = null


        fun bindData(context: Context) {
            txtNum = containerView.findViewById(R.id.txtNum)
            txtQuestion = containerView.findViewById(R.id.txtQuestion)
            rvAns = containerView.findViewById(R.id.rvAns)
            imgUrl = containerView.findViewById(R.id.uploadImage)


        }

    }


    interface onImageCliks {
        fun onImageItemCilck(
            s: ItemHolder?,
            bitmap: Uri?,
            surveyAnswer1: SurveyAnswer,
            mContext: Context
        )
    }

    class MyImageSelected : onImageCliks {
        override fun onImageItemCilck(
            holder: ItemHolder?,
            bitmap: Uri?,
            surveyAnswer: SurveyAnswer,
            mContext: Context
        ) {

            //  var appDatabase = AppDatabase.getDatabase(mContext)!!

            holder?.imgUrl?.setImageURI(bitmap)

        }

    }


}