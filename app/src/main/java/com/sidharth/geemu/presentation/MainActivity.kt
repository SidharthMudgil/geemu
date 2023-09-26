package com.sidharth.geemu.presentation

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.sidharth.geemu.R
import com.sidharth.geemu.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val activityMainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var navHostFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {
        navHostFragment?.findNavController()?.apply {
            activityMainBinding.bottomNavigationView.setupWithNavController(this)
            this.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.exploreFragment, R.id.followingFragment, R.id.profileFragment -> {
                        showBottomNavigation()
                    }
                    else -> hideBottomNavigation()
                }
            }
        }
    }

    private fun showBottomNavigation() {
        activityMainBinding.bottomNavigationView.visibility = VISIBLE
    }

    private fun hideBottomNavigation() {
        activityMainBinding.bottomNavigationView.visibility = GONE
    }
}