package com.sidharth.geemu.presentation.creator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.sidharth.geemu.databinding.FragmentCreatorDetailsBinding
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.presentation.creator.callback.OnGameClickCallback
import com.sidharth.geemu.presentation.creator.viewmodel.CreatorViewModel
import com.sidharth.geemu.presentation.creator.adapter.GamesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreatorDetailsFragment : Fragment(), OnGameClickCallback {

    private val creatorViewModel: CreatorViewModel by viewModels()
    private val args: CreatorDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCreatorDetailsBinding.inflate(inflater)

        creatorViewModel.fetchData(args.id)
        creatorViewModel.creatorDetails.observe(viewLifecycleOwner) {
            binding.apply {

            }
        }

        creatorViewModel.games.observe(viewLifecycleOwner) {
            binding.rvGames.layoutManager = LinearLayoutManager(
                binding.root.context, HORIZONTAL, false
            )
            LinearSnapHelper().attachToRecyclerView(binding.rvGames)
            binding.rvGames.adapter = GamesAdapter(
                games = it,
                onGameClickCallback = this
            )
        }

        return binding.root
    }

    override fun onGameClick(game: Game) {

    }

}