package com.kpl.interfaces


import com.kpl.model.GetMasterDataModel
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface APIInterface {

    @POST("service/")
    fun getMasterData(@Body body: RequestBody): Observable<Response<GetMasterDataModel>>

    /* @POST("common/category/getCategories")
     fun getCategory(): Observable<Response<CategoryModal>>

     @POST("common/country/getCountries")
     fun getCountry(): Observable<Response<CountryModal>>

     @POST("common/state/getStates")
     fun getState(@Body body: RequestBody): Observable<Response<StateModal>>


     @POST("home/signup")
     fun signup(@Body body: RequestBody): Observable<Response<UserModal>>*/
}
