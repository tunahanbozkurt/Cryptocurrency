package com.example.cryptocurrency.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocurrency.model.CryptoData
import com.example.cryptocurrency.service.CryptoAPI
import com.example.cryptocurrency.service.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainViewModel:ViewModel() {

    val currency = MutableLiveData<ArrayList<CryptoData>>()
    val progressBarStatus = MutableLiveData(true)
    private var cryptoDatas:ArrayList<CryptoData> = ArrayList()
    private var job:Job? = null


    fun loadData(){


        val retrofit = RetrofitClient.retrofitService


        job = viewModelScope.launch {
            withContext(Dispatchers.IO){
                val response = retrofit.getData()

                withContext(Dispatchers.Main){

                    if (response.isSuccessful){
                        response.body()?.let {
                            cryptoDatas = ArrayList(it)
                            currency.value = cryptoDatas
                            progressBarStatus.value = false

                        }
                    }
                    }
                }
            }

    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }


}