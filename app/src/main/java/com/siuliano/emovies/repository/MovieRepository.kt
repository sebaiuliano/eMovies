package com.siuliano.emovies.repository

import com.siuliano.emovies.model.Movie
import com.siuliano.emovies.network.endpoint.TMDbApi

interface MovieRepository {
    suspend fun getTopRatedMovies(): List<Movie>
    suspend fun getUpcomingMovies(): List<Movie>
    suspend fun getMovieDetails(movieId: Int): Movie?
}

class MovieRepositoryImpl(
    private val tmdbApi: TMDbApi
) : MovieRepository {

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