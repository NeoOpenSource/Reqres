package com.example.reqres.model

import User
import com.example.reqres.database.UserDao
import com.example.reqres.database.UserTable
import com.example.reqres.utils.ISchedulerProvider
import com.example.reqres.utils.SchedulerProvider
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class DomainRepository(private val repository: IDataRepository, private val userDao: UserDao):IDomainRepository {

    private var schedulerProvider: ISchedulerProvider = SchedulerProvider()
    fun setISchedulerProvider(schedulerProvider: ISchedulerProvider){
        this.schedulerProvider = schedulerProvider
    }
    fun readUserApi(): Single<DataResponseState<List<UserInformation>>> {
        return readUserData().subscribeOn(schedulerProvider.io()).toObservable().flatMap {
            userListIsEmpty(it)
        }.switchIfEmpty(getUserApi().flatMap {
            toUserTable(it)
        }.flatMap {
            writeUserTable(it)
        }.flatMap {
            readUserData()
        }.toObservable()).single(DataResponseState.Error(Throwable("is default value"))).flatMap {
            toUserList(it)
        }.observeOn(schedulerProvider.mainThread())
    }

    private fun getUserApi(): Single<User> {
        return repository.getUser()
    }

    override fun toUserTable(user: User): Single<DataResponseState<List<UserTable>>> {
        val data = ArrayList<UserTable>(user.data.size)
        user.data.forEach {
            data.add(
                UserTable(
                    id = it.id.toString(),
                    first_name = it.first_name,
                    last_name = it.last_name,
                    email = it.email,
                    avatar = it.avatar
                )
            )
        }
        return Single.just(DataResponseState.Success(data))
    }

     override fun userListIsEmpty(data: DataResponseState<List<UserTable>>): Observable<DataResponseState<List<UserTable>>> {
        return when (data) {
            is DataResponseState.Error -> Observable.just(data)
            is DataResponseState.Success -> {
                if (data.item.isEmpty()) {
                    Observable.empty()
                } else {
                    Observable.just(data)
                }
            }
        }
    }


     override fun toUserList(state: DataResponseState<List<UserTable>>): Single<DataResponseState<List<UserInformation>>> {
        return when (state) {
            is DataResponseState.Error -> Single.just(state)
            is DataResponseState.Success -> {
                val data = ArrayList<UserInformation>(state.item.size)
                state.item.forEach {
                    data.add(
                        UserInformation(
                            it.id,
                            it.avatar,
                            it.email,
                            it.first_name,
                            it.last_name
                        )
                    )
                }
                Single.just(DataResponseState.Success(data))
            }
        }

    }

    private fun readUserData(): Single<DataResponseState<List<UserTable>>> {
        return Single.create {
            it.onSuccess(DataResponseState.Success(userDao.getUserList()))
        }
    }

    private fun writeUserTable(state: DataResponseState<List<UserTable>>): Single<DataResponseState<Unit>> {
        return when (state) {
            is DataResponseState.Error -> Single.just(state)
            is DataResponseState.Success -> {
                userDao.insertUserList(state.item)
                Single.just(DataResponseState.Success(Unit))
            }
        }
    }

}
