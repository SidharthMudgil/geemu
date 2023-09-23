package com.sidharth.geemu.presentation.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.sidharth.geemu.databinding.FragmentGameDetailsBinding
import com.sidharth.geemu.domain.model.Creator
import com.sidharth.geemu.presentation.game.adapter.GameDetailsAdapter
import com.sidharth.geemu.presentation.game.adapter.ItemsAdapter
import com.sidharth.geemu.presentation.game.callback.OnActionButtonClickListener
import com.sidharth.geemu.presentation.game.callback.OnCreatorClickCallback
import com.sidharth.geemu.presentation.game.callback.OnItemClickCallback
import com.sidharth.geemu.presentation.game.callback.OnMediaClickCallback
import com.sidharth.geemu.presentation.game.viewmodel.GameDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameDetailsFragment
    : Fragment(), OnCreatorClickCallback, OnItemClickCallback, OnMediaClickCallback,
    OnActionButtonClickListener {

    private val gameDetailsViewModel: GameDetailsViewModel by viewModels()
    private val args: GameDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentGameDetailsBinding.inflate(inflater)

        gameDetailsViewModel.fetchGameDetails(args.id)
        binding.rvGameDetails.layoutManager = LinearLayoutManager(
            requireContext(), VERTICAL, false
        )
        gameDetailsViewModel.gameDetails.observe(viewLifecycleOwner) {
            binding.rvGameDetails.adapter = GameDetailsAdapter(
                gameDetails = it,
                onActionButtonClickListener = this,
                onMediaClickCallback = this,
                onItemClickCallback = this,
            )
        }
        return binding.root
    }

    override fun onBackButtonClick() {
    }

    override fun onShareButtonClick() {
    }

    override fun onSaveButtonClick() {
    }

    override fun onCreatorClick(creator: Creator) {
    }

    override fun onItemClick(id: Int, type: ItemsAdapter.CardType) {

    }

    override fun onImageClick(url: String) {
    }

    override fun onVideoClick(low: String, high: String) {
    }

}