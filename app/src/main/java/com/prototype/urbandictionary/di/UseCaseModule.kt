package com.prototype.urbandictionary.di

import com.prototype.domain.usecase.FetchWordDefinitionsUseCase
import org.koin.dsl.module

val useCaseModule = module {

    factory { FetchWordDefinitionsUseCase(get()) }

}