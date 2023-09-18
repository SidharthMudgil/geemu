package com.sidharth.geemu.presentation.explore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.sidharth.geemu.databinding.ItemSectionGamesBinding
import com.sidharth.geemu.databinding.ItemSectionGenresBinding
import com.sidharth.geemu.databinding.ItemSectionSearchBinding
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.domain.model.Genre
import com.sidharth.geemu.presentation.explore.callback.OnGameClickCallback
import com.sidharth.geemu.presentation.explore.callback.OnGenreClickCallback
import com.sidharth.geemu.presentation.explore.callback.OnSearchBarClickCallback


class ExplorePageAdapter(
    private val onSearchBarClickCallback: OnSearchBarClickCallback,
    private val onGenreClickCallback: OnGenreClickCallback,
    private val onGameClickCallback: OnGameClickCallback,
    private val genres: List<Genre>,
    private val upcoming: List<Game>,
    private val bestOfYear: List<Game>,
    private val bestOfAllTime: List<Game>,
) : Adapter<ViewHolder>() {
    enum class ExploreSection {
        SEARCH, GENRES, GAMES_TYPE1, GAMES_TYPE2, GAMES_TYPE3
    }

    inner class SearchSectionViewHolder(
        binding: ItemSectionSearchBinding
    ) : ViewHolder(binding.root) {
        init {
            binding.flowSearchBar.setOnClickListener {
                onSearchBarClickCallback.onSearchBarClick()
            }
        }
    }

    inner class GenresSectionViewHolder(
        private val binding: ItemSectionGenresBinding
    ) : ViewHolder(binding.root) {
        fun bind(genres: List<Genre>) {
            binding.apply {
                rvGenres.layoutManager = LinearLayoutManager(
                    binding.root.context, HORIZONTAL, false
                )
                rvGenres.adapter = GenresAdapter(
                    genres = genres,
                    onGenreClickCallback = onGenreClickCallback
                )
            }
        }
    }

    inner class GamesSectionViewHolder(
        private val type: GamesAdapter.CardType,
        private val binding: ItemSectionGamesBinding
    ) : ViewHolder(binding.root) {
        fun bind(games: List<Game>) {
            binding.apply {
                tvLabel.text = when (type) {
                    GamesAdapter.CardType.TYPE1 -> "Upcoming"
                    GamesAdapter.CardType.TYPE2 -> "Best of the Year"
                    GamesAdapter.CardType.TYPE3 -> "Best of All Time"
                }
                rvGames.layoutManager = when (type) {
                    GamesAdapter.CardType.TYPE3 -> LinearLayoutManager(
                        binding.root.context, VERTICAL, false
                    )

                    else -> LinearLayoutManager(
                        binding.root.context, HORIZONTAL, false
                    )
                }
                rvGames.adapter = GamesAdapter(
                    type = type,
                    games = games,
                    onGameClickCallback = onGameClickCallback
                )
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
                GenresSectionViewHolder(
                    ItemSectionGenresBinding.inflate(
                        inflater, parent, false
                    )
                )
            }

            ExploreSection.GAMES_TYPE1.ordinal -> {
                GamesSectionViewHolder(
                    type = GamesAdapter.CardType.TYPE1,
                    binding = ItemSectionGamesBinding.inflate(
                        inflater, parent, false
                    )
                )
            }

            ExploreSection.GAMES_TYPE2.ordinal -> {
                GamesSectionViewHolder(
                    type = GamesAdapter.CardType.TYPE2,
                    binding = ItemSectionGamesBinding.inflate(
                        inflater, parent, false
                    )
                )
            }

            ExploreSection.GAMES_TYPE3.ordinal -> {
                GamesSectionViewHolder(
                    type = GamesAdapter.CardType.TYPE3,
                    binding = ItemSectionGamesBinding.inflate(
                        inflater, parent, false
                    )
                )
            }

            else -> throw IllegalStateException("Invalid ViewType")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (position) {
            1 -> (holder as GenresSectionViewHolder).bind(genres)
            2 -> (holder as GamesSectionViewHolder).bind(upcoming)
            3 -> (holder as GamesSectionViewHolder).bind(bestOfYear)
            4 -> (holder as GamesSectionViewHolder).bind(bestOfAllTime)
        }
    }

    override fun getItemCount(): Int {
        return 5
    }
}