package com.example.githubuser.network

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class TokenRequestInterceptor : Interceptor {

    private fun provideBasicAuth(): String {
        return Credentials.basic("rakapermanaptr", "")
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Content-Type", "application/json")
            .header("Authorization", provideBasicAuth())
            .build()
        return chain.proceed(request)
    }
}