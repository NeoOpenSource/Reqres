package com.example.reqres.model

import Data
import Support
import User
import com.example.reqres.utils.ISchedulerProvider
import com.example.reqres.utils.SchedulerProvider
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import java.util.concurrent.TimeUnit

class MockDataRepository : IDataRepository {
    private var schedulerProvider: ISchedulerProvider = SchedulerProvider()

    fun setISchedulerProvider(schedulerProvider: ISchedulerProvider) {
        this.schedulerProvider = schedulerProvider
    }
    override fun getUser(): Single<User> {
        return Observable.create<User> {
            val listData: List<Data> = arrayListOf(
                Data("1", "1", "Wan_1", 0, "Tom_1"),
                Data("2", "2", "Wan_2", 1, "Tom_2")
            )
            val testUser = User(listData, 0, 0, Support("test", "url"), 0, 0)
            it.onNext(testUser)
            it.onComplete()
        }.delay(2,TimeUnit.SECONDS,schedulerProvider.computation()).single(User(arrayListOf(), 0, 0, Support("", ""), 0, 0))
    }
}