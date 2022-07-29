package com.siuliano.emovies.application.modules

import com.siuliano.emovies.network.endpoint.TMDbApi
import com.siuliano.emovies.repository.MovieRepository
import com.siuliano.emovies.repository.MovieRepositoryImpl
import org.koin.dsl.module

val apiRepositoryModule = module {
    fun provideMovieRepository(
        tmdbApi: TMDbApi,
    ): MovieRepository {
        return MovieRepositoryImpl(tmdbApi)
    }

    single { provideMovieRepository(get()) }
}