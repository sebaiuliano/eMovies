package com.siuliano.emovies.ui.splash

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.siuliano.emovies.R
import com.siuliano.emovies.databinding.FragmentSplashBinding
import com.siuliano.emovies.extensions.showToolbar
import com.siuliano.emovies.ui.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

const val SPLASH_ANIMATION_DELAY = 1500L

class SplashFragment : Fragment() {
    private val viewModel: MainViewModel by sharedViewModel()
    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)
        showToolbar(false)
        animateSplash()
        viewModel.getInitialData()
        Handler(Looper.getMainLooper()).postDelayed({
            goHomeFragment()
        }, SPLASH_ANIMATION_DELAY)
        return binding.root
    }

    private fun goHomeFragment() {
        findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
    }

    private fun animateSplash() {
        val zoomInX = AnimatorInflater.loadAnimator(requireContext(), R.animator.zoom_in_x) as? ObjectAnimator
        zoomInX?.target = binding.ivLogo
        val zoomInY = AnimatorInflater.loadAnimator(requireContext(), R.animator.zoom_in_y) as? ObjectAnimator
        zoomInY?.target = binding.ivLogo

        val fadeIn = AnimatorInflater.loadAnimator(requireContext(), R.animator.fade_in) as? ObjectAnimator
        fadeIn?.target = binding.clSplash
        val set = AnimatorSet()
        set.playTogether(
            zoomInX, zoomInY, fadeIn
        )
        set.start()
    }
}