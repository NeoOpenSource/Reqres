package com.example.reqres.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.reqres.model.DataResponseState
import com.example.reqres.model.DomainRepository
import com.example.reqres.model.UiResponseState
import com.example.reqres.model.UserInformation
import io.reactivex.rxjava3.kotlin.subscribeBy

class MainViewModel(private val domainRepository: DomainRepository) : ViewModel() {

    val listLiveData: LiveData<UiResponseState<List<UserInformation>>>
        get() = _listLiveData

    private val _listLiveData = MutableLiveData<UiResponseState<List<UserInformation>>>(UiResponseState.Success(emptyList()))
    fun readUserData(){
        _listLiveData.value = UiResponseState.Loading("Loading")
        domainRepository.readUserApi().subscribeBy(onSuccess = { stats ->
            when (stats){
                is DataResponseState.Success -> {
                    _listLiveData.value = UiResponseState.Success(stats.item)
                }
                is DataResponseState.Error -> {
                    _listLiveData.value = UiResponseState.Error(stats.throwable)
                }
            }
        }, onError = {
            _listLiveData.value = UiResponseState.Error(it)
        })
    }
}