package com.sidharth.geemu.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.sidharth.geemu.databinding.FragmentHomeBinding
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.domain.model.Genre
import com.sidharth.geemu.presentation.home.adapter.HomeAdapter
import com.sidharth.geemu.presentation.home.callback.OnGameClickCallback
import com.sidharth.geemu.presentation.home.callback.OnGenreClickCallback
import com.sidharth.geemu.presentation.home.callback.OnSearchBarClickCallback
import com.sidharth.geemu.presentation.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(),
    OnSearchBarClickCallback, OnGenreClickCallback, OnGameClickCallback {

    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHomeBinding.inflate(inflater)

        binding.rvHome.layoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
        homeViewModel.homeData.observe(viewLifecycleOwner) {
            binding.rvHome.adapter = HomeAdapter(
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
    }

    override fun onGenreClick(genre: Genre) {
    }

    override fun onClick() {
    }

}