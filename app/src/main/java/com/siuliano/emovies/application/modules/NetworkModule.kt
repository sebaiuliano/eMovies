package com.siuliano.emovies.application.modules

import com.siuliano.emovies.BuildConfig
import com.squareup.moshi.Moshi
import com.siuliano.emovies.network.utils.ConnectivityInterceptor
import com.siuliano.emovies.network.utils.ConnectivityUtils
import com.siuliano.emovies.utils.DIConstants
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val CACHE_SIZE = (2 * 1024 * 1024).toLong()

val networkModule = module {

    fun provideRetrofit(client: OkHttpClient, moshi: Moshi, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()
    }

    single(named(DIConstants.connectivityUtils)) { ConnectivityUtils(androidContext()) }
    single(named(DIConstants.connectivityInterceptor)) { ConnectivityInterceptor(get(named(DIConstants.connectivityUtils))) }

    single(named("http")) {
        OkHttpClient.Builder()
            .cache(Cache(androidContext().cacheDir, CACHE_SIZE))
            .addInterceptor(get(named(DIConstants.connectivityInterceptor)) as ConnectivityInterceptor)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            .build()
    }

    single(named(DIConstants.tmdbApi)) {
        provideRetrofit(
            get(named("http")),
            get(),
            BuildConfig.BASE_URL
        )
    }

    single {
        Moshi.Builder()
            .build()
    }

}

