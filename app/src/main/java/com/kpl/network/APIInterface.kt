package com.kpl.interfaces


import com.kpl.model.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface APIInterface {

    @POST("service/")
    fun getMasterData(@Body body: RequestBody): Observable<Response<GetMasterDataModel>>


    @POST("service/")
    fun SendServeyToServer(@Body body: RequestBody): Observable<Response<SendSurverDataToServer>>

    @POST("service/")
    fun GetSurveyDataFromServer(@Body body: RequestBody): Observable<Response<GetServeyDataModel>>

    @POST("service/")
    fun GetOnlineServeyList(@Body body: RequestBody): Observable<Response<OnlineServeyDataModel>>

    @POST("service/")
    fun GetAssignProjectList(@Body body: RequestBody): Observable<Response<ProjectAssignDataModel>>

    @POST("service/")
    fun UpdateUserProfile(@Body body: RequestBody): Observable<Response<UpdateProfileDataModel>>

    @Multipart
    @POST("service/")
    fun ImageUploadApi(@Part ImageData: MultipartBody.Part,
                       @Part("method") method: RequestBody,
                       @Part("ImageName") ImageName: RequestBody
    ): Observable<Response<ImageUploadModel>>


}
