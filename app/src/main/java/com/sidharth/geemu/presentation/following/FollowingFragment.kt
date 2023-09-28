package com.sidharth.geemu.presentation.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.google.android.material.chip.Chip
import com.sidharth.geemu.databinding.FragmentFollowingBinding
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.domain.model.Tag
import com.sidharth.geemu.presentation.following.adapter.GamesAdapter
import com.sidharth.geemu.presentation.following.callback.OnGameClickCallback
import com.sidharth.geemu.presentation.following.callback.OnUnfollowButtonClickCallback
import com.sidharth.geemu.presentation.following.viewmodel.FollowingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FollowingFragment : Fragment(), OnGameClickCallback, OnUnfollowButtonClickCallback {

    private val followingViewModel: FollowingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFollowingBinding.inflate(inflater)

        binding.rvFollowing.layoutManager = LinearLayoutManager(
            requireContext(), VERTICAL, false
        )
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                followingViewModel.following.collect {
                    it.forEach { tag ->
                        val chip = Chip(requireContext())
                        chip.text = tag.name
                        chip.isCheckable = true
                        binding.cgFollowing.addView(chip)
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                followingViewModel.games.collect {
                    binding.rvFollowing.adapter = GamesAdapter(
                        onGameClickCallback = this@FollowingFragment,
                        games = it
                    )
                }
            }
        }

        return binding.root
    }

    override fun onGameClick(game: Game) {
        val action = FollowingFragmentDirections.actionFollowingFragmentToGameDetailsFragment(game.id)
        findNavController().navigate(action)
    }

    override fun onUnfollowButtonClick(tag: Tag) {
        followingViewModel.unfollowTag(tag)
    }
}