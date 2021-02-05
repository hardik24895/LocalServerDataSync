package com.kpl.adapter

import android.content.Context
import android.graphics.PointF
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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.kpl.R
import com.kpl.database.Question
import com.kpl.model.SurveyAnswerData
import com.kpl.utils.Constant
import com.kpl.utils.Logger
import jp.wasabeef.glide.transformations.gpu.ContrastFilterTransformation
import jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation
import kotlinx.android.extensions.LayoutContainer
import java.io.File
import kotlin.math.log


class OnlineQuestionAnswerAdapter(
    private val mContext: Context,
    var list: MutableList<Question> = mutableListOf(),
    var surveyAnswerArray: ArrayList<SurveyAnswerData>,
) : RecyclerView.Adapter<OnlineQuestionAnswerAdapter.ItemHolder>() {


    override fun getItemCount(): Int {
        Log.d("TAG", "getItemCount: " + list.size)
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
        holder.bindData(mContext)
        holder.txtNum?.setText("" + (position + 1) + ".")
        holder.txtQuestion?.setText(data.Question)

        var stringArray = data.Questionoption?.split(",")

        val layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        holder.rvAns?.layoutManager = layoutManager
        var adapter = OnlineAnswerAdapter(
            mContext,
            stringArray,
            data.Type.toString(),
            data.QuestionID.toString(),
            data,
            surveyAnswerArray!!
        )
        holder.rvAns?.adapter = adapter
        if (data.Type.equals(Constant.typeEditWithImage) || data.Type.equals(Constant.typeSigleSelectionWithImage) || data.Type.equals(
                Constant.typeMutliSelectionWithImage
            ) || data.Type.equals(
                Constant.typeImageView
            )
        ) {
            holder.imgUrl?.setVisibility(View.VISIBLE)


            for (item in surveyAnswerArray!!.indices) {

                if (data.QuestionID!!.toString().equals(surveyAnswerArray.get(item).questionID)) {
                    Glide.with(mContext)
                        .load(
                            "http://societyfy.in/kppl/assets/uploads/surveyimage/" + surveyAnswerArray.get(item).imageUrl)
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .placeholder(holder.imgUrl?.drawable)
                        .into(holder.imgUrl!!)
                    Logger.d("TAG", "onBindViewHolder: URL  true")
                    break
                } else {
                    Logger.d("TAG", "onBindViewHolder: URL  false")
                }
            }


//            val mainLooper = Looper.getMainLooper()
//            Thread(Runnable {
//
//                var ImagePath: String? = ""
//
//                ImagePath = appDatabase?.surveyAnswerDao()
//                    ?.getImagePath(SurveyId, data.QuestionID.toString())
//
//                Handler(mainLooper).post {
//
//
//                    if (!ImagePath.equals("") && !ImagePath.equals(null)) {
//                        var selectedimageName =
//                            ImagePath?.replace("file:///storage/emulated/0/.kpl/", "")
//                        val file = File(
//                            Environment.getExternalStorageDirectory().toString() + "/.kpl/",
//                            selectedimageName
//                        )
//                        holder.imgUrl?.setImageURI(Uri.parse(file.toString()))
//                    }
//
//                }
//
//            }).start()


        } else {
            holder.imgUrl?.setVisibility(View.GONE)
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
}