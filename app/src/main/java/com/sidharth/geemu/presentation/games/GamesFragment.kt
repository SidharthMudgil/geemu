package com.sidharth.geemu.presentation.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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

    private val gamesViewModel: GamesViewModel by viewModels()
    private val args: GamesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentGamesBinding.inflate(inflater)

        gamesViewModel.fetchGames(
            id = args.id.toString(),
            filter = args.type,
        )
        binding.tvTitle.text = args.name
        binding.rvItems.layoutManager = LinearLayoutManager(
            requireContext(), VERTICAL, false
        )
        gamesViewModel.games.observe(viewLifecycleOwner) {
            binding.rvItems.adapter = GamesAdapter(
                games = it,
                onGameClickCallback = this,
            )
        }

        return binding.root
    }

    override fun onGameClick(game: Game) {
        val action = GamesFragmentDirections.actionGamesFragmentToGameDetailsFragment(
            game.id
        )
        findNavController().navigate(action)
    }
}