package com.vron.cstv.app

import android.app.Application
import com.vron.cstv.BuildConfig
import com.vron.cstv.app.di.appModule
import com.vron.cstv.common.di.commonModule
import com.vron.cstv.match_details.di.matchDetailsModule
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
            modules(listOf(commonModule, appModule, matchListModule, matchDetailsModule))
        }
    }
}