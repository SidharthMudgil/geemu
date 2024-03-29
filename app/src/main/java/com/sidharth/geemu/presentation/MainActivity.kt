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
import com.sidharth.geemu.core.util.NetworkUtils
import com.sidharth.geemu.databinding.ActivityMainBinding
import com.sidharth.geemu.presentation.creator.CreatorDetailsFragmentDirections
import com.sidharth.geemu.presentation.explore.ExploreFragmentDirections
import com.sidharth.geemu.presentation.game.GameDetailsFragmentDirections
import com.sidharth.geemu.presentation.games.GamesFragmentDirections
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
        if (NetworkUtils.isNetworkConnected(this).not()) {
            if (navHostFragment?.findNavController()?.currentDestination?.id == R.id.exploreFragment) {
                runOnUiThread {
                    navHostFragment?.findNavController()?.navigate(
                        ExploreFragmentDirections.actionExploreFragmentToNoNetworkFragment()
                    )
                }
            }
        }
        setupNetworkCallback()
        setupBottomNavigationBar()
    }

    override fun onPause() {
        super.onPause()
        NetworkUtils.stopNetworkCallback(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        NetworkUtils.stopNetworkCallback(this)
    }

    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        navHostFragment?.findNavController()?.currentDestination?.let {
            if (it.id != R.id.noNetworkFragment) {
                super.onBackPressed()
            } else {
                finish()
            }
        }
    }

    private fun setupNetworkCallback() {
        NetworkUtils.startNetworkCallback(
            context = applicationContext,
            onConnectionAvailable = {
                if (navHostFragment?.findNavController()?.currentDestination?.id == R.id.noNetworkFragment) {
                    runOnUiThread {
                        navHostFragment?.findNavController()?.navigateUp()
                    }
                }
            },
            onConnectionLost = {
                val action = when (navHostFragment?.findNavController()?.currentDestination?.id) {
                    R.id.exploreFragment -> ExploreFragmentDirections.actionExploreFragmentToNoNetworkFragment()
                    R.id.gamesFragment -> GamesFragmentDirections.actionGamesFragmentToNoNetworkFragment()
                    R.id.gameDetailsFragment -> GameDetailsFragmentDirections.actionGameDetailsFragmentToNoNetworkFragment()
                    R.id.creatorDetailsFragment -> CreatorDetailsFragmentDirections.actionCreatorDetailsFragmentToNoNetworkFragment()
                    else -> null
                }
                action?.let {
                    runOnUiThread {
                        navHostFragment?.findNavController()?.navigate(it)
                    }
                }
            }
        )
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