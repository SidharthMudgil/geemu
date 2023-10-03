package com.sidharth.geemu.presentation.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
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
import androidx.recyclerview.widget.RecyclerView.VISIBLE
import com.sidharth.geemu.R
import com.sidharth.geemu.core.enums.GameFilterType
import com.sidharth.geemu.databinding.FragmentGamesBinding
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.presentation.games.adapter.GamesAdapter
import com.sidharth.geemu.presentation.games.callback.OnGameClickCallback
import com.sidharth.geemu.presentation.games.viewmodel.GamesViewModel
import com.sidharth.geemu.presentation.viewmodel.UserDataViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GamesFragment : Fragment(), OnGameClickCallback {

    private val gamesViewModel: GamesViewModel by viewModels()
    private val userDataViewModel: UserDataViewModel by activityViewModels()
    private val args: GamesFragmentArgs by navArgs()
    private var isTagFollowed: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentGamesBinding.inflate(inflater)

        gamesViewModel.fetchGames(
            query = args.query,
            filter = args.type,
        )
        binding.tvTitle.text = args.name
        binding.rvItems.layoutManager = LinearLayoutManager(
            requireContext(), VERTICAL, false
        )
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                gamesViewModel.games.collect {
                    binding.rvItems.adapter = GamesAdapter(
                        games = it,
                        onGameClickCallback = this@GamesFragment,
                    )
                }
            }
        }

        if (args.type == GameFilterType.TAGS){
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    userDataViewModel.following.collect { it ->
                        isTagFollowed = it.contains(args.tag).also {
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
            if (args.type == GameFilterType.TAGS) {
                binding.btnSave.visibility = VISIBLE
                binding.btnSave.setOnClickListener {
                    if (isTagFollowed) {
                        userDataViewModel.unfollowTag(args.tag!!)
                    } else {
                        userDataViewModel.followTag(args.tag!!)
                    }
                }
            }
        }

        return binding.root
    }

    override fun onGameClick(game: Game) {
        val action = GamesFragmentDirections.actionGamesFragmentToGameDetailsFragment(game)
        findNavController().navigate(action)
    }
}