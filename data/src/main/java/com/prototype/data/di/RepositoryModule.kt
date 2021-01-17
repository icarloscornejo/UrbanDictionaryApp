package com.prototype.data.di

import com.prototype.data.impl.SearchRepositoryImpl
import com.prototype.domain.repository.SearchRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<SearchRepository> { SearchRepositoryImpl(get(), get()) }

}