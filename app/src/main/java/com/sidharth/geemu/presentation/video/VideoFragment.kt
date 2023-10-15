package com.sidharth.geemu.presentation.video

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.fragment.navArgs
import com.sidharth.geemu.R
import com.sidharth.geemu.databinding.FragmentVideoBinding

class VideoFragment : Fragment() {
    private lateinit var binding: FragmentVideoBinding
    private lateinit var player: ExoPlayer

    private val args: VideoFragmentArgs by navArgs()
    private var playbackPosition = 0L
    private var isPlayerPlaying = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoBinding.inflate(inflater)

        savedInstanceState?.let {
            playbackPosition = it.getLong(STATE_RESUME_POSITION)
            isPlayerPlaying = it.getBoolean(STATE_PLAYER_PLAYING)
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initPlayer()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putLong(STATE_RESUME_POSITION, player.currentPosition)
        outState.putBoolean(STATE_PLAYER_PLAYING, player.playWhenReady)
        super.onSaveInstanceState(outState)
    }

    private fun initPlayer() {
        player = ExoPlayer.Builder(requireContext()).build().apply {
            playWhenReady = isPlayerPlaying
            repeatMode = Player.REPEAT_MODE_ONE
            seekTo(playbackPosition)
            setMediaItem(MediaItem.fromUri(args.high), false)
            prepare()
        }
        binding.playerView.player = player
        openFullScreen()
    }

    private fun openFullScreen() {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        binding.playerView.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.black
            )
        )
        (binding.playerView.layoutParams as FrameLayout.LayoutParams).apply {
            width = FrameLayout.LayoutParams.MATCH_PARENT
            height = FrameLayout.LayoutParams.MATCH_PARENT
            binding.playerView.layoutParams = this
        }
        binding.playerView.windowInsetsController?.hide(WindowInsetsCompat.Type.systemBars())
    }

    private fun closeFullScreen() {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
        binding.playerView.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.grey800
            )
        )
        (binding.playerView.layoutParams as FrameLayout.LayoutParams).apply {
            width = FrameLayout.LayoutParams.MATCH_PARENT
            height = 0
            binding.playerView.layoutParams = this
        }
        binding.playerView.windowInsetsController?.show(WindowInsetsCompat.Type.systemBars())
    }

    private fun releasePlayer() {
        isPlayerPlaying = player.playWhenReady
        playbackPosition = player.currentPosition
        binding.playerView.onPause()
        player.release()
        closeFullScreen()
    }

    companion object {
        const val STATE_RESUME_POSITION = "resumePosition"
        const val STATE_PLAYER_PLAYING = "playerOnPlay"
    }
}