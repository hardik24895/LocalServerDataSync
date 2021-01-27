package com.kpl.model

import com.google.gson.annotations.SerializedName

data class GetServeyDataModel(

    @field:SerializedName("data")
    val data: List<ServeyDataItem> = mutableListOf(),

    @field:SerializedName("error")
    val error: Int? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("rowcount")
    val rowcount: String? = null,
)

data class ServeyDataItem(

    @field:SerializedName("Status")
    val status: String? = null,

    @field:SerializedName("CompanyAddress")
    val companyAddress: String? = null,

    @field:SerializedName("CompanyName")
    val companyName: String? = null,

    @field:SerializedName("Rno")
    val rno: String? = null,

    @field:SerializedName("ProjectTitle")
    val projectTitle: String? = null,

    @field:SerializedName("SurveyID")
    val surveyID: String? = null,

    @field:SerializedName("UserID")
    val userID: String? = null,

    @field:SerializedName("SurveyDate")
    val surveyDate: String? = null,

    @field:SerializedName("ProjectID")
    val projectID: String? = null,

    @field:SerializedName("Title")
    val title: String? = null
)
