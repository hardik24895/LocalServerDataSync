package com.kpl.utils

object Constant {

    // SharedPresfrence
    const val PreferencesName = "KPL_Pref"
    var SelectedImagePosition = 0


    // Database Name
    const val DATABASE_NAME = "survey.db"

    // User Table
    const val TABLE_USER = "User"
    const val TABLE_SURVEY = "Survey"
    const val TABLE_QUESTION = "Question"
    const val TABLE_SURVEY_ANSWER = "SurveyAnswer"
    const val TABLE_PROJECT = "Project"
    const val TABLE_CATEGORY = "Category"

    // Table Name
    const val UserID = "UserID"
    const val RoleID = "RoleID"
    const val EmailID = "EmailID"
    const val Password = "Password"
    const val FirstName = "FirstName"
    const val LastName = "LastName"
    const val MobileNo = "MobileNo"
    const val Address = "Address"
    const val UserType = "UserType"

    // Table Comman Fields
    const val CreatedBy = "CreatedBy"
    const val CreatedDate = "CreatedDate"
    const val ModifiedBy = "ModifiedBy"
    const val ModifiedDate = "ModifiedDate"
    const val Status = "Status"
    const val IsDeleted = "IsDeleted"


    //Table Survey
    const val SurveyID = "SurveyID"
    const val Title = "Title"
    const val SurveyDate = "SurveyDate"

    //Table Question
    const val QuestionID = "QuestionID"
    const val Answer = "Answer"
    const val Image = "Image"

    const val Question = "Question"
    const val Type = "Type"
    const val Questionoption = "Questionoption"
    const val Min = "Min"
    const val Max = "Max"
    const val Length = "Length"
    const val DataType = "DataType"

    //Table Project
    const val ProjectID = "ProjectID"
    const val CompanyName = "CompanyName"

    //Table SurveyAnswer
    const val SurveyAnswerID = "SurveyAnswerID"

    //QuesionType
    const val typeEdit ="Edittext"
    const val typeSigleSelection ="Radio"
    const val typeDropDown ="Dropdown"
    const val typeMutliSelection ="Checkbox"
    const val typeDatePicker ="Date"
    const val typeTimePicker ="Time"
    const val typeImageView ="Image"
    const val typeEditWithImage ="Edittext + Image"
    const val typeSigleSelectionWithImage ="Radio + Image"
    const val typeMutliSelectionWithImage ="Checkbox + Image"
    const val typeNumeric ="Numeric"


    //Table Category
    const val CategoryID = "CategoryID"
    const val ParentID = "ParentID"
    const val Category = "Category"


    const val IS_REFREESH = "is_refresh"
    const val LATITUDE = "latitude"
    const val LONGITUDE = "longitude"
    const val LAST_LATITUDE = "last_latitude"
    const val LAST_LONGITUDE = "last_longitude"
    const val MY_EVENT_ID = "my_eventid"
    const val DISTANCE = "distance"
    const val STEPS = "steps"
    const val CAT_ID = "catID"
    const val BUSNESSNAME = "busnessname"
    const val SORTNAME = "sortname"
    const val ADDRESS1 = "address1"
    const val ADDRESS2 = "address2"
    const val ZIPCODE = "zipcode"
    const val EVENT_END_TIME = "event_end_time"
    const val BROADCAST_CODES = "code"
    const val TOTAL_KM = "total_km"
    const val TOTAL_TIME = "total_time"
    const val EVENT_REGISTERED = "event_registered"
    const val TITLE = "title"
    const val HOME_SYNC_TIME = "home_sync_time"
    const val EVENT_SYNC_TIME = "event_sync_time"
    const val PROFILE_SYNC_TIME = "profile_sync_time"
    const val TEN_MILISEC = 600000

    const val IS_SERVICE_RUNNING = "service_runnig"
    const val IS_PRACTICE_RUNNING = "practice_runnig"

    const val TIME = "time"
    const val TEXT = "text"

    // Common Params
    const val METHOD = "method"
    const val BODY = "body"
    const val MESSAGE = "message"
    const val ERROR = "error"
    const val ROW_COUNT = "rowCount"
    const val DATA = "data"


    //---URL Image Load---//
    const val IMG_URL = "http://demo.sprinters.club"

    // ---Server Date Time--//
    const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"


    //---- List Of Data----//
    const val LIST_EVENTSTATICS = "list_eventstatics"
    const val LIST_EVENTCATEGORY = "list_eventcategory"


    const val MOBILE = "mobile"
    const val PHONE_CODE = "phone_code"
    const val KEY_STEP_COUNT = "steps"
    const val KEY_DATE = "date"
    const val KEY_DISTANCE = "distence"
    const val UNAUTHORIZED = "unauthorized"
    const val EVENT_ID = "event_id"
    const val COUNTRY_ID = "countryId"
    const val STATE_ID = "stateId"
    const val ENABLE = "visible"
    const val USER_ID = "userId"
    const val POSITION = "position"
    const val VIEW_POSITION = "view_position"
    const val HOME = "home"
    const val TOTAL_VOTE = "totalvotes"
    const val PROFILE_PIC = "profilePic"
    const val NAME = "name"
    const val FNAME = "fname"
    const val LNAME = "lname"
    const val EMAIL = "email"
    const val COUNTRY = "country"
    const val STATE = "state"
    const val CITY = "city"
    const val URI = "uri"
    const val GLOBAL = "global"
    const val TYPE = "type"
    const val AS_HOST: String = "asHost"
    const val NOTIFICATION_TYPE = "notificationType"
    const val HOURS = "hours"
    const val LOGIN_COUNT = "logincount"
    const val MEDIA_ID = "mediaId"
    const val FILTER = "filter"
    const val PROFILE_PHOTO = "profilePhoto"
    const val COVER_PHOTO = "coverPhoto"
    const val BUCKET_NAME = "contestee"
    const val BUCKET_ACCESS_KEY = "AKIAJBM3XU4HNVVTAOXA"
    const val BUCKET_SECRETE_KEY = "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"


    //------User Profile Dialog Option---

    const val IMAGE = "image"
    const val VIDEO = "video"
    const val WATCH = "watch"
    const val WATCH_IMAGE = "watch_image"
    const val WATCH_ADD = "watch_add"


    //----Post Option-----

    const val LIKE = "Like"
    const val SAVE = "Save"
    const val SHARE = "Share"
    const val REPORT = "Report"
    const val DELETE = "Delete"
}