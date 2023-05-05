package com.example.stories101

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import com.example.stories101.NotificationHandler.Companion.CHANNEL_DESCRIPTION
import com.example.stories101.NotificationHandler.Companion.CHANNEL_NAME
import com.example.stories101.databinding.ActivityMainBinding
import com.example.stories101.fragments.HomeFragment
import com.example.stories101.fragments.ProfileFragment
import com.example.stories101.fragments.SplashFragment
import com.example.stories101.fragments.VideoFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val CHANNEL_ID = "my_channel"

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        FirebaseApp.initializeApp(this)
//
//        // Inflate the layout using view binding
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        // Initialize the NavHostFragment and NavController
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        val navController = navHostFragment.navController
//
////        // Replace the default fragment with the SplashFragment
////        replaceFragment(SplashFragment())
//
//        // Set up the bottom navigation view
//        binding.bottomNavigationView.setOnItemSelectedListener {
//            when (it.itemId) {
//                R.id.home -> replaceFragment(HomeFragment())
//                R.id.profile -> replaceFragment(ProfileFragment())
//                R.id.video -> replaceFragment(VideoFragment())
//            }
//            true
//        }
//
//        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
//            if (!task.isSuccessful) {
//                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
//                return@OnCompleteListener
//            }
//
//            // Get new FCM registration token
//            val token = task.result
//
//            // Log and toast
//            val msg = "FCM registration token: $token"
//            Log.d(TAG, msg)
//            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
//
//            // Create and show a notification
//            val intent = Intent(this, NotificationHandler::class.java)
//            intent.putExtra("message", "Your notification message")
//            startService(intent)
//        })
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            // Create the NotificationChannel
//            val name = "My notification channel"
//            val descriptionText = "My notification channel description"
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
//                description = descriptionText
//            }
//            // Register the channel with the system
//            val notificationManager: NotificationManager =
//                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.createNotificationChannel(channel)
//        }
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        // Inflate the layout using view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the NavHostFragment and NavController
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Set up the bottom navigation view
//        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.home -> {
//                    supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
//                    navController.navigate(R.id.homeFragment)
//                }
//                R.id.profile -> {
//                    supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
//                    navController.navigate(R.id.profileFragment)
//                }
//                R.id.video -> {
//                    supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
//                    navController.navigate(R.id.videoFragment)
//                }
//            }
//            true
//        }

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            val currentDestinationId = navController.currentDestination?.id
            val destinationId = when (menuItem.itemId) {
                R.id.home -> R.id.homeFragment
                R.id.profile -> R.id.profileFragment
                R.id.video -> R.id.videoFragment
                else -> currentDestinationId // stay on the current fragment if no valid menu item is selected
            }
            if (destinationId != currentDestinationId) {
                if (destinationId != null) {
                    navController.popBackStack(destinationId, false)
                } // pop the back stack up to the specified destination
                if (destinationId != null) {
                    navController.navigate(destinationId)
                } // navigate to the selected destination
            }
            true
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = "FCM registration token: $token"
            Log.d(TAG, msg)
            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()

            // Create and show a notification
            val intent = Intent(this, NotificationHandler::class.java)
            intent.putExtra("message", "Your notification message")
            startService(intent)
        })

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = "My notification channel"
            val descriptionText = "My notification channel description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


//    private fun replaceFragment(fragment: Fragment) {
//        val fragmentManager = supportFragmentManager
//        val fragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.frame_layout, fragment)
//        fragmentTransaction.commit()
//    }
}
