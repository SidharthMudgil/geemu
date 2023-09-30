package com.sidharth.geemu.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.sidharth.geemu.databinding.FragmentProfileBinding
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.presentation.profile.adapter.CollectionsAdapter
import com.sidharth.geemu.presentation.profile.callback.OnGameClickCallback
import com.sidharth.geemu.presentation.viewmodel.UserDataViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment(), OnGameClickCallback {

    private val userDataViewModel: UserDataViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentProfileBinding.inflate(inflater)

        binding.rvCollections.layoutManager = LinearLayoutManager(
            requireContext(), VERTICAL, false
        )
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userDataViewModel.collections.collect {
                    binding.rvCollections.adapter = CollectionsAdapter(
                        collections = it,
                        onGameClickCallback = this@ProfileFragment
                    )
                }
            }
        }

        return binding.root
    }

    override fun onGameClick(game: Game) {
        val action = ProfileFragmentDirections.actionProfileFragmentToGameDetailsFragment(game)
        findNavController().navigate(action)
    }
}