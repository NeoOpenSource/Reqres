package com.example.reqres.model

import User
import com.example.reqres.database.UserTable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface IDomainRepository {

    fun userListIsEmpty(data: DataResponseState<List<UserTable>>): Observable<DataResponseState<List<UserTable>>>

    fun toUserTable(user: User): Single<DataResponseState<List<UserTable>>>

    fun toUserList(state: DataResponseState<List<UserTable>>): Single<DataResponseState<List<UserInformation>>>
}