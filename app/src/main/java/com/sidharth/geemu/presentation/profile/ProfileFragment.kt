package com.sidharth.geemu.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.sidharth.geemu.R
import com.sidharth.geemu.databinding.FragmentProfileBinding
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.presentation.profile.adapter.CollectionsAdapter
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
                    val genreCountMap = collections
                        .flatMap { it.games }
                        .flatMap { it.genres.split(", ") }
                        .groupingBy { it }
                        .eachCount()
                        .toList().sortedByDescending { it.second }

                    val topGenres = genreCountMap.take(4)
                    val other = genreCountMap.drop(4).sumOf { it.second }
                    val data = if (other > 0) {
                        (topGenres + Pair("Others", other)).toMap()
                    } else topGenres.toMap()

                    AAChartModel()
                        .chartType(AAChartType.Pie)
                        .backgroundColor(R.color.grey900)
                        .series(
                            arrayOf(
                                AASeriesElement().name("Game Count").data(
                                    data.map { arrayOf(it.key, it.value) }.toTypedArray()
                                )
                            )
                        ).apply {
                            binding.chartGenresDistribution.aa_drawChartWithChartModel(this)
                        }

                    AAChartModel()
                        .chartType(AAChartType.Bar)
                        .backgroundColor(R.color.grey900)
                        .categories(collections.map { it.name }.toTypedArray())
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
                    val popularTags =
                        tags.sortedByDescending { it.count }.take(10).sortedBy { it.name }
                    AAChartModel()
                        .chartType(AAChartType.Column)
                        .backgroundColor(R.color.grey900)
                        .categories(popularTags.map { it.name }.toTypedArray())
                        .series(
                            arrayOf(
                                AASeriesElement().name("Game Count").data(
                                    popularTags.map {
                                        arrayOf(it.name, it.count)
                                    }.toTypedArray()
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