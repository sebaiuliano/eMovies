package com.siuliano.emovies.repository

import com.siuliano.emovies.model.configuration.Configuration
import com.siuliano.emovies.model.movie.Movie
import com.siuliano.emovies.network.endpoint.TMDbApi
import com.siuliano.emovies.utils.StringUtils.extractNumbersToInt

interface MovieRepository {
    suspend fun setConfiguration()
    suspend fun getTopRatedMovies(): List<Movie>
    suspend fun getUpcomingMovies(): List<Movie>
    suspend fun getMovieDetails(movieId: Int): Movie?
}

class MovieRepositoryImpl(
    private val tmdbApi: TMDbApi
) : MovieRepository {
    override suspend fun setConfiguration() {
        val configuration = tmdbApi.getConfiguration().body()
        configuration?.images?.let { config ->
            Configuration.secureBaseUrl = config.secureBaseUrl
            Configuration.largeSize = config.poster_sizes.find { size -> size == "original" } ?: config.poster_sizes.last()
            Configuration.smallSize = config.poster_sizes.first { sizeString -> sizeString.extractNumbersToInt() > 150 }
        }
    }

    override suspend fun getTopRatedMovies(): List<Movie> {
        return tmdbApi.getTopRatedMovies().body()?.results.orEmpty()
    }

    override suspend fun getUpcomingMovies(): List<Movie> {
        return tmdbApi.getUpcomingMovies().body()?.results.orEmpty()
    }

    override suspend fun getMovieDetails(movieId: Int): Movie? {
        //TODO perhaps throw an exception if movie is null?
        return tmdbApi.getMovieDetails(movieId = movieId).body()
    }

}