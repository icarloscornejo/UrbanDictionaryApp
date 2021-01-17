package com.prototype.data.di

import com.prototype.data.api.util.HeaderInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkingModule = module {

    single {
        HeaderInterceptor()
    }

    single<Interceptor>(named("logging")) {
        HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get() as HeaderInterceptor)
            .addInterceptor(get(named("logging")) as Interceptor)
            .build()
    }

    factory {


    }

    factory {
        Retrofit.Builder()
            .baseUrl(com.prototype.data.util.Constants.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }
}