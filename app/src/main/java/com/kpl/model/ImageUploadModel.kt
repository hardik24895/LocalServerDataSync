package com.kpl.model

import com.google.gson.annotations.SerializedName

data class ImageUploadModel(

	@field:SerializedName("error")
	val error: Int? = null,

	@field:SerializedName("message")
	val message: String? = null
)
