package com.prototype.urbandictionary

import android.app.Application
import com.prototype.data.di.apiModule
import com.prototype.data.di.databaseModule
import com.prototype.data.di.networkingModule
import com.prototype.data.di.repositoryModule
import com.prototype.urbandictionary.di.useCaseModule
import com.prototype.urbandictionary.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

open class UrbanDictionaryApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@UrbanDictionaryApp)
            androidLogger()
            modules(appModules())
        }
    }

    /**
     * Koin Modules getter function.
     * Used in the onCreate method by the Koin framework.
     * Open to be overridden by the Test Application class.
     *
     * @return listOf Koin Modules
     */
    open fun appModules() = listOf(
        networkingModule,
        apiModule,
        databaseModule,
        repositoryModule,
        useCaseModule,
        viewModelModule
    )
}