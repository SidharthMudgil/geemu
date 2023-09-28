package com.sidharth.geemu.presentation.creator.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.sidharth.geemu.core.constant.Constants
import com.sidharth.geemu.core.util.BlurTransformation
import com.sidharth.geemu.databinding.ItemCardGame2Binding
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.presentation.creator.callback.OnGameClickCallback

class GamesAdapter(
    private val games: List<Game>,
    private val onGameClickCallback: OnGameClickCallback,
) : Adapter<GamesAdapter.GameCard2ViewHolder>() {

    inner class GameCard2ViewHolder(
        private val binding: ItemCardGame2Binding
    ) : ViewHolder(binding.root) {
        fun bind(game: Game) {
            binding.apply {
                when {
                    game.image.isBlank() -> {
                        ivCover.load(Constants.BACKGROUND_IMAGE) {
                            transformations(BlurTransformation(5))
                        }
                    }

                    else -> ivCover.load(game.image)
                }
                tvName.text = game.name
                tvRatings.text = game.rating
                tvGenres.text = game.genres
                tvReleaseDate.text = game.release
                cvGame.setOnClickListener { onGameClickCallback.onGameClick(game) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameCard2ViewHolder {
        val binding = ItemCardGame2Binding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return GameCard2ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameCard2ViewHolder, position: Int) {
        holder.bind(games[position])
    }

    override fun getItemCount(): Int {
        return games.size
    }
}