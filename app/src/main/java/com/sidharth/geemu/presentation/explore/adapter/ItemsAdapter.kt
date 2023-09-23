package com.sidharth.geemu.presentation.explore.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.sidharth.geemu.core.util.DateTimeUtils.toPrettyFormat
import com.sidharth.geemu.databinding.ItemCardGame1Binding
import com.sidharth.geemu.databinding.ItemCardGame2Binding
import com.sidharth.geemu.databinding.ItemCardGame3Binding
import com.sidharth.geemu.databinding.ItemCardGenreBinding
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.domain.model.Genre
import com.sidharth.geemu.presentation.explore.callback.OnGameClickCallback
import com.sidharth.geemu.presentation.explore.callback.OnGenreClickCallback

class ItemsAdapter(
    private val type: CardType,
    private val items: List<Any>,
    private val onItemClickCallback: Any,
) : Adapter<ViewHolder>() {

    enum class CardType {
        GENRE, GAME_TYPE1, GAME_TYPE2, GAME_TYPE3
    }

    inner class GenreViewHolder(
        private val binding: ItemCardGenreBinding
    ) : ViewHolder(binding.root) {
        fun bind(genre: Genre) {
            binding.apply {
                ivGenre.load(genre.image)
                tvGenre.text = genre.name
                cvGenre.setOnClickListener {
                    (onItemClickCallback as OnGenreClickCallback).onGenreClick(genre)
                }
            }
        }
    }

    inner class GameCard1ViewHolder(
        private val binding: ItemCardGame1Binding
    ) : ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(game: Game) {
            binding.apply {
                ivPoster.load(game.image)
                tvGame.text = game.name
                tvGame.isSelected = true
                tvRelease.text = "Releasing on ${game.release.toPrettyFormat()}"
                tvGenres.text = game.genres
            }
        }
    }

    inner class GameCard2ViewHolder(
        private val binding: ItemCardGame2Binding
    ) : ViewHolder(binding.root) {
        fun bind(game: Game) {
            binding.apply {
                ivCover.load(game.image)
                tvName.text = game.name
                tvRatings.text = game.rating
                tvGenres.text = game.genres
                tvName.isSelected = true
                tvReleaseDate.text = game.release
                cvGame.setOnClickListener {
                    (onItemClickCallback as OnGameClickCallback).onGameClick(
                        game
                    )
                }
            }
        }
    }

    inner class GameCard3ViewHolder(
        private val binding: ItemCardGame3Binding
    ) : ViewHolder(binding.root) {
        fun bind(game: Game) {
            binding.apply {
                ivGame.load(game.image)
                tvGame.text = game.name
                tvGame.isSelected = true
                tvGenres.text = game.genres
                tvRatings.text = game.rating
                cvGame.setOnClickListener {
                    (onItemClickCallback as OnGameClickCallback).onGameClick(
                        game
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (type) {
            CardType.GENRE -> GenreViewHolder(
                ItemCardGenreBinding.inflate(
                    inflater, parent, false
                )
            )

            CardType.GAME_TYPE1 -> GameCard1ViewHolder(
                ItemCardGame1Binding.inflate(
                    inflater, parent, false
                )
            )

            CardType.GAME_TYPE2 -> GameCard2ViewHolder(
                ItemCardGame2Binding.inflate(
                    inflater, parent, false
                )
            )

            CardType.GAME_TYPE3 -> GameCard3ViewHolder(
                ItemCardGame3Binding.inflate(
                    inflater, parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (type) {
            CardType.GENRE -> {
                (holder as GenreViewHolder).bind(items[position] as Genre)
            }

            CardType.GAME_TYPE1 -> {
                (holder as GameCard1ViewHolder).bind(items[position] as Game)
            }

            CardType.GAME_TYPE2 -> {
                (holder as GameCard2ViewHolder).bind(items[position] as Game)
            }

            CardType.GAME_TYPE3 -> {
                (holder as GameCard3ViewHolder).bind(items[position] as Game)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}