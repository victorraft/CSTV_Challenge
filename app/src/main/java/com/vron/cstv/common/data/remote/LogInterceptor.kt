package com.vron.cstv.common.data.remote

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class LogInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val method = request.method()
        val url = request.url()

        Log.d("LogInterceptor", "$method: $url")
        return chain.proceed(request)
    }
}