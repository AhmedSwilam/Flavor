package kw.com.pixel.flavor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import kw.com.pixel.flavor.data.api.ApiHelper
import kw.com.pixel.flavor.utils.Resource
import kw.com.pixel.flavor.viewModel.CountryViewModel
import kw.com.pixel.flavor.viewModel.CountryViewModelProviderFactory

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: CountryViewModel
    lateinit var viewModelProviderFactory: CountryViewModelProviderFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        obtainViewModel("", "")
        subscribeToUI()
        viewModel.getCountry("", "")
    }
    fun obtainViewModel(authorization: String, type: String) {

        viewModelProviderFactory = CountryViewModelProviderFactory(ApiHelper(), authorization, type)
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(CountryViewModel::class.java)

    }

    fun subscribeToUI() {
        viewModel.CountryLiveData.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    //     response as responseCountry
                    findViewById<TextView>(R.id.text).setText(response.data?.Data?.countries?.get(4)!!.name)
                    Log.d("TAG", "subscribeToUI: response  " + response.data.toString())

                }
                is Resource.Error -> {
                    //  hideProgressBar()
                    response.message?.let { message ->
                        //   progressLoading.CreatePoPup(this, message, "error")
                        Log.e("TAG", "An error occured: $message")
                    }
                }
                is Resource.Loading -> {
                    //  showProgressBar()
                }
            }
        }

    }
}