package com.siuliano.emovies.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.siuliano.emovies.R
import com.siuliano.emovies.databinding.FragmentDetailBinding
import com.siuliano.emovies.model.configuration.Configuration
import com.siuliano.emovies.model.movie.Movie
import com.siuliano.emovies.ui.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class DetailFragment : Fragment() {
    private val viewModel: MainViewModel by sharedViewModel()
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        setObservers()
        return binding.root
    }

    private fun setObservers() {
        viewModel.selectedMovie.observe(viewLifecycleOwner) {
            //TODO show movie details information
            Timber.d("MOVIE ${it.id} - ${it.title} - ${Configuration.secureBaseUrl}/${Configuration.largeSize}/${it.posterPath}")

            setMovie(it)

            val url = "${Configuration.secureBaseUrl}/${Configuration.largeSize}/${it.posterPath}"
            Glide.with(requireContext()).load(url).into(binding.ivBackground)
        }
    }

    private fun setMovie(movie: Movie) {
        with(binding.layoutData) {
            tvMovieTitle.text = movie.title
            tvMovieYear.text = movie.releaseDate

        }
    }

//    companion object {
//        @JvmStatic
//        fun newInstance() = DetailFragment()
//    }
}