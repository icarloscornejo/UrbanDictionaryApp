package com.prototype.data.di

import androidx.room.Room
import com.prototype.data.db.AppDatabase
import com.prototype.data.util.Constants
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            Constants.DB_NAME
        ).build()
    }

    factory {
        (get() as AppDatabase).categoryDao()
    }

}