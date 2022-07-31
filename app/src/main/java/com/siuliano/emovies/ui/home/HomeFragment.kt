package com.siuliano.emovies.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.siuliano.emovies.R
import com.siuliano.emovies.databinding.FragmentHomeBinding
import com.siuliano.emovies.extensions.showToolbar
import com.siuliano.emovies.ui.base.MoviesAdapter
import com.siuliano.emovies.ui.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        setObservers()
        initializeRecyclerViews()
        showToolbar(false)
//        binding.btn.setOnClickListener {
//            findNavController().navigate(R.id.detailFragment)
        //TODO change this
//            viewModel.select(152601)
//        }
        setFilterButtons()

        return binding.root
    }

    private fun setObservers() {

    }

    private fun setFilterButtons() {
//        binding.btnFilterLanguage.text = resources.getString(R.string.movie_language_filter, Locale.getDefault().displayLanguage)
        //TODO add some logic here
//        binding.btnFilterYear.text = resources.getString(R.string.movie_year_filter, 1993)
    }

    private fun initializeRecyclerViews() {
        initializeUpcoming()
        initializeTopRated()
        initializeRecommended()
    }

    private fun initializeTopRated() {
        val adapter = MoviesAdapter()
        binding.categoryTopRated.tvCategoryTitle.text = resources.getString(R.string.top_rated)
        binding.categoryTopRated.rvMovies.adapter = adapter
        adapter.setMovies(viewModel.topRatedMovies)
    }
    private fun initializeUpcoming() {
        val adapter = MoviesAdapter()
        binding.categoryUpcoming.tvCategoryTitle.text = resources.getString(R.string.upcoming)
        binding.categoryUpcoming.rvMovies.adapter = adapter
        adapter.setMovies(viewModel.upcomingMovies)
    }
    private fun initializeRecommended() {
        val adapter = MoviesAdapter()
        binding.rvCategoryRecommended.adapter = adapter
        //TODO filter logic
        adapter.setMovies(viewModel.topRatedMovies.filter { it.releaseDate.startsWith("1993") })
    }
}