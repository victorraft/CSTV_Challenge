package com.vron.cstv.match_list.di

import com.vron.cstv.BuildConfig
import com.vron.cstv.common.PANDASCORE_BASE_URL
import com.vron.cstv.match_list.data.remote.AuthInterceptor
import com.vron.cstv.match_list.data.remote.CsApi
import com.vron.cstv.match_list.data.remote.MatchMapper
import com.vron.cstv.match_list.data.remote.RemoteMatchDataSource
import com.vron.cstv.match_list.data.repository.MatchDataSource
import com.vron.cstv.match_list.data.repository.MatchRepositoryImpl
import com.vron.cstv.match_list.domain.repository.MatchRepository
import com.vron.cstv.match_list.domain.usecase.GetMatchList
import com.vron.cstv.match_list.presentation.MatchListViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val matchListModule = module {
    viewModel {
        MatchListViewModel(getMatchList = get())
    }

    single<MatchRepository> { MatchRepositoryImpl(matchDataSource = get()) }

    factory<MatchDataSource> { RemoteMatchDataSource(api = get(), matchMapper = get()) }

    factory { MatchMapper() }

    factory { GetMatchList(matchRepository = get()) }

    factory { AuthInterceptor(apiKey = BuildConfig.API_KEY) }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(get<AuthInterceptor>())
            .build()
    }

    single<CsApi> {
        Retrofit.Builder()
            .baseUrl(PANDASCORE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
            .create()
    }
}