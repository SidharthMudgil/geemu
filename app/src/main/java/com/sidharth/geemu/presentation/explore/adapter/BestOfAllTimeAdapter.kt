package com.sidharth.geemu.presentation.explore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.sidharth.geemu.core.constant.Constants
import com.sidharth.geemu.core.util.BlurTransformation
import com.sidharth.geemu.databinding.ItemCardGame3Binding
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.presentation.explore.callback.OnGameClickCallback

class BestOfAllTimeAdapter(
    private val onGameClickCallback: OnGameClickCallback,
) : PagingDataAdapter<Game, BestOfAllTimeAdapter.GameViewHolder>(GameComparator) {
    inner class GameViewHolder(
        private val binding: ItemCardGame3Binding
    ) : ViewHolder(binding.root) {
        fun bind(game: Game) {
            binding.apply {
                when {
                    game.image.isBlank() -> {
                        ivGame.load(Constants.BACKGROUND_IMAGE) {
                            transformations(BlurTransformation(5))
                        }
                    }

                    else -> ivGame.load(game.image)
                }
                tvGame.text = game.name
                tvGame.isSelected = true
                tvGenres.text = game.genres
                tvRatings.text = game.rating
                cvGame.setOnClickListener {
                    onGameClickCallback.onGameClick(game)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding = ItemCardGame3Binding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return GameViewHolder(binding)
    }

    object GameComparator : DiffUtil.ItemCallback<Game>() {
        override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem == newItem
        }
    }
}