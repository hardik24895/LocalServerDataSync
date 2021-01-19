package com.kpl.model

data class SendSurverDataToServer(
	val data: List<DataItem?>? = null,
	val error: Int? = null,
	val message: String? = null
)

data class DataItem(
	val iD: String? = null
)

