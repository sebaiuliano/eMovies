package com.siuliano.emovies.network.utils

import okhttp3.*
import java.util.concurrent.TimeUnit


const val ONLINE_CACHE_PARAMS = "public, max-age=5"
val OFFLINE_CACHE_PARAMS = "public, only-if-cached, max-stale=${TimeUnit.DAYS.toSeconds(7)}"

class ConnectivityInterceptor(
    private val connectivityUtils: ConnectivityUtils
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response =
        chain.proceed(chain.request().newBuilder().header(
            "Cache-Control",
            if (connectivityUtils.isOnline()) {
                ONLINE_CACHE_PARAMS
//                "public, max-age=" + 5
            } else {
                OFFLINE_CACHE_PARAMS
            }
        ).build())
}