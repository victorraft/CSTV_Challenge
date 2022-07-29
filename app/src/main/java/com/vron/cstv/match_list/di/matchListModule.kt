package com.vron.cstv.match_list.di

import com.vron.cstv.match_list.domain.usecase.GetMatchList
import com.vron.cstv.match_list.presentation.MatchListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val matchListModule = module {
    viewModel {
        MatchListViewModel(getMatchList = get())
    }

    factory { GetMatchList(matchRepository = get()) }
}