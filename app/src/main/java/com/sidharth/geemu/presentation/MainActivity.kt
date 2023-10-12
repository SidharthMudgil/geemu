package com.sidharth.geemu.presentation

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.sidharth.geemu.R
import com.sidharth.geemu.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val activityMainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var navHostFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
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
        ObjectAnimator.ofFloat(
            activityMainBinding.bottomNavigationView,
            "translationX",
            -activityMainBinding.bottomNavigationView.width.toFloat(),
            0f,
        ).apply {
            duration = 0
            start()
        }
        activityMainBinding.bottomNavigationView.visibility = VISIBLE
    }

    private fun hideBottomNavigation() {
        ObjectAnimator.ofFloat(
            activityMainBinding.bottomNavigationView,
            "translationX",
            0f,
            -activityMainBinding.bottomNavigationView.width.toFloat()
        ).apply {
            duration = 300
            start()
        }
        lifecycleScope.launch {
            delay(300)
            activityMainBinding.bottomNavigationView.visibility = GONE
        }
    }
}