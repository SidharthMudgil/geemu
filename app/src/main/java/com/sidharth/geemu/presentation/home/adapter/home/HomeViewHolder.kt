package com.sidharth.geemu.presentation.home.adapter.home

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.sidharth.geemu.databinding.ItemCardGame1Binding
import com.sidharth.geemu.databinding.ItemCardGame2Binding
import com.sidharth.geemu.databinding.ItemCardGame3Binding
import com.sidharth.geemu.databinding.ItemCardGenreBinding
import com.sidharth.geemu.databinding.ItemSectionSearchBinding
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.domain.model.Genre

class SearchViewHolder(
    binding: ItemSectionSearchBinding
) : ViewHolder(binding.root) {
    init {
        binding.flowSearchBar.setOnClickListener {

        }
    }
}

class GenreCardViewHolder(
    private val binding: ItemCardGenreBinding
) : ViewHolder(binding.root) {
    fun bind(genre: Genre) {
        binding.apply {
            ivGenre.load(genre.image)
            tvGenre.text = genre.name
            cvGenre.setOnClickListener {

            }
        }
    }
}

// todo check if there is need for click listener because most data will be empty
class GameCard1ViewHolder(
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

class GameCard2ViewHolder(
    private val binding: ItemCardGame2Binding
) : ViewHolder(binding.root) {
    fun bind(game: Game) { // todo add click listener
        binding.apply {
            ivCover.load(game.image)
            tvName.text = game.name
            tvRatings.text = game.rating
            tvGenres.text = game.genres
            tvReleaseDate.text = game.release
        }
    }
}

class GameCard3ViewHolder( // todo add click listener
    private val binding: ItemCardGame3Binding
) : ViewHolder(binding.root) {
    fun bind(game: Game) {
        binding.apply {
            ivGame.load(game.image)
            tvGame.text = game.name
            tvGenre.text = game.genres
            tvRating.text = game.rating
        }
    }
}