package com.example.reqres.model

import User
import com.example.reqres.api.FreeServer
import io.reactivex.rxjava3.core.Single

class DataRepository(private val service: FreeServer) {

    fun getUser(): Single<User> {
        return service.getUser()
    }
}