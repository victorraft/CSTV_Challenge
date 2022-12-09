package com.vron.cstv.app.di

import com.vron.cstv.app.ui.AppJetpackComposeNavigator
import com.vron.cstv.match_list.ui.MatchListNavigator
import org.koin.dsl.module

val appModule = module {
    factory<MatchListNavigator> { params ->
        AppJetpackComposeNavigator(activity = params.get())
    }
}