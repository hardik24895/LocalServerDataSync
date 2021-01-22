package com.kpl.model

import com.google.gson.annotations.SerializedName

data class ProjectAssignDataModel(

    @field:SerializedName("data")
    val data: List<ProjectDataItem> = mutableListOf(),

    @field:SerializedName("error")
    val error: Int? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("rowcount")
    val rowcount: String? = null
)

data class ProjectDataItem(

    @field:SerializedName("MobileNo")
    val mobileNo: String? = null,

    @field:SerializedName("Status")
    val status: String? = null,

    @field:SerializedName("CompanyName")
    val companyName: String? = null,

    @field:SerializedName("Type")
    val type: String? = null,

    @field:SerializedName("Rno")
    val rno: String? = null,

    @field:SerializedName("Address")
    val address: String? = null,

    @field:SerializedName("rowcount")
    val rowcount: String? = null,

    @field:SerializedName("UserID")
    val userID: String? = null,

    @field:SerializedName("ProjectID")
    val projectID: String? = null,

    @field:SerializedName("ProjectassignID")
    val projectassignID: String? = null,

    @field:SerializedName("Title")
    val title: String? = null
)
