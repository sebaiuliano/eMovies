package com.siuliano.emovies.ui.main

import androidx.lifecycle.*
import com.siuliano.emovies.model.movie.Movie
import com.siuliano.emovies.model.movie.MovieMinimalData
import com.siuliano.emovies.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val _selectedMovie = MutableLiveData<Movie>()
    val selectedMovie : MutableLiveData<Movie>
        get() = _selectedMovie

    private val _upcomingMoviesLiveData = MutableLiveData<List<MovieMinimalData>>()
    val upcomingMovies: List<MovieMinimalData>
        get() = _upcomingMoviesLiveData.value ?: emptyList()

    private val _topRatedMoviesLiveData = MutableLiveData<List<MovieMinimalData>>()
    val topRatedMovies: List<MovieMinimalData>
        get() = _topRatedMoviesLiveData.value ?: emptyList()

    val liveDataMerger = MediatorLiveData<Pair<List<MovieMinimalData>, List<MovieMinimalData>>>()

    init {
        liveDataMerger.addSource(_upcomingMoviesLiveData) { liveDataMerger.value = liveDataMerger.value?.copy(first = it) }
        liveDataMerger.addSource(_topRatedMoviesLiveData) { liveDataMerger.value = liveDataMerger.value?.copy(second = it) }
    }

    fun getInitialData(){
        viewModelScope.launch{
            withContext(Dispatchers.IO) {
                movieRepository.fetchConfiguration()
            }
            getUpcomingMovies()
            getTopRatedMovies()
        }
    }

    //TODO replace with original function
    //    fun select(movie: Movie) = getMovieDetails(movie.id)
    fun select(movieId: Int) = getMovieDetails(movieId)

    private fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _selectedMovie.postValue(movieRepository.getMovieDetails(movieId))
            }
        }
    }

    private suspend fun getUpcomingMovies() {
        _upcomingMoviesLiveData.postValue(
            movieRepository.getUpcomingMovies()
        )
    }

    private suspend fun getTopRatedMovies() {
        _topRatedMoviesLiveData.postValue(
            movieRepository.getTopRatedMovies()
        )
    }
}