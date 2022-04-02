package com.example.githubuser.network

import okhttp3.Interceptor
import okhttp3.Response

class TokenRequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "token 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")
            .addHeader("User-Agent", "request")
            .build()
        return chain.proceed(request)
    }
}