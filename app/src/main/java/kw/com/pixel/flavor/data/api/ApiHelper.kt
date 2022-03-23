package kw.com.pixel.flavor.data.api


class ApiHelper{

    suspend fun countries(authorization:String,type:String = "product") =
        RetrofitInstance.api.countries(authorization,type)


}