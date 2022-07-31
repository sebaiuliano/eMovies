package com.siuliano.emovies.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.siuliano.emovies.R
import com.siuliano.emovies.databinding.FragmentDetailBinding
import com.siuliano.emovies.extensions.showToolbar
import com.siuliano.emovies.model.movie.Movie
import com.siuliano.emovies.ui.main.MainViewModel
import com.siuliano.emovies.utils.DoubleUtils.toStringWithDecimals
import com.siuliano.emovies.utils.StringUtils
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.lang.Exception

class DetailFragment : Fragment() {
    private val viewModel: MainViewModel by sharedViewModel()
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        setObservers()
        showToolbar(true)
        return binding.root
    }

    private fun setObservers() {
        viewModel.selectedMovie.observe(viewLifecycleOwner) {
            setMovie(it)
            if (it.posterPath != null) {
                Glide.with(requireContext())
                    .load(StringUtils.getPosterUrl(it.posterPath))
                    .into(binding.ivBackground)
            }
        }
    }

    private fun setMovie(movie: Movie) {
        with(binding.layoutData) {
            tvMovieTitle.text = movie.title
            tvMovieYear.text = movie.releaseDate.substring(0, 4)
            tvMovieLanguage.text = movie.originalLanguage
            tvMovieRating.text = movie.voteAverage.toStringWithDecimals()
            var genreString = ""
            for ((index, genre) in movie.genres.withIndex()) {
                genreString += genre.name
                if (movie.genres.size != index + 1) {
                    genreString += " · "
                }
            }
            tvMovieGenres.text = genreString
            tvMoviePlotValue.text = movie.overview
            btnWatchTrailer.setOnClickListener {
                val trailer = movie.videos.results.find {
                    it.type.equals("Trailer", true) &&
                            it.official &&
                            it.site.equals("YouTube", true)
                }
                if (trailer != null) {
                    watchVideo(trailer.key)
                }
            }
        }
    }

    private fun watchVideo(id: String){
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$id")))
        } catch(e: Exception) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=$id")))
        }
    }
}