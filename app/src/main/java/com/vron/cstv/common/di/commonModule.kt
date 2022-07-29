package com.vron.cstv.common.di

import com.vron.cstv.common.presentation.DateFormatter
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val commonModule = module {
    factory { DateFormatter(context = androidContext()) }
}