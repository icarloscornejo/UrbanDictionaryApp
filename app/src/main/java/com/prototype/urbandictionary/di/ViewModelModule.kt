package com.prototype.urbandictionary.di

import com.prototype.urbandictionary.presentation.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { HomeViewModel(get()) }

}