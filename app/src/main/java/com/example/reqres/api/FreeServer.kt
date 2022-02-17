package com.example.reqres.api

import User
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface FreeServer {
    @GET("users?page=1")
    fun getUser():Single<User>
}