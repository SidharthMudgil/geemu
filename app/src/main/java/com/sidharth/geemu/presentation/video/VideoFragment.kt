package com.sidharth.geemu.presentation.video

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.devbrackets.android.exomedia.listener.OnPreparedListener
import com.sidharth.geemu.databinding.FragmentVideoBinding

class VideoFragment : Fragment(), OnPreparedListener {
    private val args: VideoFragmentArgs by navArgs()
    private lateinit var binding: FragmentVideoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoBinding.inflate(inflater)

        setupVideoView()

        return binding.root
    }

    private fun setupVideoView() {
        binding.videoView.setOnPreparedListener(this)
        binding.videoView.setMedia(Uri.parse(args.high))
    }

    override fun onPrepared() {
        binding.videoView.start()
    }
}