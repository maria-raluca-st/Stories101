package com.example.stories101.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.stories101.R
import com.example.stories101.databinding.FragmentSplashBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Hide the BottomNavigationView only on the SplashFragment
//        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.visibility=View.GONE
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)

        val isLogin: Boolean = mAuth.currentUser != null
        val handler = Handler(Looper.myLooper()!!)

        val appIconImageView: ImageView = binding.bookImageview
        val rotation = ObjectAnimator.ofFloat(appIconImageView, "rotation", 0f, 360f)
        rotation.duration = 2000
        rotation.repeatCount = 0
        rotation.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                handler.postDelayed({
                    if (navController.currentDestination?.id == R.id.splashFragment) {
                        if (isLogin) {
                            Log.d("SplashFragment", "Navigating to HomeFragment")
                            if (navController.currentDestination?.id == R.id.lohInFragment) {
                                navController.popBackStack()
                            }
                            navController.navigate(R.id.action_splashFragment_to_homeFragment)
                        } else {
                            Log.d("SplashFragment", "Navigating to LoginFragment")
                            if (navController.currentDestination?.id == R.id.homeFragment) {
                                navController.popBackStack()
                            }
                            navController.navigate(R.id.action_splashFragment_to_lohInFragment)
                            navController.popBackStack()
                        }
                    }
                }, 2000)
            }
        })
        rotation.start()

        view.setOnClickListener {
            if (navController.currentDestination?.id == R.id.splashFragment) {
                if (isLogin) {
                    navController.navigate(R.id.action_splashFragment_to_homeFragment)
                } else {
                    navController.navigate(R.id.action_splashFragment_to_lohInFragment)
                }
            }
        }
    }

    private fun init(view: View) {
        mAuth = FirebaseAuth.getInstance()
        navController = findNavController()
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        // Hide the bottom navigation view only if it's in the Splash fragment
//        if (requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) is SplashFragment) {
//            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.visibility = View.GONE
//        }
//    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.visibility = View.GONE
    }

    override fun onStop() {
        super.onStop()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.visibility = View.VISIBLE
    }
}
