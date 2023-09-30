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
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
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
                userDataViewModel.collections.collect { collections ->
                    binding.rvCollections.adapter = CollectionsAdapter(
                        collections = collections,
                        onGameClickCallback = this@ProfileFragment
                    )
                    val genreCountMap = collections.associate {collection->
                        collection.name to collection.games
                            .flatMap { it.genres.split(", ") }
                            .count()
                    }

                    AAChartModel()
                        .chartType(AAChartType.Pie)
                        .series(
                            arrayOf(
                                AASeriesElement().name("Game Count").data(
                                    genreCountMap.map { arrayOf(it.key, it.value) }.toTypedArray()
                                )
                            )
                        ).apply {
                            binding.chartGenresDistribution.aa_drawChartWithChartModel(this)
                        }

                    AAChartModel()
                        .chartType(AAChartType.Bar)
                        .series(
                            arrayOf(
                                AASeriesElement().name("Game Count").data(
                                    collections.map { arrayOf(it.name, it.games.size) }
                                        .toTypedArray()
                                )
                            )
                        ).apply {
                            binding.chartCollectionOverview.aa_drawChartWithChartModel(this)
                        }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userDataViewModel.following.collect { tags ->
                    AAChartModel()
                        .chartType(AAChartType.Bubble)
                        .series(
                            arrayOf(
                                AASeriesElement().name("Game Count").data(
                                    tags.map { it.name }.toTypedArray()
                                )
                            )
                        ).apply {
                            binding.chartPopularTags.aa_drawChartWithChartModel(this)
                        }
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