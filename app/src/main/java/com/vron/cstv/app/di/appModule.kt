package com.vron.cstv.app.di

import com.vron.cstv.app.AppNavigator
import com.vron.cstv.match_list.ui.MatchListNavigator
import org.koin.dsl.module

val appModule = module {
    factory<MatchListNavigator> { params ->
        AppNavigator(activity = params.get())
    }
}