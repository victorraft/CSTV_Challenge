package com.vron.cstv

import android.app.Application
import com.vron.cstv.match_list.di.matchListModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class CsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            val logLevel = if (BuildConfig.DEBUG) Level.ERROR else Level.NONE
            androidLogger(logLevel)
            androidContext(this@CsApplication)
            modules(listOf(matchListModule))
        }
    }
}