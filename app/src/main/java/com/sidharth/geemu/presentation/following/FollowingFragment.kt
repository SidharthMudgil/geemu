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
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.sidharth.geemu.R
import com.sidharth.geemu.databinding.FragmentFollowingBinding
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.domain.model.Tag
import com.sidharth.geemu.presentation.following.adapter.GamesPagerAdapter
import com.sidharth.geemu.presentation.following.callback.OnGameClickCallback
import com.sidharth.geemu.presentation.viewmodel.UserDataViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FollowingFragment : Fragment(), OnGameClickCallback {

    private val userDataViewModel: UserDataViewModel by activityViewModels()
    private lateinit var binding: FragmentFollowingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowingBinding.inflate(inflater)

        setupFollowingChips()
        setupRecyclerView()
        observeFollowingData()
        observeSavedGames()
        binding.loading.playAnimation()

        return binding.root
    }

    private fun setupFollowingChips() {
        binding.cgFollowing.setOnCheckedStateChangeListener { _, checkedIds ->
            if (checkedIds.isEmpty()) {
                filterList("", true)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvFollowing.layoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
        MaterialDividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL).apply {
            isLastItemDecorated = false
            dividerColor = requireContext().getColor(R.color.grey700)
            dividerInsetStart = resources.getDimension(R.dimen.white_space).toInt()
            dividerInsetEnd = resources.getDimension(R.dimen.white_space).toInt()
            binding.rvFollowing.addItemDecoration(this)
        }
    }

    private fun observeFollowingData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userDataViewModel.following.collect {
                    if (it.isNotEmpty()) {
                        onData()
                    } else {
                        onNoData()
                    }
                    createAddChips(it)
                }
            }
        }
    }

    private fun observeSavedGames() {
        val adapter = GamesPagerAdapter(
            onGameClickCallback = this@FollowingFragment
        )
        binding.rvFollowing.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userDataViewModel.filteredGames.collect {
                    adapter.submitData(it)
                }
            }
        }
    }

    private fun createAddChips(tags: List<Tag>) {
        binding.cgFollowing.removeAllViews()
        tags.forEach { tag ->
            val chip = Chip(requireContext())
            chip.text = tag.name
            chip.isCheckable = true
            binding.cgFollowing.addView(chip)
            setupChipClickListeners(chip, tag)
        }
    }

    private fun setupChipClickListeners(chip: Chip, tag: Tag) {
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

    private fun onData() {
        binding.loading.visibility = GONE
        binding.tvNoFollowing.visibility = GONE
        binding.scrollView.visibility = VISIBLE
        binding.rvFollowing.visibility = VISIBLE
    }

    private fun onNoData() {
        binding.loading.visibility = VISIBLE
        binding.tvNoFollowing.visibility = VISIBLE
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