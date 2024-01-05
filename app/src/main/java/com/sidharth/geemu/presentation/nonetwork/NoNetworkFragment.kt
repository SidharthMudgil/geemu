package com.sidharth.geemu.presentation.nonetwork

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sidharth.geemu.databinding.FragmentNoNetworkBinding

class NoNetworkFragment : Fragment() {

    private lateinit var fragmentNoNetworkBinding: FragmentNoNetworkBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentNoNetworkBinding = FragmentNoNetworkBinding.inflate(inflater)
        return fragmentNoNetworkBinding.root
    }
}