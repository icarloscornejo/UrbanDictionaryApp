package com.prototype.data.di

import com.prototype.data.api.UrbanDictionaryApi
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {

    single {
        get<Retrofit>().create(UrbanDictionaryApi::class.java)
    }

}