package com.sidharth.geemu.presentation.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.sidharth.geemu.databinding.ItemCardGame2Binding
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.presentation.profile.callback.OnGameClickCallback

class SavedGamesAdapter(
    private val games: List<Game>,
    private val onGameClickCallback: OnGameClickCallback,
) : Adapter<SavedGamesAdapter.GameViewHolder>() {

    inner class GameViewHolder(
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding = ItemCardGame2Binding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return GameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(games[position])
    }

    override fun getItemCount(): Int {
        return games.size
    }
}