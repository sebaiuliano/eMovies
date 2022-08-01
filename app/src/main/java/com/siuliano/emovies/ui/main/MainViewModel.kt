package com.siuliano.emovies.ui.main

import androidx.lifecycle.*
import com.siuliano.emovies.model.catalog.Filters
import com.siuliano.emovies.model.movie.Movie
import com.siuliano.emovies.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MainViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _selectedMovie = MutableLiveData<Movie>()
    val selectedMovie : MutableLiveData<Movie>
        get() = _selectedMovie

    private val _upcomingMoviesLiveData = MutableLiveData<List<Movie>>()
    val upcomingMovies: List<Movie>
        get() = _upcomingMoviesLiveData.value ?: emptyList()

    private val _topRatedMoviesLiveData = MutableLiveData<List<Movie>>()
    val topRatedMovies: List<Movie>
        get() = _topRatedMoviesLiveData.value ?: emptyList()

    private val _recommendedMoviesLiveData = MutableLiveData<List<Movie>>()
    val recommendedMovies: List<Movie>
        get() = _recommendedMoviesLiveData.value ?: emptyList()

    val moviesByLanguageLiveData = MutableLiveData<List<Movie>>()
    private val moviesByLanguageMovies: List<Movie>
        get() = moviesByLanguageLiveData.value ?: emptyList()

    val moviesByReleaseYearLiveData = MutableLiveData<List<Movie>>()
    private val moviesByReleaseYear: List<Movie>
        get() = moviesByReleaseYearLiveData.value ?: emptyList()

    val liveDataMerger =
        MediatorLiveData<Triple<
                List<Movie>,
                List<Movie>,
                List<Movie>,
                >>()

    init {
        liveDataMerger.addSource(_upcomingMoviesLiveData) { liveDataMerger.value = liveDataMerger.value?.copy(first = it) }
        liveDataMerger.addSource(_topRatedMoviesLiveData) { liveDataMerger.value = liveDataMerger.value?.copy(second = it) }
        liveDataMerger.addSource(_recommendedMoviesLiveData) { liveDataMerger.value = liveDataMerger.value?.copy(third = it) }
    }

    fun getInitialData(){
        viewModelScope.launch{
            withContext(Dispatchers.IO) {
                movieRepository.fetchConfiguration()
            }
            getUpcomingMovies()
            getTopRatedMovies()
            getMoviesByLanguage("${Locale.getDefault().language}-${Locale.getDefault().country}")
            getMoviesByReleaseYear(1993)
        }
    }

    fun select(movie: Movie) = getMovieDetails(movie.id)

    fun selectRecommendation(recommendationType: Filters) {
        when (recommendationType) {
            Filters.YEAR -> { _recommendedMoviesLiveData.postValue(moviesByReleaseYear) }
            Filters.LANGUAGE -> { _recommendedMoviesLiveData.postValue(moviesByLanguageMovies) }
        }
    }

    private fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            _selectedMovie.postValue(
                movieRepository.getMovieDetails(movieId) ?:
                upcomingMovies.find { it.id == movieId } ?:
                topRatedMovies.find { it.id == movieId } ?:
                recommendedMovies.find { it.id == movieId }
            )
        }
    }

    private fun getUpcomingMovies() {
        viewModelScope.launch {
            _upcomingMoviesLiveData.postValue(
                movieRepository.getUpcomingMovies()
            )
        }
    }

    private fun getTopRatedMovies() {
        viewModelScope.launch {
            _topRatedMoviesLiveData.postValue(
                movieRepository.getTopRatedMovies()
            )
        }
    }

    private fun getMoviesByLanguage(language: String) {
        viewModelScope.launch {
            moviesByLanguageLiveData.postValue(
                movieRepository.getMoviesByLanguage(language)
            )
        }
    }

    private fun getMoviesByReleaseYear(year: Int) {
        viewModelScope.launch {
            moviesByReleaseYearLiveData.postValue(
                movieRepository.getMoviesByReleaseYear(year)
            )
        }
    }



}