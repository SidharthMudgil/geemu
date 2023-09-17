package com.sidharth.geemu.presentation.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.sidharth.geemu.databinding.ItemSectionGamesBinding
import com.sidharth.geemu.domain.model.Collection
import com.sidharth.geemu.presentation.profile.callback.OnGameClickCallback

class CollectionsAdapter(
    private val collections: List<Collection>,
    private val onGameClickCallback: OnGameClickCallback,
) : Adapter<CollectionsAdapter.CollectionViewHolder>() {

    inner class CollectionViewHolder(
        private val binding: ItemSectionGamesBinding
    ) : ViewHolder(binding.root) {
        fun bind(collection: Collection) {
            binding.apply {
                tvLabel.text = collection.name
                rvGames.layoutManager = LinearLayoutManager(
                    binding.root.context, HORIZONTAL, false
                )
                rvGames.adapter = SavedGamesAdapter(
                    games = collection.games,
                    onGameClickCallback = onGameClickCallback
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        val binding = ItemSectionGamesBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return CollectionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        holder.bind(collections[position])
    }

    override fun getItemCount(): Int {
        return collections.size
    }

}