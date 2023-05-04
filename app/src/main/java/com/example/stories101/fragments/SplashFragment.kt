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
import com.google.firebase.auth.FirebaseAuth


class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
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
//                    if (navController.currentDestination?.id == R.id.splashFragment) {
//                        if (isLogin)
//                            navController.navigate(R.id.action_splashFragment_to_homeFragment)
//                        else
//                            navController.navigate(R.id.action_splashFragment_to_lohInFragment)
//                    }
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
                        }
                    }

                }, 2000)
            }
        })
        rotation.start()
    }

    private fun init(view: View) {
        mAuth = FirebaseAuth.getInstance()
        navController = findNavController()
    }
}

//
//import android.animation.Animator
//import android.animation.AnimatorListenerAdapter
//import android.animation.ObjectAnimator
//import android.os.Bundle
//import android.os.Handler
//import android.os.Looper
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import androidx.fragment.app.Fragment
//import androidx.navigation.NavController
//import androidx.navigation.fragment.findNavController
//import com.example.stories101.R
//import com.example.stories101.databinding.FragmentSplashBinding
//import com.google.firebase.auth.FirebaseAuth
//
//class SplashFragment : Fragment() {
//    private lateinit var binding: FragmentSplashBinding
//    private lateinit var mAuth: FirebaseAuth
//    private lateinit var navController: NavController
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        binding = FragmentSplashBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        init(view)
//
//        val isLogin: Boolean = mAuth.currentUser != null
//        val handler = Handler(Looper.myLooper()!!)
//
//        val appIconImageView: ImageView = binding.bookImageview
//        val rotation = ObjectAnimator.ofFloat(appIconImageView, "rotation", 0f, 360f)
//        rotation.duration = 2000
//        rotation.repeatCount = 0
//        rotation.addListener(object : AnimatorListenerAdapter() {
//            override fun onAnimationEnd(animation: Animator) {
//                handler.postDelayed({
//                    if (navController.currentDestination?.id == R.id.splashFragment) {
//                        if (isLogin) {
//                            Log.d("SplashFragment", "Navigating to HomeFragment")
//                            if (navController.previousBackStackEntry?.destination?.id == R.id.lohInFragment) {
//                                navController.popBackStack()
//                            }
//                            navController.navigate(R.id.action_splashFragment_to_homeFragment)
//                        } else {
//                            Log.d("SplashFragment", "Navigating to LoginFragment")
//                            if (navController.previousBackStackEntry?.destination?.id == R.id.homeFragment) {
//                                navController.popBackStack()
//                            }
//                            navController.navigate(R.id.action_splashFragment_to_lohInFragment)
//                        }
//                    }
//                }, 2000)
//            }
//        })
//        rotation.start()
//    }
//
//    private fun init(view: View) {
//        mAuth = FirebaseAuth.getInstance()
//        navController = findNavController()
//    }
//}
