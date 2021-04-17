package com.example.sunrise.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sunrise.repository.Repository
import com.example.sunrise.retrofit.data.JokeItem
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository): ViewModel() {
    var items: MutableLiveData<Response<List<JokeItem>>> = MutableLiveData()

    fun getItems() {
        viewModelScope.launch {
            val response: Response<List<JokeItem>> = repository.getItems()
            items.value = response
        }
    }
}