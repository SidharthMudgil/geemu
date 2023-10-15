package com.sidharth.geemu.presentation.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.fragment.navArgs
import com.sidharth.geemu.databinding.FragmentVideoBinding

class VideoFragment : Fragment() {
    private val args: VideoFragmentArgs by navArgs()
    private lateinit var binding: FragmentVideoBinding

    private var player: ExoPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoBinding.inflate(inflater)

        initPlayer()

        return binding.root
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    private fun initPlayer() {
        player = ExoPlayer.Builder(requireContext()).build().apply {
            binding.playerView.player = this
            setMediaItem(MediaItem.fromUri(args.high))
            prepare()
            play()
        }
    }

    private fun releasePlayer() {
        player?.release()
    }
}