package com.example.reqres.module

import com.example.reqres.api.Config
import com.example.reqres.database.FreeDB
import com.example.reqres.model.DataRepository
import com.example.reqres.model.DomainRepository
import com.example.reqres.ui.main.MainViewModel
import com.example.reqres.utils.LoggerInterceptor
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import java.util.concurrent.TimeUnit
import retrofit2.converter.gson.GsonConverterFactory



val mainVmModule = module {
    viewModel { MainViewModel(DomainRepository(DataRepository(createRetrofit(Config.URL)),get())) }
}

val apiModule = module {
    single { FreeDB.getInstance(get()) }
    single { get<FreeDB>().userDao() }
}

val diModules = listOf(
    apiModule,
    mainVmModule
)

fun createOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .addNetworkInterceptor(LoggerInterceptor())
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .connectionPool(ConnectionPool(0, 1, TimeUnit.NANOSECONDS))
        .build()
}


inline fun <reified T> createRetrofit(serverUrl: String): T {

    val retrofit = Retrofit.Builder()
        .baseUrl(serverUrl)
        .client(createOkHttpClient())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(T::class.java)
}
