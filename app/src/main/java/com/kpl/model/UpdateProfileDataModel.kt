package com.kpl.model

import com.google.gson.annotations.SerializedName

data class UpdateProfileDataModel(

	@field:SerializedName("data")
	val data: List<UpdateProfileDataItem> = mutableListOf(),

	@field:SerializedName("error")
	val error: Int? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class UpdateProfileDataItem(

	@field:SerializedName("ID")
	val iD: String? = null
)
