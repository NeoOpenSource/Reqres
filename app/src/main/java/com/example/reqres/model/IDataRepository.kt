package com.example.reqres.model

import User
import com.example.reqres.database.UserTable
import io.reactivex.rxjava3.core.Single

interface  IDataRepository {
    fun getUser(): Single<User>

}