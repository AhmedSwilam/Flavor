package kw.com.pixel.flavor.data.api


import kw.com.pixel.flavor.model.responseCountry
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface ApiService {

    @GET("countries")
    suspend fun countries(
        @Header("Authorization") authorization: String = "",
        @Query("type") type: String = "product"
    ): Response<responseCountry>


}