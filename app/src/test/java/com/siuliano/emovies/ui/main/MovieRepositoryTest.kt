package com.siuliano.emovies.ui.main

import com.siuliano.emovies.model.movie.Movie
import com.siuliano.emovies.model.movie.MovieListDTO
import com.siuliano.emovies.model.movie.Videos
import com.siuliano.emovies.network.endpoint.TMDbApi
import com.siuliano.emovies.repository.MovieRepository
import com.siuliano.emovies.repository.MovieRepositoryImpl
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response.success

class MovieRepositoryTest : KoinTest {

    lateinit var movieRepository: MovieRepository

    @Mock
    lateinit var tmDbApi: TMDbApi

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this).close()
        movieRepository = MovieRepositoryImpl(tmDbApi)
    }

    @Test
    fun getTopRatedMovies() {
        runBlocking {
            Mockito.`when`(tmDbApi.getTopRatedMovies()).thenReturn(success(MovieListDTO(1, listOf(), 1)))
            val response = movieRepository.getTopRatedMovies()
            assertEquals(listOf<Movie>(), response)
        }
    }

    @Test
    fun getUpcomingMovies() {
        runBlocking {
            Mockito.`when`(tmDbApi.getUpcomingMovies()).thenReturn(success(MovieListDTO(1, listOf(), 1)))
            val response = movieRepository.getUpcomingMovies()
            assertEquals(listOf<Movie>(), response)
        }
    }

    @Test
    fun getMoviesByLanguage() {
        runBlocking {
            Mockito.`when`(tmDbApi.getRecommendedMoviesByLanguage(language = "en-US")).thenReturn(success(MovieListDTO(1, listOf(), 1)))
            val response = movieRepository.getMoviesByLanguage("en-US")
            assertEquals(listOf<Movie>(), response)
        }
    }

    @Test
    fun getMoviesByReleaseYear() {
        runBlocking {
            Mockito.`when`(tmDbApi.getRecommendedMoviesByYear(year = 1993)).thenReturn(success(MovieListDTO(1, listOf(), 1)))
            val response = movieRepository.getMoviesByReleaseYear(1993)
            assertEquals(listOf<Movie>(), response)
        }
    }

    @Test
    fun getMovieDetails() {
        runBlocking {
            Mockito.`when`(tmDbApi.getMovieDetails(1)).thenReturn(success(mockMovie()))
            val response = movieRepository.getMovieDetails(1)
            assertEquals(mockMovie(), response)
        }
    }

    private fun mockMovie() = Movie(
        1,
        emptyList(),
        "",
        "",
        "",
        "",
        "",
        0.0,
        Videos(
            emptyList()
        )
    )
}