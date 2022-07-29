package com.siuliano.emovies.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.siuliano.emovies.R
import com.siuliano.emovies.databinding.FragmentHomeBinding
import com.siuliano.emovies.ui.main.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

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
        viewModel.fetchConfiguration()

        binding.btn.setOnClickListener {
            findNavController().navigate(R.id.detailFragment)
            viewModel.select(152601)
        }

        return binding.root
    }

    private fun setObservers() {
        viewModel.fetchConfigurationLiveData.observe(viewLifecycleOwner) {
            if (it) {
                viewModel.fetchConfigurationLiveData.value = false
            }
        }
    }

//    companion object {
//        @JvmStatic
//        fun newInstance() = HomeFragment()
//    }
}