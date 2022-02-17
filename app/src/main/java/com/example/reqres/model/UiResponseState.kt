package com.example.reqres.model


sealed class UiResponseState<out T> {
    data class Loading(val message:String) : UiResponseState<Nothing>()
    data class Error(val throwable: Throwable) : UiResponseState<Nothing>()
    data class Success<T>(val item: T) : UiResponseState<T>()
}

sealed class DataResponseState<out T> {
    data class Error(val throwable: Throwable) : DataResponseState<Nothing>()
    data class Success<T>(val item: T) : DataResponseState<T>()
}