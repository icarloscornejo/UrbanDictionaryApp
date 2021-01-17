package com.prototype.data.api.util

import okhttp3.Interceptor
import okhttp3.Response

// This is used to set required headers if an API requires to
// This also can be used to add an authorization token if required
// This HeaderInterceptor can be injected via Koin to have any data stored on the Database

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("x-rapidapi-key", com.prototype.data.util.Constants.RAPIDAPI_KEY)
                .addHeader("x-rapidapi-host", com.prototype.data.util.Constants.RAPIDAPI_HOST)
                .build()
        )
    }
}