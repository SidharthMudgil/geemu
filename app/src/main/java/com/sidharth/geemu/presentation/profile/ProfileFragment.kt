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
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.sidharth.geemu.R
import com.sidharth.geemu.databinding.FragmentProfileBinding
import com.sidharth.geemu.domain.model.Collection
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.domain.model.Tag
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
                    if (collections.isEmpty()) {
                        onNoData(binding)
                    } else {
                        onData(binding)
                    }

                    binding.rvCollections.adapter = CollectionsAdapter(
                        collections = collections,
                        onGameClickCallback = this@ProfileFragment
                    )

                    generateGenresDistributionChart(collections).apply {
                        binding.chartGenresDistribution.aa_drawChartWithChartModel(this)
                    }

                    generateCollectionOverviewChart(collections).apply {
                        binding.chartCollectionOverview.aa_drawChartWithChartModel(this)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userDataViewModel.following.collect { tags ->
                    generatePopularTagsChart(tags).apply {
                        binding.chartPopularTags.aa_drawChartWithChartModel(this)
                    }
                }
            }
        }

        return binding.root
    }

    private fun getGenresDistributionMap(collections: List<Collection>): Map<String, Int> {
        val genreCountMap = collections
            .flatMap { it.games }
            .flatMap { it.genres.split(", ") }
            .groupingBy { it }
            .eachCount()
            .toList().sortedByDescending { it.second }

        val topGenres = genreCountMap.take(4)
        val other = genreCountMap.drop(4).sumOf { it.second }
        return if (other > 0) {
            (topGenres + Pair("Others", other)).toMap()
        } else topGenres.toMap()
    }

    private fun generateGenresDistributionChart(collections: List<Collection>): AAChartModel {
        val data = getGenresDistributionMap(collections)

        return AAChartModel()
            .chartType(AAChartType.Pie)
            .backgroundColor(R.color.grey900)
            .series(
                arrayOf(
                    AASeriesElement().name("Game Count").data(
                        data.map { arrayOf(it.key, it.value) }.toTypedArray()
                    )
                )
            )
    }

    private fun generateCollectionOverviewChart(collections: List<Collection>): AAChartModel {
        return AAChartModel()
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
            )
    }

    private fun generatePopularTagsChart(tags: List<Tag>): AAChartModel {
        val popular = tags.sortedByDescending { it.count }.take(10).sortedBy { it.name }

        return AAChartModel()
            .chartType(AAChartType.Column)
            .backgroundColor(R.color.grey900)
            .categories(popular.map { it.name }.toTypedArray())
            .series(
                arrayOf(
                    AASeriesElement().name("Game Count").data(
                        popular.map {
                            arrayOf(it.name, it.count)
                        }.toTypedArray()
                    )
                )
            )
    }

    private fun onNoData(binding: FragmentProfileBinding) {
        binding.apply {
            loading.visibility = VISIBLE
            rvCollections.visibility = GONE
            tvPopularTags.visibility = GONE
            chartPopularTags.visibility = GONE
            tvCollectionOverview.visibility = GONE
            chartCollectionOverview.visibility = GONE
            tvGenresDistribution.visibility = GONE
            chartGenresDistribution.visibility = GONE
        }
    }

    private fun onData(binding: FragmentProfileBinding) {
        binding.apply {
            loading.visibility = GONE
            rvCollections.visibility = VISIBLE
            tvPopularTags.visibility = VISIBLE
            chartPopularTags.visibility = VISIBLE
            tvCollectionOverview.visibility = VISIBLE
            chartCollectionOverview.visibility = VISIBLE
            tvGenresDistribution.visibility = VISIBLE
            chartGenresDistribution.visibility = VISIBLE
        }
    }

    override fun onGameClick(game: Game) {
        val action = ProfileFragmentDirections.actionProfileFragmentToGameDetailsFragment(game)
        findNavController().navigate(action)
    }
}