package com.sidharth.geemu.presentation.profile.adapter

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.sidharth.geemu.R
import com.sidharth.geemu.databinding.ItemSectionChartBinding
import com.sidharth.geemu.databinding.ItemSectionCollectionBinding
import com.sidharth.geemu.domain.model.Collection
import com.sidharth.geemu.domain.model.Tag
import com.sidharth.geemu.presentation.profile.callback.OnGameClickCallback

class ProfileScreenAdapter(
    private val collections: List<Collection>,
    private val tags: List<Tag>,
    private val onGameClickCallback: OnGameClickCallback,
) : Adapter<ViewHolder>() {
    enum class ProfileSection {
        COLLECTION, CHART
    }

    enum class ChartType {
        GENRES, COLLECTION, TAGS
    }

    inner class CollectionsViewHolder(
        private val binding: ItemSectionCollectionBinding
    ) : ViewHolder(binding.root) {
        fun bind() {
            binding.rvCollection.layoutManager = LinearLayoutManager(
                binding.root.context, RecyclerView.VERTICAL, false
            )
            binding.rvCollection.adapter = CollectionsAdapter(
                collections = collections,
                onGameClickCallback = onGameClickCallback
            )
        }
    }

    inner class ChartViewHolder(
        private val binding: ItemSectionChartBinding
    ) : ViewHolder(binding.root) {
        fun bind(type: ChartType) {
            when (type) {
                ChartType.GENRES -> {
                    generateGenresDistributionChart().apply {
                        binding.tvLabel.text =
                            binding.root.context.getString(R.string.genres_distribution)
                        binding.chart.aa_drawChartWithChartModel(this)
                    }
                }

                ChartType.COLLECTION -> {
                    binding.tvLabel.text =
                        binding.root.context.getString(R.string.collection_overview)
                    generateCollectionOverviewChart().apply {
                        binding.chart.aa_drawChartWithChartModel(this)
                    }
                }

                ChartType.TAGS -> {
                    if (tags.isNotEmpty()) {
                        binding.tvLabel.text = binding.root.context.getString(R.string.popular_tags)
                        generatePopularTagsChart().apply {
                            binding.chart.aa_drawChartWithChartModel(this)
                        }
                    } else {
                        binding.tvLabel.visibility = GONE
                        binding.chart.visibility = GONE
                    }
                }
            }
        }

        private fun generateGenresDistributionChart(): AAChartModel {
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

            return AAChartModel()
                .chartType(AAChartType.Pie)
                .backgroundColor(R.color.grey800)
                .series(
                    arrayOf(
                        AASeriesElement().name("Game Count").data(
                            data.map { arrayOf(it.key, it.value) }.toTypedArray()
                        )
                    )
                )
        }

        private fun generateCollectionOverviewChart(): AAChartModel {
            return AAChartModel()
                .chartType(AAChartType.Bar)
                .backgroundColor(R.color.grey800)
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

        private fun generatePopularTagsChart(): AAChartModel {
            val popular = tags.sortedByDescending { it.count }.take(10).sortedBy { it.name }

            return AAChartModel()
                .chartType(AAChartType.Column)
                .backgroundColor(R.color.grey800)
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
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> ProfileSection.COLLECTION.ordinal
            1, 2, 3 -> ProfileSection.CHART.ordinal
            else -> throw IllegalStateException("Invalid Type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            ProfileSection.COLLECTION.ordinal -> CollectionsViewHolder(
                ItemSectionCollectionBinding.inflate(
                    inflater, parent, false
                )
            )

            ProfileSection.CHART.ordinal -> ChartViewHolder(
                ItemSectionChartBinding.inflate(
                    inflater, parent, false
                )
            )

            else -> throw IllegalStateException("Invalid Type")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (position) {
            0 -> (holder as CollectionsViewHolder).bind()
            1 -> (holder as ChartViewHolder).bind(ChartType.GENRES)
            2 -> (holder as ChartViewHolder).bind(ChartType.COLLECTION)
            3 -> (holder as ChartViewHolder).bind(ChartType.TAGS)
        }
    }

    override fun getItemCount(): Int {
        return 4
    }
}