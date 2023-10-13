package com.sidharth.geemu.presentation.game.adapter

import android.annotation.SuppressLint
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartSymbolStyleType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAStyle
import com.google.android.material.chip.Chip
import com.sidharth.geemu.R
import com.sidharth.geemu.core.constant.Constants
import com.sidharth.geemu.core.enums.GameFilterType
import com.sidharth.geemu.core.util.DateTimeUtils.toPrettyFormat
import com.sidharth.geemu.databinding.ItemSectionGameInfo1Binding
import com.sidharth.geemu.databinding.ItemSectionGameInfo2Binding
import com.sidharth.geemu.databinding.ItemSectionItemsBinding
import com.sidharth.geemu.domain.model.GameDetails
import com.sidharth.geemu.presentation.game.callback.OnCreatorClickCallback
import com.sidharth.geemu.presentation.game.callback.OnItemClickCallback
import com.sidharth.geemu.presentation.game.callback.OnMediaClickCallback

class GameDetailsAdapter(
    private val gameDetails: GameDetails,
    private val onMediaClickCallback: OnMediaClickCallback,
    private val onItemClickCallback: OnMediaClickCallback,
    private val onCreatorClickCallback: OnCreatorClickCallback,
) : Adapter<ViewHolder>() {

    private enum class GameDetailsSection {
        GAME_INFO1,
        SCREENSHOTS,
        GAME_INFO2,
        ADDITIONS,
        CREATORS,
        DEVELOPERS,
        PUBLISHERS,
    }

    inner class GameInfo1ViewHolder(
        private val binding: ItemSectionGameInfo1Binding
    ) : ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind() {
            binding.apply {
                ivImage.load(gameDetails.image)
                tvEsrb.text = gameDetails.esrbRating
                if (gameDetails.esrbRating.isBlank()) {
                    cvEsrb.visibility = GONE
                }
                tvName.text = gameDetails.name
                tvGenres.text = gameDetails.genres.joinToString(", ") { it.name }
                if (gameDetails.release.isNotBlank()) {
                    tvRelease.text = gameDetails.release.toPrettyFormat()
                } else {
                    tvRelease.visibility = GONE
                }
                tvDescription.text =
                    Html.fromHtml(gameDetails.description, Html.FROM_HTML_MODE_LEGACY)
                Log.d("description", gameDetails.description)
                tvRatings.text = "(${gameDetails.rating})"
                gameDetails.platforms.forEach {
                    val chip = Chip(root.context)
                    chip.text = it.name
                    chip.isClickable = true
                    cgPlatforms.addView(chip)
                }
            }
        }
    }

    inner class GameInfo2ViewHolder(
        private val binding: ItemSectionGameInfo2Binding
    ) : ViewHolder(binding.root) {
        fun bind() {
            binding.cgTags.isClickable = true
            gameDetails.tags.forEach { tag ->
                val chip = Chip(binding.root.context)
                chip.text = tag.name
                chip.isClickable = true
                binding.cgTags.addView(chip)
                chip.setOnClickListener {
                    (onItemClickCallback as OnItemClickCallback).onItemClick(
                        id = tag.id,
                        name = tag.name,
                        type = GameFilterType.TAGS,
                        tag = tag,
                    )
                }
            }

            if (gameDetails.tags.isEmpty()) {
                binding.hv.visibility = GONE
                binding.tvTags.visibility = GONE
            }

            if (gameDetails.ratings.isEmpty()) {
                binding.chartRating.visibility = GONE
                binding.tvRatingsChart.visibility = GONE
                return
            }

            AAChartModel()
                .chartType(AAChartType.Pie)
                .backgroundColor(R.color.grey800)
                .colorsTheme(Constants.colors)
                .axesTextColor("#EAEAEA")
                .markerSymbolStyle(AAChartSymbolStyleType.BorderBlank)
                .borderRadius(0)
                .dataLabelsStyle(AAStyle().color("#EAEAEA"))
                .legendEnabled(false)
                .series(
                    arrayOf(
                        AASeriesElement().name("Rating Count")
                            .data(
                                gameDetails.ratings.map {
                                    arrayOf(it.title, it.count)
                                }.toTypedArray()
                            ).borderWidth(0)
                    )
                ).apply {
                    binding.chartRating.aa_drawChartWithChartModel(this)
                }
        }
    }

    inner class ItemSectionViewHolder(
        private val type: ItemsAdapter.CardType, private val binding: ItemSectionItemsBinding
    ) : ViewHolder(binding.root) {
        fun bind(items: List<Any>) {
            if (items.isEmpty()) {
                binding.tvLabel.visibility = GONE
                binding.rvItems.visibility = GONE
                return
            }

            binding.apply {
                tvLabel.text = when (type) {
                    ItemsAdapter.CardType.SCREENSHOT -> "Screenshots"
                    ItemsAdapter.CardType.ADDITION -> "Additions"
                    ItemsAdapter.CardType.CREATOR -> "Creators"
                    ItemsAdapter.CardType.PUBLISHER -> "Publishers"
                    ItemsAdapter.CardType.DEVELOPER -> "Developers"
                }
                rvItems.layoutManager = LinearLayoutManager(
                    binding.root.context, HORIZONTAL, false
                )
                rvItems.adapter = when (type) {
                    ItemsAdapter.CardType.SCREENSHOT -> ItemsAdapter(
                        type = type,
                        items = items,
                        onItemClickCallback = onMediaClickCallback,
                    )

                    ItemsAdapter.CardType.ADDITION -> ItemsAdapter(
                        type = type,
                        items = items,
                    )

                    ItemsAdapter.CardType.CREATOR -> ItemsAdapter(
                        type = type,
                        items = items,
                        onItemClickCallback = onCreatorClickCallback,
                    )

                    else -> ItemsAdapter(
                        type = type,
                        items = items,
                        onItemClickCallback = onItemClickCallback,
                    )
                }
                if (rvItems.onFlingListener == null && type == ItemsAdapter.CardType.SCREENSHOT) {
                    LinearSnapHelper().attachToRecyclerView(rvItems)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> GameDetailsSection.GAME_INFO1.ordinal
            1 -> GameDetailsSection.SCREENSHOTS.ordinal
            2 -> GameDetailsSection.GAME_INFO2.ordinal
            3 -> GameDetailsSection.ADDITIONS.ordinal
            4 -> GameDetailsSection.CREATORS.ordinal
            5 -> GameDetailsSection.DEVELOPERS.ordinal
            6 -> GameDetailsSection.PUBLISHERS.ordinal
            else -> throw IllegalStateException("Invalid ViewType")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            GameDetailsSection.GAME_INFO1.ordinal -> {
                GameInfo1ViewHolder(
                    ItemSectionGameInfo1Binding.inflate(
                        inflater, parent, false
                    )
                )
            }

            GameDetailsSection.SCREENSHOTS.ordinal -> {
                ItemSectionViewHolder(
                    type = ItemsAdapter.CardType.SCREENSHOT,
                    binding = ItemSectionItemsBinding.inflate(
                        inflater, parent, false
                    )
                )
            }

            GameDetailsSection.ADDITIONS.ordinal -> {
                ItemSectionViewHolder(
                    type = ItemsAdapter.CardType.ADDITION,
                    binding = ItemSectionItemsBinding.inflate(
                        inflater, parent, false
                    ),
                )
            }

            GameDetailsSection.CREATORS.ordinal -> {
                ItemSectionViewHolder(
                    type = ItemsAdapter.CardType.CREATOR,
                    binding = ItemSectionItemsBinding.inflate(
                        inflater, parent, false
                    ),
                )
            }

            GameDetailsSection.DEVELOPERS.ordinal -> {
                ItemSectionViewHolder(
                    type = ItemsAdapter.CardType.DEVELOPER,
                    binding = ItemSectionItemsBinding.inflate(
                        inflater, parent, false
                    ),
                )
            }

            GameDetailsSection.PUBLISHERS.ordinal -> {
                ItemSectionViewHolder(
                    type = ItemsAdapter.CardType.PUBLISHER,
                    binding = ItemSectionItemsBinding.inflate(
                        inflater, parent, false
                    ),
                )
            }

            GameDetailsSection.GAME_INFO2.ordinal -> {
                GameInfo2ViewHolder(
                    ItemSectionGameInfo2Binding.inflate(
                        inflater, parent, false
                    )
                )
            }

            else -> throw IllegalStateException("Invalid ViewType")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (position) {
            0 -> (holder as GameInfo1ViewHolder).bind()
            1 -> (holder as ItemSectionViewHolder).bind(gameDetails.trailers.plus(gameDetails.screenshots))
            2 -> (holder as GameInfo2ViewHolder).bind()
            3 -> (holder as ItemSectionViewHolder).bind(gameDetails.additions)
            4 -> (holder as ItemSectionViewHolder).bind(gameDetails.creators)
            5 -> (holder as ItemSectionViewHolder).bind(gameDetails.developers)
            6 -> (holder as ItemSectionViewHolder).bind(gameDetails.publishers)
        }
    }

    override fun getItemCount(): Int {
        return 7
    }

}