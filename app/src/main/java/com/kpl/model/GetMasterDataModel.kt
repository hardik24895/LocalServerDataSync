package com.kpl.model

import com.google.gson.annotations.SerializedName

data class GetMasterDataModel(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("error")
	val error: Int? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class CategoryItem(

	@field:SerializedName("CategoryID")
	val categoryID: String? = null,

	@field:SerializedName("Status")
	val status: String? = null,

	@field:SerializedName("Rno")
	val rno: String? = null,

	@field:SerializedName("Category")
	val category: String? = null,

	@field:SerializedName("CreatedBy")
	val createdBy: String? = null,

	@field:SerializedName("rowcount")
	val rowcount: String? = null,

	@field:SerializedName("CreatedDate")
	val createdDate: String? = null,

	@field:SerializedName("ModifiedBy")
	val modifiedBy: String? = null,

	@field:SerializedName("ModifiedDate")
	val modifiedDate: String? = null
)

data class EmployeeItem(

	@field:SerializedName("MobileNo")
	val mobileNo: String? = null,

	@field:SerializedName("Status")
	val status: String? = null,

	@field:SerializedName("EmailID")
	val emailID: String? = null,

	@field:SerializedName("IsDeleted")
	val isDeleted: String? = null,

	@field:SerializedName("CreatedBy")
	val createdBy: String? = null,

	@field:SerializedName("Address")
	val address: String? = null,

	@field:SerializedName("rowcount")
	val rowcount: String? = null,

	@field:SerializedName("FirstName")
	val firstName: String? = null,

	@field:SerializedName("ModifiedBy")
	val modifiedBy: String? = null,

	@field:SerializedName("ModifiedDate")
	val modifiedDate: String? = null,

	@field:SerializedName("RoleID")
	val roleID: String? = null,

	@field:SerializedName("Rno")
	val rno: String? = null,

	@field:SerializedName("UserID")
	val userID: String? = null,

	@field:SerializedName("CreatedDate")
	val createdDate: String? = null,

	@field:SerializedName("LastName")
	val lastName: String? = null,

	@field:SerializedName("UserType")
	val userType: String? = null,

	@field:SerializedName("Password")
	val password: String? = null
)

data class ProjectItem(

	@field:SerializedName("MobileNo")
	val mobileNo: String? = null,

	@field:SerializedName("Status")
	val status: String? = null,

	@field:SerializedName("CreatedBy")
	val createdBy: String? = null,

	@field:SerializedName("Address")
	val address: String? = null,

	@field:SerializedName("rowcount")
	val rowcount: String? = null,

	@field:SerializedName("ProjectID")
	val projectID: String? = null,

	@field:SerializedName("Title")
	val title: String? = null,

	@field:SerializedName("ModifiedBy")
	val modifiedBy: String? = null,

	@field:SerializedName("ModifiedDate")
	val modifiedDate: String? = null,

	@field:SerializedName("CompanyName")
	val companyName: String? = null,

	@field:SerializedName("Type")
	val type: String? = null,

	@field:SerializedName("Rno")
	val rno: String? = null,

	@field:SerializedName("CreatedDate")
	val createdDate: String? = null
)

data class Data(

	@field:SerializedName("Project")
	val project: List<ProjectItem> = mutableListOf(),

	@field:SerializedName("Employee")
	val employee: List<EmployeeItem> =  mutableListOf(),

	@field:SerializedName("Category")
	val category: List<CategoryItem> =  mutableListOf(),

	@field:SerializedName("Question")
	val question: List<QuestionItem> =  mutableListOf()
)

data class QuestionItem(

	@field:SerializedName("CategoryID")
	val categoryID: String? = null,

	@field:SerializedName("Status")
	val status: String? = null,

	@field:SerializedName("Type")
	val type: String? = null,

	@field:SerializedName("Rno")
	val rno: String? = null,

	@field:SerializedName("CreatedBy")
	val createdBy: String? = null,

	@field:SerializedName("rowcount")
	val rowcount: String? = null,

	@field:SerializedName("QuestionID")
	val questionID: String? = null,

	@field:SerializedName("CreatedDate")
	val createdDate: String? = null,

	@field:SerializedName("Question")
	val question: String? = null,

	@field:SerializedName("ModifiedBy")
	val modifiedBy: String? = null,

	@field:SerializedName("ModifiedDate")
	val modifiedDate: String? = null,

	@field:SerializedName("Questionoption")
	val questionoption: String? = null
)
