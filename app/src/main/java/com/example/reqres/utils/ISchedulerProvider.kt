package com.example.reqres.utils

import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

interface ISchedulerProvider {
    fun computation(): Scheduler = Schedulers.trampoline()
    fun mainThread(): Scheduler = Schedulers.trampoline()
    fun io(): Scheduler = Schedulers.trampoline()
}