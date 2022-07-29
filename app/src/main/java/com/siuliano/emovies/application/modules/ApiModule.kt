package com.siuliano.emovies.application.modules

import com.siuliano.emovies.network.endpoint.TMDbApi
import com.siuliano.emovies.utils.DIConstants
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    fun provideTMDbApi(retrofit: Retrofit): TMDbApi {
        return retrofit.create(TMDbApi::class.java)
    }

    single { provideTMDbApi(get(named(DIConstants.tmdbApi))) }
}