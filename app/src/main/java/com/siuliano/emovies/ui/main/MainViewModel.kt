package com.siuliano.emovies.ui.main

import androidx.lifecycle.*
import com.siuliano.emovies.model.catalog.Filters
import com.siuliano.emovies.model.movie.Movie
import com.siuliano.emovies.model.movie.MovieMinimalData
import com.siuliano.emovies.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.intellij.lang.annotations.Language
import java.util.*

class MainViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _selectedMovie = MutableLiveData<Movie>()
    val selectedMovie : MutableLiveData<Movie>
        get() = _selectedMovie

    private val _upcomingMoviesLiveData = MutableLiveData<List<MovieMinimalData>>()
    val upcomingMoviesLiveData: List<MovieMinimalData>
        get() = _upcomingMoviesLiveData.value ?: emptyList()

    private val _topRatedMoviesLiveData = MutableLiveData<List<MovieMinimalData>>()
    val topRatedMovies: List<MovieMinimalData>
        get() = _topRatedMoviesLiveData.value ?: emptyList()

    private val _recommendedMoviesLiveData = MutableLiveData<List<MovieMinimalData>>()
    val recommendedMovies: List<MovieMinimalData>
        get() = _recommendedMoviesLiveData.value ?: emptyList()

    private val _moviesByLanguageLiveData = MutableLiveData<List<MovieMinimalData>>()
    private val moviesByLanguageMovies: List<MovieMinimalData>
        get() = _moviesByLanguageLiveData.value ?: emptyList()

    private val _moviesByReleaseYearLiveData = MutableLiveData<List<MovieMinimalData>>()
    private val moviesByReleaseYearLiveData: List<MovieMinimalData>
        get() = _moviesByReleaseYearLiveData.value ?: emptyList()

    val liveDataMerger =
        MediatorLiveData<Triple<
                List<MovieMinimalData>,
                List<MovieMinimalData>,
                List<MovieMinimalData>,
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

    fun select(movie: MovieMinimalData) = getMovieDetails(movie.id)

    fun selectRecommendation(recommendationType: Filters) {
        when (recommendationType) {
            Filters.YEAR -> { _recommendedMoviesLiveData.postValue(moviesByReleaseYearLiveData) }
            Filters.LANGUAGE -> { _recommendedMoviesLiveData.postValue(moviesByLanguageMovies) }
        }
    }

    private fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            _selectedMovie.postValue(movieRepository.getMovieDetails(movieId))
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
            _moviesByLanguageLiveData.postValue(
                movieRepository.getMoviesByLanguage(language)
            )
        }
    }

    private fun getMoviesByReleaseYear(year: Int) {
        viewModelScope.launch {
            _moviesByReleaseYearLiveData.postValue(
                movieRepository.getMoviesByReleaseYear(year)
            )
        }
    }



}