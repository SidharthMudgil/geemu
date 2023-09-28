package com.sidharth.geemu.presentation.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.sidharth.geemu.core.enum.GameFilterType
import com.sidharth.geemu.databinding.FragmentGameDetailsBinding
import com.sidharth.geemu.domain.model.Creator
import com.sidharth.geemu.presentation.game.adapter.GameDetailsAdapter
import com.sidharth.geemu.presentation.game.callback.OnCreatorClickCallback
import com.sidharth.geemu.presentation.game.callback.OnItemClickCallback
import com.sidharth.geemu.presentation.game.callback.OnMediaClickCallback
import com.sidharth.geemu.presentation.game.viewmodel.GameDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
        binding.rvGameDetails.layoutManager = LinearLayoutManager(
            requireContext(), VERTICAL, false
        )
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                gameDetailsViewModel.gameDetails.collect {
                    binding.rvGameDetails.adapter = GameDetailsAdapter(
                        gameDetails = it,
                        onMediaClickCallback = this@GameDetailsFragment,
                        onItemClickCallback = this@GameDetailsFragment,
                        onCreatorClickCallback = this@GameDetailsFragment,
                    )
                }
            }
        }
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnShare.setOnClickListener {
            Toast.makeText(requireContext(), "Shared", Toast.LENGTH_LONG).show()
        }

        binding.btnSave.setOnClickListener {

        }

        return binding.root
    }

    override fun onCreatorClick(creator: Creator) {
        val action =
            GameDetailsFragmentDirections.actionGameDetailsFragmentToCreatorDetailsFragment(creator.id)
        findNavController().navigate(action)
    }

    override fun onItemClick(id: Int, name: String, type: GameFilterType) {
        val action = GameDetailsFragmentDirections.actionGameDetailsFragmentToGamesFragment(
            id = id,
            name = name,
            type = type,
        )
        findNavController().navigate(action)
    }

    override fun onVideoClick(preview: String, low: String, high: String) {
        val action = GameDetailsFragmentDirections.actionGameDetailsFragmentToVideoFragment(
            preview = preview,
            low = low,
            high = high,
        )
        findNavController().navigate(action)
    }

}