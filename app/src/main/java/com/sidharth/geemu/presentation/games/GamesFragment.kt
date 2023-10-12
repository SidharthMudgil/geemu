package com.sidharth.geemu.presentation.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import androidx.recyclerview.widget.RecyclerView.VISIBLE
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.sidharth.geemu.R
import com.sidharth.geemu.core.enums.GameFilterType
import com.sidharth.geemu.databinding.FragmentGamesBinding
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.presentation.games.adapter.GamesPagerAdapter
import com.sidharth.geemu.presentation.games.callback.OnGameClickCallback
import com.sidharth.geemu.presentation.games.viewmodel.GamesViewModel
import com.sidharth.geemu.presentation.viewmodel.UserDataViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GamesFragment : Fragment(), OnGameClickCallback {

    private lateinit var binding: FragmentGamesBinding
    private val gamesViewModel: GamesViewModel by viewModels()
    private val userDataViewModel: UserDataViewModel by activityViewModels()
    private val args: GamesFragmentArgs by navArgs()
    private var isTagFollowed: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGamesBinding.inflate(inflater)

        fetchGames()
        setupRecyclerView()
        observeFilteredGames()
        observeFollowingTags()
        setupClickListeners()

        return binding.root
    }

    private fun fetchGames() {
        gamesViewModel.fetchGames(
            query = args.query,
            filter = args.type,
        )
    }

    private fun setupRecyclerView() {
        binding.tvTitle.text = args.name
        binding.rvItems.layoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
        MaterialDividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL).apply {
            isLastItemDecorated = false
            dividerColor = requireContext().getColor(R.color.grey700)
            dividerInsetStart = resources.getDimension(R.dimen.white_space).toInt()
            dividerInsetEnd = resources.getDimension(R.dimen.white_space).toInt()
            binding.rvItems.addItemDecoration(this)
        }
    }

    private fun observeFilteredGames() {
        val adapter = GamesPagerAdapter(
            onGameClickCallback = this@GamesFragment,
        )
        binding.rvItems.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                gamesViewModel.games.collect {
                    adapter.submitData(it)
                }
            }
        }
    }

    private fun observeFollowingTags() {
        if (args.type != GameFilterType.TAGS) {
            return
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userDataViewModel.following.collect {
                    val drawable = when (it.contains(args.tag)) {
                        true -> R.drawable.ic_added
                        else -> R.drawable.ic_add
                    }
                    binding.btnSave.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            resources, drawable, null
                        )
                    )
                }
            }
        }
    }

    private fun setupClickListeners() {
        if (args.type != GameFilterType.TAGS) {
            return
        }
        binding.btnSave.visibility = VISIBLE
        binding.btnSave.setOnClickListener {
            if (isTagFollowed) {
                userDataViewModel.unfollowTag(args.tag!!)
            } else {
                userDataViewModel.followTag(args.tag!!)
            }
        }
    }

    override fun onGameClick(game: Game) {
        val action = GamesFragmentDirections.actionGamesFragmentToGameDetailsFragment(game)
        findNavController().navigate(action)
    }
}