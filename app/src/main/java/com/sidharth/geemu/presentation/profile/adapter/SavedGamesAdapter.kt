package com.sidharth.geemu.presentation.profile.adapter

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.sidharth.geemu.core.constant.Constants
import com.sidharth.geemu.core.util.BlurTransformation
import com.sidharth.geemu.databinding.ItemCardGame4Binding
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.presentation.profile.callback.OnGameClickCallback

class SavedGamesAdapter(
    private val games: List<Game>,
    private val onGameClickCallback: OnGameClickCallback,
) : Adapter<SavedGamesAdapter.GameViewHolder>() {

    inner class GameViewHolder(
        private val binding: ItemCardGame4Binding
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
                cvGame.setOnClickListener { onGameClickCallback.onGameClick(game) }
                cvGame.setOnLongClickListener {
                    btnRemove.visibility = VISIBLE
                    btnRemove.setOnClickListener {
                        onGameClickCallback.onGameRemove(game)
                    }
                    true
                }
                cvGame.setOnFocusChangeListener { _, hasFocus ->
                    if (hasFocus.not()) {
                        btnRemove.visibility = GONE
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding = ItemCardGame4Binding.inflate(
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