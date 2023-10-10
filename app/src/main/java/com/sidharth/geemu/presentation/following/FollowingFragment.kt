package com.sidharth.geemu.presentation.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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
import com.sidharth.geemu.presentation.viewmodel.UserDataViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FollowingFragment : Fragment(), OnGameClickCallback {

    private val userDataViewModel: UserDataViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFollowingBinding.inflate(inflater)

        binding.cgFollowing.setOnCheckedStateChangeListener { _, checkedIds ->
            if (checkedIds.isEmpty()) {
                filterList("", true)
            }
        }
        binding.loading.playAnimation()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userDataViewModel.following.collect {
                    if (it.isNotEmpty()) {
                        onData(binding)
                    } else {
                        onNoData(binding)
                    }
                    createAddChips(it, binding)
                }
            }
        }
        binding.rvFollowing.layoutManager = LinearLayoutManager(
            requireContext(), VERTICAL, false
        )
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userDataViewModel.games.collect {
                    binding.rvFollowing.adapter = GamesAdapter(
                        onGameClickCallback = this@FollowingFragment,
                        games = it
                    )
                }
            }
        }

        return binding.root
    }

    private fun createAddChips(tags: List<Tag>, binding: FragmentFollowingBinding) {
        binding.cgFollowing.removeAllViews()
        tags.forEach { tag ->
            val chip = Chip(requireContext())
            chip.text = tag.name
            chip.isCheckable = true
            binding.cgFollowing.addView(chip)
            chip.setOnClickListener {
                chip.isCloseIconVisible = false
                filterList(tag.id.toString())
            }
            chip.setOnLongClickListener {
                chip.isCloseIconVisible = true
                true
            }
            chip.setOnCloseIconClickListener {
                binding.cgFollowing.removeView(chip)
                unfollow(tag)
            }
        }
    }

    private fun onData(binding: FragmentFollowingBinding) {
        binding.loading.visibility = GONE
        binding.tvNoFollowing.visibility = GONE
        binding.tvLabel.visibility = VISIBLE
        binding.scrollView.visibility = VISIBLE
        binding.rvFollowing.visibility = VISIBLE
    }

    private fun onNoData(binding: FragmentFollowingBinding) {
        binding.loading.visibility = VISIBLE
        binding.tvNoFollowing.visibility = VISIBLE
        binding.tvLabel.visibility = GONE
        binding.scrollView.visibility = GONE
        binding.rvFollowing.visibility = GONE
    }

    private fun filterList(id: String, fetchAll: Boolean = false) {
        userDataViewModel.fetchFilteredGames(id, fetchAll)
    }

    override fun onGameClick(game: Game) {
        val action = FollowingFragmentDirections.actionFollowingFragmentToGameDetailsFragment(game)
        findNavController().navigate(action)
    }

    private fun unfollow(tag: Tag) {
        userDataViewModel.unfollowTag(tag)
    }
}