package kw.com.pixel.flavor.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kw.com.pixel.flavor.data.api.ApiHelper

class CountryViewModelProviderFactory(
    val apiHelper: ApiHelper, val authorization: String, val Type: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CountryViewModel(apiHelper,authorization,Type) as T
    }

}