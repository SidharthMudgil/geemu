package com.sidharth.geemu.presentation.game

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
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
import com.sidharth.geemu.core.constant.Constants
import com.sidharth.geemu.core.enums.GameFilterType
import com.sidharth.geemu.databinding.FragmentGameDetailsBinding
import com.sidharth.geemu.domain.model.Creator
import com.sidharth.geemu.domain.model.Game
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
    private lateinit var binding: FragmentGameDetailsBinding
    private var game: Game? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameDetailsBinding.inflate(inflater)

        game = args.game
        gameDetailsViewModel.fetchGameDetails(args.game?.id ?: args.id)
        binding.rvGameDetails.layoutManager = LinearLayoutManager(
            requireContext(), VERTICAL, false
        )
        binding.loading.playAnimation()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                gameDetailsViewModel.gameDetails.collect { it ->
                    if (it != Constants.EMPTY_GAME_DETAILS) {
                        binding.loading.visibility = GONE
                        binding.rvGameDetails.visibility = VISIBLE
                        binding.cvAction.visibility = VISIBLE

                        game = Game(
                            id = it.id,
                            name = it.name,
                            image = it.image,
                            genres = it.genres.joinToString(", ") { it.name }.take(2),
                            release = it.release,
                            rating = it.rating.toString(),
                        )
                    }
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
                userDataViewModel.collections.collect { it ->
                    isGameInCollection = it.flatMap { it.games }.contains(args.game).also {
                        val drawable = when (it) {
                            true -> R.drawable.ic_added
                            else -> R.drawable.ic_add
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
        binding.btnShare.setOnClickListener {
            Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                if (args.game != null || game != null) {
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "https://www.geemu.com/game/${args.game?.id ?: game?.id!!}"
                    )
                }
                startActivity(this)
            }
        }
        binding.btnSave.setOnClickListener {
            if (isGameInCollection) {
                if (args.game != null || game != null) {
                    userDataViewModel.removeGameFromCollection(args.game ?: game!!)
                }
            } else {
                showBottomSheet()
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findNavController().currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<Int>(ModalBottomSheet.KEY)
            ?.observe(viewLifecycleOwner) { collection ->
                if (args.game != null || game != null) {
                    userDataViewModel.addGameToCollection(args.game ?: game!!, collection)
                }
            }
    }

    private fun showBottomSheet() {
        val action = GameDetailsFragmentDirections.actionGameDetailsFragmentToModalBottomSheet()
        findNavController().navigate(action)
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