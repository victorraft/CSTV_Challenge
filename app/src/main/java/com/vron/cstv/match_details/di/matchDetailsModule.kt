package com.vron.cstv.match_details.di

import com.vron.cstv.match_details.domain.usecase.GetTeamsDetailsList
import com.vron.cstv.match_details.presentation.MatchDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val matchDetailsModule = module {
    viewModel { MatchDetailsViewModel(getTeamDetails = get()) }

    factory { GetTeamsDetailsList(matchRepository = get()) }
}