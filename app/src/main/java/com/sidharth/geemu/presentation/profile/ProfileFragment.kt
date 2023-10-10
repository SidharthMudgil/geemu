package com.sidharth.geemu.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.sidharth.geemu.databinding.FragmentProfileBinding
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.presentation.profile.adapter.ProfileScreenAdapter
import com.sidharth.geemu.presentation.profile.callback.OnGameClickCallback
import com.sidharth.geemu.presentation.viewmodel.UserDataViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment(), OnGameClickCallback {

    private val userDataViewModel: UserDataViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentProfileBinding.inflate(inflater)

        binding.rvProfile.layoutManager = LinearLayoutManager(
            requireContext(), VERTICAL, false
        )
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userDataViewModel.collections.collect { collections ->
                    if (collections.isEmpty()) {
                        onNoData(binding)
                    } else {
                        onData(binding)
                    }

                    binding.rvProfile.adapter = ProfileScreenAdapter(
                        collections = collections,
                        tags = userDataViewModel.following.value,
                        onGameClickCallback = this@ProfileFragment,
                    )
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userDataViewModel.following.collect { tags ->
                    binding.rvProfile.adapter = ProfileScreenAdapter(
                        collections = userDataViewModel.collections.value,
                        tags = tags,
                        onGameClickCallback = this@ProfileFragment,
                    )
                }
            }
        }

        return binding.root
    }

    private fun onNoData(binding: FragmentProfileBinding) {
        binding.apply {
            flowNoData.visibility = VISIBLE
            rvProfile.visibility = GONE
        }
    }

    private fun onData(binding: FragmentProfileBinding) {
        binding.apply {
            flowNoData.visibility = GONE
            rvProfile.visibility = VISIBLE
        }
    }

    override fun onGameClick(game: Game) {
        val action = ProfileFragmentDirections.actionProfileFragmentToGameDetailsFragment(game)
        findNavController().navigate(action)
    }

    override fun onGameRemove(game: Game) {
        userDataViewModel.removeGameFromCollection(game)
    }

    override fun onGameMove(game: Game, collection: Int) {
        userDataViewModel.moveGameToCollection(game, collection)
    }
}