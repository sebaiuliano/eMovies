package com.siuliano.emovies.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.siuliano.emovies.R
import com.siuliano.emovies.databinding.FragmentHomeBinding
import com.siuliano.emovies.extensions.showToolbar
import com.siuliano.emovies.model.catalog.Filters
import com.siuliano.emovies.model.movie.Movie
import com.siuliano.emovies.ui.base.MoviesAdapter
import com.siuliano.emovies.ui.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.*

const val RECOMMENDED_MOVIES_QUANTITY = 6

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: MainViewModel by sharedViewModel()

    private val clickListener = object : OnItemClickListener {
        override fun onItemClick(movie: Movie) {
            viewModel.select(movie)
            findNavController().navigate(R.id.action_homeFragment_to_detailFragment)
        }
    }

    private val upcomingAdapter = MoviesAdapter(clickListener)
    private val topRatedAdapter = MoviesAdapter(clickListener)
    private val recommendedAdapter = MoviesAdapter(clickListener)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        setObservers()
        showToolbar(false)
        setFilterButtons()
        initializeRecyclerViews()

        return binding.root
    }

    private fun setObservers() {
        viewModel.liveDataMerger.observe(viewLifecycleOwner) {
            setTopRatedMovies()
            setUpcomingMovies()
            setRecommendedMovies()
        }
        viewModel.moviesByLanguageLiveData.observe(viewLifecycleOwner) {
            if (binding.groupFilters.checkedRadioButtonId == R.id.btn_filter_language) {
                viewModel.selectRecommendation(Filters.LANGUAGE)
            }
        }
        viewModel.moviesByReleaseYearLiveData.observe(viewLifecycleOwner) {
            if (binding.groupFilters.checkedRadioButtonId == R.id.btn_filter_year) {
                viewModel.selectRecommendation(Filters.YEAR)
            }
        }
    }

    private fun setFilterButtons() {
        binding.btnFilterLanguage.text = resources.getString(R.string.movie_language_filter, Locale.getDefault().displayLanguage)
        binding.btnFilterYear.text = resources.getString(R.string.movie_year_filter, 1993)
        binding.groupFilters.setOnCheckedChangeListener { radioGroup, i ->
            when(i) {
                R.id.btn_filter_language -> {
                    viewModel.selectRecommendation(Filters.LANGUAGE)
                }
                R.id.btn_filter_year -> {
                    viewModel.selectRecommendation(Filters.YEAR)
                }
            }
        }
    }

    private fun initializeRecyclerViews() {
        initializeUpcoming()
        initializeTopRated()
        initializeRecommended()
    }

    private fun initializeTopRated() {
        binding.categoryTopRated.tvCategoryTitle.text = resources.getString(R.string.top_rated)
        binding.categoryTopRated.rvMovies.adapter = topRatedAdapter
        setTopRatedMovies()
    }

    private fun setTopRatedMovies() {
        topRatedAdapter.setMovies(viewModel.topRatedMovies)
    }

    private fun initializeUpcoming() {
        binding.categoryUpcoming.tvCategoryTitle.text = resources.getString(R.string.upcoming)
        binding.categoryUpcoming.rvMovies.adapter = upcomingAdapter
        setUpcomingMovies()
    }

    private fun setUpcomingMovies() {
        upcomingAdapter.setMovies(viewModel.upcomingMovies)
    }

    private fun initializeRecommended() {
        binding.rvCategoryRecommended.adapter = recommendedAdapter
        setRecommendedMovies()
    }

    private fun setRecommendedMovies() {
        recommendedAdapter.setMovies(viewModel.recommendedMovies.take(RECOMMENDED_MOVIES_QUANTITY))
    }

}

interface OnItemClickListener {
    fun onItemClick(movie: Movie)
}