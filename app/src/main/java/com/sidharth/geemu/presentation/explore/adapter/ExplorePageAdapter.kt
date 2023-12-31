package com.sidharth.geemu.presentation.explore.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.sidharth.geemu.R
import com.sidharth.geemu.databinding.ItemSectionItemsBinding
import com.sidharth.geemu.databinding.ItemSectionSearchBinding
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.domain.model.Genre
import com.sidharth.geemu.presentation.explore.callback.OnGameClickCallback
import com.sidharth.geemu.presentation.explore.callback.OnGenreClickCallback
import com.sidharth.geemu.presentation.explore.callback.OnSearchButtonClickCallback

class ExplorePageAdapter(
    private val onSearchButtonClickCallback: OnSearchButtonClickCallback,
    private val onGenreClickCallback: OnGenreClickCallback,
    private val onGameClickCallback: OnGameClickCallback,
    private val genres: List<Genre>,
    private val upcoming: List<Game>,
    private val bestOfYear: List<Game>,
    private val bestOfAllTime: List<Game>,
) : Adapter<ViewHolder>() {
    private enum class ExploreSection {
        SEARCH,
        GENRES,
        GAMES_TYPE1,
        GAMES_TYPE2,
        GAMES_TYPE3,
    }

    inner class SearchSectionViewHolder(
        private val binding: ItemSectionSearchBinding
    ) : ViewHolder(binding.root) {
        fun bind() {
            binding.apply {
                inputSearch.setAnimationDelay(200)
                inputSearch.setInterpolator(AccelerateInterpolator())
                inputSearch.startTypewriterAnimation(textList)
                btnSearch.setOnClickListener { search() }
                inputSearch.setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        search()
                        true
                    } else false
                }
            }
        }

        fun search() {
            if (binding.inputSearch.text?.isNotBlank() == true) {
                onSearchButtonClickCallback.onSearchButtonClick(binding.inputSearch.text.toString())
            }
        }

        fun stopTypewriterAnimation() {
            binding.inputSearch.stopTypewriterAnimation()
        }
    }

    inner class ItemsSectionViewHolder(
        private val type: ItemsAdapter.CardType,
        private val binding: ItemSectionItemsBinding
    ) : ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind() {
            binding.apply {
                when (type) {
                    ItemsAdapter.CardType.GENRE -> {
                        tvLabel.text = "Genres"
                        rvItems.adapter = ItemsAdapter(
                            type = type,
                            items = genres,
                            onItemClickCallback = onGenreClickCallback
                        )
                    }

                    ItemsAdapter.CardType.GAME_TYPE1 -> {
                        tvLabel.text = "Upcoming"

                        if (rvItems.onFlingListener == null) {
                            LinearSnapHelper().attachToRecyclerView(rvItems)
                        }
                        rvItems.adapter = ItemsAdapter(
                            type = type,
                            items = upcoming,
                            onItemClickCallback = onGameClickCallback,
                        )
                    }

                    ItemsAdapter.CardType.GAME_TYPE2 -> {
                        tvLabel.text = "Best of the Year"

                        rvItems.adapter =  ItemsAdapter(
                            type = type,
                            items = bestOfYear,
                            onItemClickCallback = onGameClickCallback,
                        )
                    }

                    ItemsAdapter.CardType.GAME_TYPE3 -> {
                        tvLabel.text = "Best of All Time"

                        MaterialDividerItemDecoration(
                            binding.root.context,
                            LinearLayoutManager.VERTICAL
                        ).apply {
                            isLastItemDecorated = false
                            dividerColor = binding.root.context.getColor(R.color.grey700)
                            dividerInsetStart =
                                binding.root.context.resources.getDimension(R.dimen.white_space)
                                    .toInt()
                            dividerInsetEnd =
                                binding.root.context.resources.getDimension(R.dimen.white_space)
                                    .toInt()
                            binding.rvItems.addItemDecoration(this)
                        }
                        rvItems.adapter =   ItemsAdapter(
                            type = type,
                            items = bestOfAllTime,
                            onItemClickCallback = onGameClickCallback,
                        )

                    }
                }
                rvItems.layoutManager = when (type) {
                    ItemsAdapter.CardType.GAME_TYPE3 -> LinearLayoutManager(
                        binding.root.context, VERTICAL, false
                    )

                    else -> LinearLayoutManager(
                        binding.root.context, HORIZONTAL, false
                    )
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> ExploreSection.SEARCH.ordinal
            1 -> ExploreSection.GENRES.ordinal
            2 -> ExploreSection.GAMES_TYPE1.ordinal
            3 -> ExploreSection.GAMES_TYPE2.ordinal
            4 -> ExploreSection.GAMES_TYPE3.ordinal
            else -> throw IllegalStateException("Invalid ViewType")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            ExploreSection.SEARCH.ordinal -> {
                SearchSectionViewHolder(
                    ItemSectionSearchBinding.inflate(
                        inflater, parent, false
                    )
                )
            }

            ExploreSection.GENRES.ordinal -> {
                ItemsSectionViewHolder(
                    type = ItemsAdapter.CardType.GENRE,
                    binding = ItemSectionItemsBinding.inflate(
                        inflater, parent, false
                    )
                )
            }

            ExploreSection.GAMES_TYPE1.ordinal -> {
                ItemsSectionViewHolder(
                    type = ItemsAdapter.CardType.GAME_TYPE1,
                    binding = ItemSectionItemsBinding.inflate(
                        inflater, parent, false
                    )
                )
            }

            ExploreSection.GAMES_TYPE2.ordinal -> {
                ItemsSectionViewHolder(
                    type = ItemsAdapter.CardType.GAME_TYPE2,
                    binding = ItemSectionItemsBinding.inflate(
                        inflater, parent, false
                    )
                )
            }

            ExploreSection.GAMES_TYPE3.ordinal -> {
                ItemsSectionViewHolder(
                    type = ItemsAdapter.CardType.GAME_TYPE3,
                    binding = ItemSectionItemsBinding.inflate(
                        inflater, parent, false
                    )
                )
            }

            else -> throw IllegalStateException("Invalid ViewType")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (position) {
            0 -> ((holder) as SearchSectionViewHolder).bind()
            1 -> (holder as ItemsSectionViewHolder).bind()
            2 -> (holder as ItemsSectionViewHolder).bind()
            3 -> (holder as ItemsSectionViewHolder).bind()
            4 -> (holder as ItemsSectionViewHolder).bind()
        }
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onViewRecycled(holder: ViewHolder) {
        if (holder is SearchSectionViewHolder) {
            holder.stopTypewriterAnimation()
        }
        super.onViewRecycled(holder)
    }

    companion object {
        private val textList = listOf(
            "Grand Theft Auto V",
            "Cyberpunk 2077",
            "Red Dead Redemption 2",
            "God of War Ragnarok",
            "The Legend of Zelda",
            "Resident Evil Village",
        )
    }
}