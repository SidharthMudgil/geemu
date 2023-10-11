package com.sidharth.geemu.presentation.explore

import android.annotation.SuppressLint
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
import com.sidharth.geemu.core.enums.GameFilterType
import com.sidharth.geemu.databinding.FragmentExploreBinding
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.domain.model.Genre
import com.sidharth.geemu.presentation.explore.adapter.ExplorePageAdapter
import com.sidharth.geemu.presentation.explore.callback.OnGameClickCallback
import com.sidharth.geemu.presentation.explore.callback.OnGenreClickCallback
import com.sidharth.geemu.presentation.explore.callback.OnSearchButtonClickCallback
import com.sidharth.geemu.presentation.explore.viewmodel.ExploreViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ExploreFragment : Fragment(),
    OnSearchButtonClickCallback, OnGenreClickCallback, OnGameClickCallback {

    private lateinit var binding: FragmentExploreBinding
    private val exploreViewModel by viewModels<ExploreViewModel>()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExploreBinding.inflate(inflater)

        setupRecyclerView()
        observeExploreData()

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.rvExplore.layoutManager = LinearLayoutManager(
            requireContext(), VERTICAL, false
        )
    }

    private fun observeExploreData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                exploreViewModel.exploreData.collect {
                    binding.rvExplore.adapter = ExplorePageAdapter(
                        onSearchButtonClickCallback = this@ExploreFragment,
                        onGenreClickCallback = this@ExploreFragment,
                        onGameClickCallback = this@ExploreFragment,
                        genres = it.genres,
                        upcoming = it.upcoming,
                        bestOfYear = it.bestOfYear,
                        bestOfAllTime = it.bestOfAllTime,
                    )
                }
            }
        }
    }

    override fun onGameClick(game: Game) {
        val action = ExploreFragmentDirections.actionExploreFragmentToGameDetailsFragment(game)
        findNavController().navigate(action)
    }

    override fun onGenreClick(genre: Genre) {
        val action = ExploreFragmentDirections.actionExploreFragmentToGamesFragment(
            query = genre.id.toString(),
            name = genre.name,
            type = GameFilterType.GENRES,
        )
        findNavController().navigate(action)
    }

    override fun onSearchButtonClick(query: String) {
        val action = ExploreFragmentDirections.actionExploreFragmentToGamesFragment(
            query = query,
            type = GameFilterType.SEARCH,
        )
        findNavController().navigate(action)
    }
}