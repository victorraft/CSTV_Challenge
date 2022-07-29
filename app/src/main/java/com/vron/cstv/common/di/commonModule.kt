package com.vron.cstv.common.di

import com.vron.cstv.BuildConfig
import com.vron.cstv.common.PANDASCORE_BASE_URL
import com.vron.cstv.common.data.remote.AuthInterceptor
import com.vron.cstv.common.data.remote.CsApi
import com.vron.cstv.common.data.remote.RemoteMatchDataSource
import com.vron.cstv.common.data.remote.mapper.MatchMapper
import com.vron.cstv.common.data.remote.mapper.TeamDetailsMapper
import com.vron.cstv.common.data.repository.MatchDataSource
import com.vron.cstv.common.data.repository.MatchRepositoryImpl
import com.vron.cstv.common.domain.repository.MatchRepository
import com.vron.cstv.common.presentation.DateFormatter
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val commonModule = module {
    single<MatchRepository> { MatchRepositoryImpl(matchDataSource = get()) }

    factory<MatchDataSource> { RemoteMatchDataSource(api = get(), matchMapper = get()) }

    factory { MatchMapper() }
    factory { TeamDetailsMapper() }

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

    factory { DateFormatter(context = androidContext()) }
}