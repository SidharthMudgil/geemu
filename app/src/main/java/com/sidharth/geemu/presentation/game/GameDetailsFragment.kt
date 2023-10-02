package com.sidharth.geemu.presentation.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.sidharth.geemu.R
import com.sidharth.geemu.core.enums.GameFilterType
import com.sidharth.geemu.databinding.FragmentGameDetailsBinding
import com.sidharth.geemu.domain.model.Creator
import com.sidharth.geemu.domain.model.Tag
import com.sidharth.geemu.presentation.game.adapter.GameDetailsAdapter
import com.sidharth.geemu.presentation.game.callback.OnCreatorClickCallback
import com.sidharth.geemu.presentation.game.callback.OnItemClickCallback
import com.sidharth.geemu.presentation.game.callback.OnMediaClickCallback
import com.sidharth.geemu.presentation.game.viewmodel.GameDetailsViewModel
import com.sidharth.geemu.presentation.viewmodel.UserDataViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GameDetailsFragment
    : Fragment(), OnCreatorClickCallback, OnItemClickCallback, OnMediaClickCallback {

    private val gameDetailsViewModel: GameDetailsViewModel by viewModels()
    private val userDataViewModel: UserDataViewModel by activityViewModels()
    private val args: GameDetailsFragmentArgs by navArgs()
    private var isGameInCollection: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentGameDetailsBinding.inflate(inflater)

        gameDetailsViewModel.fetchGameDetails(args.game.id)
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
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userDataViewModel.games.collect { it ->
                    isGameInCollection = it.contains(args.game).also {
                        val drawable = when (it) {
                            true -> R.drawable.ic_saved
                            else -> R.drawable.ic_save
                        }
                        binding.btnSave.setImageDrawable(
                            ResourcesCompat.getDrawable(
                                resources,
                                drawable,
                                null
                            )
                        )
                    }
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
            if (isGameInCollection) {
                userDataViewModel.removeGameFromCollection(args.game)
            } else {
                userDataViewModel.addGameToCollection(args.game, 0)
            }
        }

        return binding.root
    }

    override fun onCreatorClick(creator: Creator) {
        val action =
            GameDetailsFragmentDirections.actionGameDetailsFragmentToCreatorDetailsFragment(creator.id)
        findNavController().navigate(action)
    }

    override fun onItemClick(
        id: Int,
        name: String,
        type: GameFilterType,
        tag: Tag?
    ) {
        val action = GameDetailsFragmentDirections.actionGameDetailsFragmentToGamesFragment(
            query = id.toString(),
            name = name,
            type = type,
            tag = tag,
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