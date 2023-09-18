package com.sidharth.geemu.presentation.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.sidharth.geemu.databinding.FragmentFollowingBinding
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.domain.model.Tag
import com.sidharth.geemu.presentation.following.adapter.FollowingAdapter
import com.sidharth.geemu.presentation.following.callback.OnGameClickCallback
import com.sidharth.geemu.presentation.following.callback.OnUnfollowButtonClickCallback
import com.sidharth.geemu.presentation.following.viewmodel.FollowingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowingFragment: Fragment(), OnGameClickCallback, OnUnfollowButtonClickCallback {

    private val followingViewModel by viewModels<FollowingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFollowingBinding.inflate(inflater)

        binding.rvFollowing.layoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
        followingViewModel.following.observe(viewLifecycleOwner) {
            binding.rvFollowing.adapter = FollowingAdapter(it, this, this)
        }

        return binding.root
    }

    override fun onGameClick(game: Game) {

    }

    override fun onUnfollowButtonClick(tag: Tag) {
        followingViewModel.unfollowTag(tag)
    }
}