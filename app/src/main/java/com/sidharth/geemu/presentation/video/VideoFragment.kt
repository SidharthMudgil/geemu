package com.sidharth.geemu.presentation.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.fragment.navArgs
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
    }

    private fun releasePlayer() {
        isPlayerPlaying = player.playWhenReady
        playbackPosition = player.currentPosition
        binding.playerView.onPause()
        player.release()
    }

    companion object {
        const val STATE_RESUME_POSITION = "resumePosition"
        const val STATE_PLAYER_FULLSCREEN = "playerFullscreen"
        const val STATE_PLAYER_PLAYING = "playerOnPlay"
    }
}