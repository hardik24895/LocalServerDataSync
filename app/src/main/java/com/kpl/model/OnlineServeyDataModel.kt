package com.kpl.model

import com.google.gson.annotations.SerializedName
import com.kpl.database.SurveyAnswer

data class OnlineServeyDataModel(

    @field:SerializedName("data")
    val data: List<SurveyAnswerData> = mutableListOf(),

    @field:SerializedName("error")
    val error: Int? = null,

    @field:SerializedName("message")
    val message: String? = null
)

data class SurveyAnswerData(

    @field:SerializedName("Status")
    val status: String? = null,

    @field:SerializedName("Answer")
    val answer: String? = null,

    @field:SerializedName("Rno")
    val rno: String? = null,

    @field:SerializedName("SurveyID")
    val surveyID: String? = null,

    @field:SerializedName("rowcount")
    val rowcount: String? = null,

    @field:SerializedName("QuestionID")
    val questionID: String? = null,

    @field:SerializedName("SurveyanswerID")
    val surveyanswerID: String? = null,

    @field:SerializedName("Question")
    val question: String? = null
)
