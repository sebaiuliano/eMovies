package com.siuliano.emovies.repository

import com.siuliano.emovies.model.configuration.Configuration
import com.siuliano.emovies.model.movie.Movie
import com.siuliano.emovies.model.movie.MovieMinimalData
import com.siuliano.emovies.network.endpoint.TMDbApi
import com.siuliano.emovies.utils.StringUtils.extractNumbersToInt

interface MovieRepository {
    suspend fun fetchConfiguration()
    suspend fun getTopRatedMovies(): List<MovieMinimalData>
    suspend fun getUpcomingMovies(): List<MovieMinimalData>
    suspend fun getMoviesByLanguage(language: String): List<MovieMinimalData>
    suspend fun getMoviesByReleaseYear(year: Int): List<MovieMinimalData>
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

    override suspend fun getTopRatedMovies(): List<MovieMinimalData> {
        val list: MutableList<MovieMinimalData> = mutableListOf()
        val response = tmdbApi.getTopRatedMovies().body()
        if (response != null) {
            list.addAll(response.results)
            for (i in (response.page + 1)..10) {
                list.addAll(tmdbApi.getTopRatedMovies(page = i).body()?.results ?: emptyList())
            }
        }
        return list
    }

    override suspend fun getUpcomingMovies(): List<MovieMinimalData> {
        return tmdbApi.getUpcomingMovies().body()?.results.orEmpty()
    }

    override suspend fun getMoviesByLanguage(language: String): List<MovieMinimalData> {
        return tmdbApi.getRecommendedMoviesByLanguage(language = language).body()?.results.orEmpty()
    }

    override suspend fun getMoviesByReleaseYear(year: Int): List<MovieMinimalData> {
        return tmdbApi.getRecommendedMoviesByYear(year = year).body()?.results.orEmpty()
    }

    override suspend fun getMovieDetails(movieId: Int): Movie? {
        return tmdbApi.getMovieDetails(movieId = movieId).body()
    }

}