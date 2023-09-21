package com.sidharth.geemu.presentation.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.sidharth.geemu.databinding.FragmentGamesBinding
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.presentation.games.adapter.GamesAdapter
import com.sidharth.geemu.presentation.games.callback.OnGameClickCallback
import com.sidharth.geemu.presentation.games.viewmodel.GamesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GamesFragment : Fragment(), OnGameClickCallback {

    private val gamesViewModel by viewModels<GamesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentGamesBinding.inflate(inflater)

        binding.rvGames.layoutManager = LinearLayoutManager(
            requireContext(), VERTICAL, false
        )
        gamesViewModel.games.observe(viewLifecycleOwner) {
            binding.rvGames.adapter = GamesAdapter(
                games = it,
                onGameClickCallback = this,
            )
        }
//        binding.tvTitle.text =
//        gamesViewModel.fetchGames()

        return binding.root
    }

    override fun onGameClick(game: Game) {
    }
}