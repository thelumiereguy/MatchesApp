package com.thelumiereguy.matchesapp.ui.fragments


import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.thelumiereguy.matchesapp.R
import com.thelumiereguy.matchesapp.databinding.FragmentSplashBinding
import com.thelumiereguy.matchesapp.ui.LauncherViewModel


class SplashFragment : Fragment() {


    private val animSet = AnimatorSet()
    private lateinit var binding: FragmentSplashBinding
    private lateinit var launcherViewModel: LauncherViewModel
    private val handler: Handler = Handler()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_splash, container, false)
        launcherViewModel = ViewModelProviders.of(requireActivity()).get(LauncherViewModel::class.java)


        initAnimation()
        return binding.root
    }

    private fun initAnimation() {
        val move = ObjectAnimator.ofFloat(binding.imageView, "translationY", -100f)
        move.duration = 800
        val alpha1 = ObjectAnimator.ofFloat(binding.title, "alpha", 0.5f)
        alpha1.duration = 400
        val alpha2 = ObjectAnimator.ofFloat(binding.title, "alpha", 0f)
        alpha2.duration = 600
        animSet.play(alpha2).before(alpha1).with(move)
        animSet.start()
        animSet.addListener(AnimatorListenerImpl())
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }


    inner class AnimatorListenerImpl : Animator.AnimatorListener {
        override fun onAnimationRepeat(animation: Animator?) {
        }

        override fun onAnimationEnd(animation: Animator?) {
            showLoading()
            startNextScreen()
        }

        override fun onAnimationCancel(animation: Animator?) {
        }

        override fun onAnimationStart(animation: Animator?) {

        }

    }

    private fun startNextScreen() {
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed({
            launcherViewModel.showOnBoarding()
        }, 2000)
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacksAndMessages(null)
    }
}
