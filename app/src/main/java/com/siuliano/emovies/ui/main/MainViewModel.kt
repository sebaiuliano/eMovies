package com.siuliano.emovies.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siuliano.emovies.model.Movie
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
}