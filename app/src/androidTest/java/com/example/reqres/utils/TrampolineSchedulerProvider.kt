package com.example.reqres.utils

import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.schedulers.TestScheduler

class TrampolineSchedulerProvider :ISchedulerProvider{
    val scheduler = TestScheduler()
    override fun computation(): Scheduler = scheduler
    override fun mainThread(): Scheduler = scheduler
    override fun io(): Scheduler = scheduler
}