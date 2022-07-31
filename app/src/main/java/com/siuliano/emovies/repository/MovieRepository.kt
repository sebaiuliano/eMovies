package com.siuliano.emovies.repository

import com.siuliano.emovies.extensions.extractNumbersToInt
import com.siuliano.emovies.model.configuration.Configuration
import com.siuliano.emovies.model.movie.Movie
import com.siuliano.emovies.model.movie.toMovie
import com.siuliano.emovies.network.endpoint.TMDbApi

interface MovieRepository {
    suspend fun fetchConfiguration()
    suspend fun getTopRatedMovies(): List<Movie>
    suspend fun getUpcomingMovies(): List<Movie>
    suspend fun getMoviesByLanguage(language: String): List<Movie>
    suspend fun getMoviesByReleaseYear(year: Int): List<Movie>
    suspend fun getMovieDetails(movieId: Int): Movie?
}

class MovieRepositoryImpl(
    private val tmdbApi: TMDbApi
) : MovieRepository {

    override suspend fun fetchConfiguration() {
        val configuration = tmdbApi.getConfiguration().body()
        configuration?.images?.let { config ->
            Configuration.secureBaseUrl = config.secureBaseUrl
            Configuration.largeSize = config.poster_sizes.find { size -> size == "original" } ?: config.poster_sizes.last()
            Configuration.smallSize = config.poster_sizes.first { sizeString -> sizeString.extractNumbersToInt() > 150 }
        }
    }

    override suspend fun getTopRatedMovies(): List<Movie> {
        val list: MutableList<Movie> = mutableListOf()
        val response = tmdbApi.getTopRatedMovies().body()
        if (response != null) {
            list.addAll(response.results.map { it.toMovie() })
            for (i in (response.page + 1)..10) {
                list.addAll(tmdbApi.getTopRatedMovies(page = i).body()?.results?.map{ it.toMovie() }.orEmpty())
            }
        }
        return list
    }

    override suspend fun getUpcomingMovies(): List<Movie> {
        return tmdbApi.getUpcomingMovies().body()?.results?.map{ it.toMovie() }.orEmpty()
    }

    override suspend fun getMoviesByLanguage(language: String): List<Movie> {
        return tmdbApi.getRecommendedMoviesByLanguage(language = language).body()?.results?.map { it.toMovie() }.orEmpty()
    }

    override suspend fun getMoviesByReleaseYear(year: Int): List<Movie> {
        return tmdbApi.getRecommendedMoviesByYear(year = year).body()?.results?.map { it.toMovie() }.orEmpty()
    }

    override suspend fun getMovieDetails(movieId: Int): Movie? {
        return tmdbApi.getMovieDetails(movieId = movieId).body()
    }

}