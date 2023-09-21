package com.sidharth.geemu.presentation.games.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.sidharth.geemu.databinding.ItemCardGame3Binding
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.presentation.games.callback.OnGameClickCallback

class GamesAdapter(
    private val games: List<Game>,
    private val onGameClickCallback: OnGameClickCallback,
) : Adapter<GamesAdapter.GameViewHolder>() {

    inner class GameViewHolder(
        private val binding: ItemCardGame3Binding
    ) : ViewHolder(binding.root) {
        fun bind(game: Game) {
            binding.apply {
                ivGame.load(game.image)
                tvGame.text = game.name
                tvRatings.text = game.rating
                tvGenres.text = game.genres
                cvGame.setOnClickListener { onGameClickCallback.onGameClick(game) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding = ItemCardGame3Binding.inflate(
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