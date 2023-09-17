package com.sidharth.geemu.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.sidharth.geemu.databinding.FragmentProfileBinding
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.presentation.profile.adapter.CollectionsAdapter
import com.sidharth.geemu.presentation.profile.callback.OnGameClickCallback
import com.sidharth.geemu.presentation.profile.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(), OnGameClickCallback {

    private val profileViewModel by viewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentProfileBinding.inflate(inflater)

        binding.rvCollections.layoutManager = LinearLayoutManager(
            requireContext(), VERTICAL, false
        )

        profileViewModel.collections.observe(viewLifecycleOwner) {
            binding.rvCollections.adapter = CollectionsAdapter(
                collections = it,
                onGameClickCallback = this
            )
        }

        return binding.root
    }

    override fun onGameClick(game: Game) {
    }
}