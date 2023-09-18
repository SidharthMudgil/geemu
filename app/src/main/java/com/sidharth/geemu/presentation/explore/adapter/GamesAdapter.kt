package com.sidharth.geemu.presentation.explore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.sidharth.geemu.databinding.ItemCardGame1Binding
import com.sidharth.geemu.databinding.ItemCardGame2Binding
import com.sidharth.geemu.databinding.ItemCardGame3Binding
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.presentation.explore.callback.OnGameClickCallback

class GamesAdapter(
    private val type: CardType,
    private val games: List<Game>,
    private val onGameClickCallback: OnGameClickCallback,
) : Adapter<ViewHolder>() {

    enum class CardType { TYPE1, TYPE2, TYPE3 }

    inner class GameCard1ViewHolder(
        private val binding: ItemCardGame1Binding
    ) : ViewHolder(binding.root) {
        fun bind(game: Game) {
            binding.apply {
                ivPoster.load(game.image)
                tvGame.text = game.name
                tvRelease.text = game.release
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
                tvReleaseDate.text = game.release
                cvGame.setOnClickListener { onGameClickCallback.onGameClick(game) }
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
                tvGenre.text = game.genres
                tvRating.text = game.rating
                cvGame.setOnClickListener { onGameClickCallback.onGameClick(game) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (type) {
            CardType.TYPE1 -> GameCard1ViewHolder(
                ItemCardGame1Binding.inflate(
                    inflater, parent, false
                )
            )

            CardType.TYPE2 -> GameCard2ViewHolder(
                ItemCardGame2Binding.inflate(
                    inflater, parent, false
                )
            )

            CardType.TYPE3 -> GameCard3ViewHolder(
                ItemCardGame3Binding.inflate(
                    inflater, parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (type) {
            CardType.TYPE1 -> {
                (holder as GameCard1ViewHolder).bind(games[position])
            }

            CardType.TYPE2 -> {
                (holder as GameCard2ViewHolder).bind(games[position])
            }

            CardType.TYPE3 -> {
                (holder as GameCard3ViewHolder).bind(games[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return games.size
    }
}