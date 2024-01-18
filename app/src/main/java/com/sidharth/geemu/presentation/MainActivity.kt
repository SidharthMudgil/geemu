package com.sidharth.geemu.presentation

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.ActivityResult
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
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

    private val appUpdateManager: AppUpdateManager by lazy {
        AppUpdateManagerFactory.create(this)
    }
    private lateinit var installStateUpdatedListener: InstallStateUpdatedListener
    private val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        when (result.resultCode) {
            RESULT_OK -> {
                Log.d("Update flow result", "OKAY ${result.resultCode}")
            }

            RESULT_CANCELED -> {
                Log.d("Update flow result", "CANCELLED ${result.resultCode}")
            }

            ActivityResult.RESULT_IN_APP_UPDATE_FAILED -> {
                Log.d("Update flow result", "FAILED ${result.resultCode}")
            }
        }
    }

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
        setupListeners()
        setupNetworkCallback()
        setupBottomNavigationBar()
    }

    override fun onPause() {
        super.onPause()
        NetworkUtils.stopNetworkCallback(this)
    }

    override fun onResume() {
        super.onResume()
        checkForUpdateAvailability()
    }

    override fun onDestroy() {
        super.onDestroy()
        NetworkUtils.stopNetworkCallback(this)
        appUpdateManager.unregisterListener(installStateUpdatedListener)
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

    private fun setupListeners() {
        installStateUpdatedListener = InstallStateUpdatedListener { state ->
            when (state.installStatus()) {
                InstallStatus.DOWNLOADING -> {
                    val bytesDownloaded = state.bytesDownloaded()
                    val totalBytesToDownload = state.totalBytesToDownload()
                    Log.d(
                        "Download Percentage",
                        "${bytesDownloaded / totalBytesToDownload * 100}% downloaded"
                    )
                }

                InstallStatus.DOWNLOADED -> {
                    popupSnackbarForCompleteUpdate()
                }

                InstallStatus.INSTALLED -> {
//                    appUpdateManager.unregisterListener(installStateUpdatedListener)
                }

                else -> {

                }
            }
        }
        appUpdateManager.registerListener(installStateUpdatedListener)
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

    private fun popupSnackbarForCompleteUpdate() {
        Snackbar.make(
            findViewById(R.id.container),
            "An update has just been downloaded.",
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction("RESTART") { appUpdateManager.completeUpdate() }
            show()
        }
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

    private fun checkForUpdateAvailability() {
        appUpdateManager.appUpdateInfo.addOnSuccessListener { updateInfo ->
            Log.d("InAppUpdateTask", "Inside On Success Listener")

            Log.d(
                "InAppUpdateTask",
                "${updateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE}"
            )

            when {
                updateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        && updateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
                        && (updateInfo.clientVersionStalenessDays()
                    ?: 0) >= DAYS_FOR_FLEXIBLE_UPDATE -> {
                    Log.d("InAppUpdateTask", "starting immediate update")
                    startImmediateUpdate(updateInfo)
                }

                updateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        && updateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
                        && (updateInfo.clientVersionStalenessDays()
                    ?: 0) >= DAYS_FOR_FLEXIBLE_UPDATE -> {
                    Log.d("InAppUpdateTask", "starting flexible update")
                    startFlexibleUpdate(updateInfo)
                }

                updateInfo.installStatus() == InstallStatus.DOWNLOADED -> {
                    Log.d("InAppUpdateTask", "Update downloaded")
                    popupSnackbarForCompleteUpdate()
                }

                updateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS -> {
                    Log.d("InAppUpdateTask", "Update In Progress")
                    startImmediateUpdate(updateInfo)
                }
            }
        }
    }

    private fun startImmediateUpdate(appUpdateInfo: AppUpdateInfo) {
        appUpdateManager.startUpdateFlowForResult(
            appUpdateInfo,
            activityResultLauncher,
            AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build(),
        )
    }

    private fun startFlexibleUpdate(appUpdateInfo: AppUpdateInfo) {
        appUpdateManager.startUpdateFlowForResult(
            appUpdateInfo,
            activityResultLauncher,
            AppUpdateOptions.newBuilder(AppUpdateType.FLEXIBLE).build(),
        )
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

    companion object {
        const val DAYS_FOR_FLEXIBLE_UPDATE = 0
    }
}