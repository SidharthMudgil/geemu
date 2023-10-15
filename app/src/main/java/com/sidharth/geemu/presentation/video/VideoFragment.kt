package com.sidharth.geemu.presentation.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.sidharth.geemu.databinding.FragmentVideoBinding

class VideoFragment : Fragment() {
    private val args: VideoFragmentArgs by navArgs()
    private lateinit var binding: FragmentVideoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoBinding.inflate(inflater)


        return binding.root
    }
}