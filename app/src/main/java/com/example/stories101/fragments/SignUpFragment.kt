package com.example.stories101.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.example.stories101.R
import com.example.stories101.databinding.FragmentSignUpBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class SignInFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var navControl: NavController
    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.visibility=View.GONE
        // Inflate the layout for this fragment

        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated( view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        init(view)
        registerEvents()
    }
   private fun init(view:View){
       val navHostFragment = (activity as FragmentActivity).supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
       navControl= navHostFragment.navController
//       navControl = Navigation.findNavController(view)
       auth = FirebaseAuth.getInstance()
   }
    private fun registerEvents(){

        binding.gotoLoginButton.setOnClickListener(){
            navControl.navigate(R.id.action_signInFragment_to_lohInFragment)
        }

        binding.signupButton.setOnClickListener{
            val email = binding.emailField.text.toString().trim()
            val pass = binding.passwordField.text.toString().trim()

            if(email.isNotEmpty() && pass.isNotEmpty()){
                auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener({
                    if(it.isSuccessful){
//                        Toast.makeText(context, "Registered successfully", Toast.LENGTH_SHORT).show()
                        navControl.navigate(R.id.action_signInFragment_to_homeFragment)
                    }
                    else{
                        Toast.makeText(context, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.visibility = View.GONE
    }

    override fun onStop() {
        super.onStop()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.visibility = View.VISIBLE
    }
}