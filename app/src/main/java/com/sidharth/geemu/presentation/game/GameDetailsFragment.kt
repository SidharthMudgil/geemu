package com.sidharth.geemu.presentation.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.sidharth.geemu.databinding.FragmentGameDetailsBinding
import com.sidharth.geemu.domain.model.Creator
import com.sidharth.geemu.presentation.game.callback.OnCreatorClickCallback
import com.sidharth.geemu.presentation.game.callback.OnItemClickCallback
import com.sidharth.geemu.presentation.game.callback.OnMediaClickCallback
import com.sidharth.geemu.presentation.game.viewmodel.GameDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameDetailsFragment
    : Fragment(), OnCreatorClickCallback, OnItemClickCallback, OnMediaClickCallback {

    private val gameDetailsViewModel: GameDetailsViewModel by viewModels()
    private val args: GameDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentGameDetailsBinding.inflate(inflater)

        gameDetailsViewModel.fetchGameDetails(args.id)
        gameDetailsViewModel.gameDetails.observe(viewLifecycleOwner) {

        }

        return binding.root
    }

    override fun onCreatorClick(creator: Creator) {
    }

    override fun onItemClick(id: Int) {
    }

    override fun onMediaClick(url: String, isVideo: Boolean) {
    }

}