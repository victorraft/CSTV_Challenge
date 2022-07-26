package com.vron.cstv.match_list.di

import com.vron.cstv.match_list.data.repository.MatchRepositoryImpl
import com.vron.cstv.match_list.domain.repository.MatchRepository
import com.vron.cstv.match_list.domain.usecase.GetMatchList
import com.vron.cstv.match_list.presentation.MatchListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val matchListModule = module {
    viewModel {
        MatchListViewModel(getMatchList = get())
    }

    single<MatchRepository> { MatchRepositoryImpl() }

    factory { GetMatchList(matchRepository = get()) }
}