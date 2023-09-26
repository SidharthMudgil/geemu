package com.sidharth.geemu.presentation.game.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.google.android.material.chip.Chip
import com.sidharth.geemu.core.enum.GameFilterType
import com.sidharth.geemu.core.util.DateTimeUtils.toPrettyFormat
import com.sidharth.geemu.databinding.ItemSectionGameInfo1Binding
import com.sidharth.geemu.databinding.ItemSectionGameInfo2Binding
import com.sidharth.geemu.databinding.ItemSectionItemsBinding
import com.sidharth.geemu.domain.model.GameDetails
import com.sidharth.geemu.presentation.game.callback.OnActionButtonClickListener
import com.sidharth.geemu.presentation.game.callback.OnCreatorClickCallback
import com.sidharth.geemu.presentation.game.callback.OnItemClickCallback
import com.sidharth.geemu.presentation.game.callback.OnMediaClickCallback

class GameDetailsAdapter(
    private val gameDetails: GameDetails,
    private val onActionButtonClickListener: OnActionButtonClickListener,
    private val onMediaClickCallback: OnMediaClickCallback,
    private val onItemClickCallback: OnMediaClickCallback,
    private val onCreatorClickCallback: OnCreatorClickCallback,
) : Adapter<ViewHolder>() {

    enum class GameDetailsSection {
        GAME_INFO1, SCREENSHOTS, ADDITIONS, CREATORS, DEVELOPERS, PUBLISHERS, GAME_INFO2
    }

    inner class GameInfo1ViewHolder(
        private val binding: ItemSectionGameInfo1Binding
    ) : ViewHolder(binding.root) {
        fun bind() {
            binding.apply {
                ivImage.load(gameDetails.image)
                ivBackground.load(gameDetails.background)
                tvEsrb.text = gameDetails.esrbRating
                tvName.text = gameDetails.name
                tvName.isSelected = true
                tvGenres.text = gameDetails.genres.joinToString(", ") { it.name }
                tvRelease.text = gameDetails.release.toPrettyFormat()
                tvDescription.text = gameDetails.description
                rbRating.rating = gameDetails.rating.toFloat()
                gameDetails.platforms.forEach {
                    val chip = Chip(root.context)
                    chip.text = it.name
                    chip.isClickable = true
                    cgPlatforms.addView(chip)
                }
                btnBack.setOnClickListener {
                    onActionButtonClickListener.onBackButtonClick()
                }
                btnShare.setOnClickListener {
                    onActionButtonClickListener.onShareButtonClick()
                }
                btnSave.setOnClickListener {
                    onActionButtonClickListener.onSaveButtonClick()
                }
            }
        }
    }

    inner class GameInfo2ViewHolder(
        private val binding: ItemSectionGameInfo2Binding
    ) : ViewHolder(binding.root) {
        fun bind() {
            binding.apply {
                AAChartModel()
                    .chartType(AAChartType.Pie)
                    .series(
                        arrayOf(
                            AASeriesElement().name("Rating Count")
                                .data(
                                    gameDetails.ratings.map {
                                        arrayOf(it.title, it.count)
                                    }.toTypedArray()
                                )
                        )
                    ).apply {
                        chartRating.aa_drawChartWithChartModel(this)
                    }

                cgTags.isClickable = true
                gameDetails.tags.forEach { tag ->
                    val chip = Chip(root.context)
                    chip.text = tag.name
                    chip.isClickable = true
                    cgTags.addView(chip)
                    chip.setOnClickListener {
                        (onItemClickCallback as OnItemClickCallback).onItemClick(
                            id = tag.id,
                            name = tag.name,
                            type = GameFilterType.TAGS,
                        )
                    }
                }
            }
        }
    }

    inner class ItemSectionViewHolder(
        private val type: ItemsAdapter.CardType, private val binding: ItemSectionItemsBinding
    ) : ViewHolder(binding.root) {
        fun bind(items: List<Any>) {
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
            2 -> GameDetailsSection.ADDITIONS.ordinal
            3 -> GameDetailsSection.CREATORS.ordinal
            4 -> GameDetailsSection.DEVELOPERS.ordinal
            5 -> GameDetailsSection.PUBLISHERS.ordinal
            6 -> GameDetailsSection.GAME_INFO2.ordinal
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
            2 -> (holder as ItemSectionViewHolder).bind(gameDetails.additions)
            3 -> (holder as ItemSectionViewHolder).bind(gameDetails.creators)
            4 -> (holder as ItemSectionViewHolder).bind(gameDetails.developers)
            5 -> (holder as ItemSectionViewHolder).bind(gameDetails.publishers)
            6 -> (holder as GameInfo2ViewHolder).bind()
        }
    }

    override fun getItemCount(): Int {
        return 7
    }

}