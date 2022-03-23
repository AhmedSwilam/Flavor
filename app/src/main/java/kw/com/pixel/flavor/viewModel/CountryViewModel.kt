package kw.com.pixel.flavor.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kw.com.pixel.flavor.utils.Resource

import com.google.gson.Gson
import kw.com.pixel.flavor.data.api.ApiHelper
import kotlinx.coroutines.launch
import kw.com.pixel.flavor.model.responseCountry

import okhttp3.ResponseBody
import retrofit2.Response
import java.io.IOException


class CountryViewModel(
    val apiHelper: ApiHelper, authorization:String, Type:String) : ViewModel() {
    val CountryLiveData: MutableLiveData<Resource<responseCountry>> = MutableLiveData()

    fun getCountry(authorization:String,type:String = "product") = viewModelScope.launch {
        CountryLiveData.postValue(Resource.Loading())
        try {
            val response = apiHelper.countries( authorization,type)
            CountryLiveData.postValue(handleCountry(response))
        } catch (e: Exception) {
            Log.d("TAG", "getAuthenticate: " + e)
        }
    }

    private fun handleCountry( response: Response<responseCountry>): Resource<responseCountry> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if (response.body()!!.Success){
                    if (resultResponse.Data.countries.size>=0){
                        resultResponse.Data.countries
                    }
                    return Resource.Success(resultResponse)
                }else{
                    return Resource.Error(response.body()?.Message!!)
                }
            }
        }
        return Resource.Error(getErrorMessageFromGenericResponse(response.errorBody()!!)!!)
    }

    private fun getErrorMessageFromGenericResponse(httpException: ResponseBody): String? {
        var errorMessage: String? = null
        try {
            val body = httpException
            val adapter = Gson().getAdapter(responseCountry::class.java)
            val errorParser = adapter.fromJson(body?.string())
            Log.d("TAG", "getErrorMessageFromGenericResponse: " + errorParser)
            errorMessage = errorParser.Message
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            return errorMessage
        }
    }
}