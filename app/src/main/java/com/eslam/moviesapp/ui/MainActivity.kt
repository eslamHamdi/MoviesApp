package com.eslam.moviesapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.eslam.moviesapp.R
import com.eslam.moviesapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var binding:ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding!!.root)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.movies_nav) as NavHostFragment
        val navController = navHostFragment.navController
        binding!!.bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.movieDetailsFragment) {

                binding!!.bottomNav.visibility = View.GONE
            } else {

                binding!!.bottomNav.visibility = View.VISIBLE
            }
        }
    }
}