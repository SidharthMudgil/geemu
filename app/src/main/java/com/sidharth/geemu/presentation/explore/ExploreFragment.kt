package com.sidharth.geemu.presentation.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.sidharth.geemu.core.enum.GameFilterType
import com.sidharth.geemu.databinding.FragmentExploreBinding
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.domain.model.Genre
import com.sidharth.geemu.presentation.explore.adapter.ExplorePageAdapter
import com.sidharth.geemu.presentation.explore.callback.OnGameClickCallback
import com.sidharth.geemu.presentation.explore.callback.OnGenreClickCallback
import com.sidharth.geemu.presentation.explore.callback.OnSearchBarClickCallback
import com.sidharth.geemu.presentation.explore.viewmodel.ExploreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreFragment : Fragment(),
    OnSearchBarClickCallback, OnGenreClickCallback, OnGameClickCallback {

    private val exploreViewModel by viewModels<ExploreViewModel>()
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentExploreBinding.inflate(inflater)

        recyclerView = binding.rvExplore

        binding.rvExplore.layoutManager = LinearLayoutManager(
            requireContext(), VERTICAL, false
        )
        exploreViewModel.exploreData.observe(viewLifecycleOwner) {
            binding.rvExplore.adapter = ExplorePageAdapter(
                onSearchBarClickCallback = this,
                onGenreClickCallback = this,
                onGameClickCallback = this,
                genres = it.genres,
                upcoming = it.upcoming,
                bestOfYear = it.bestOfYear,
                bestOfAllTime = it.bestOfAllTime,
            )
        }

        return binding.root
    }

    override fun onGameClick(game: Game) {
        val action = ExploreFragmentDirections.actionExploreFragmentToGameDetailsFragment(
            game.id
        )
        findNavController().navigate(action)
    }

    override fun onGenreClick(genre: Genre) {
        val action = ExploreFragmentDirections.actionExploreFragmentToGamesFragment(
            id = genre.id,
            name = genre.name,
            type = GameFilterType.GENRES,
        )
        findNavController().navigate(action)
    }

    override fun onSearchBarClick() {
        val action = ExploreFragmentDirections.actionExploreFragmentToSearchFragment()
        findNavController().navigate(action)
    }
}