package com.vron.cstv.match_details.di

import com.vron.cstv.match_details.domain.usecase.GetTeamsDetailsList
import org.koin.dsl.module

val matchDetailsModule = module {
    factory { GetTeamsDetailsList(matchRepository = get()) }
}