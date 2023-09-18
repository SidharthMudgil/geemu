package com.sidharth.geemu.presentation.explore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import coil.load
import com.sidharth.geemu.databinding.ItemCardGenreBinding
import com.sidharth.geemu.domain.model.Genre
import com.sidharth.geemu.presentation.explore.callback.OnGenreClickCallback

class GenresAdapter(
    private val genres: List<Genre>,
    private val onGenreClickCallback: OnGenreClickCallback,
) : Adapter<GenresAdapter.GenreViewHolder>() {

    inner class GenreViewHolder(
        private val binding: ItemCardGenreBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(genre: Genre) {
            binding.apply {
                ivGenre.load(genre.image)
                tvGenre.text = genre.name
                cvGenre.setOnClickListener {
                    onGenreClickCallback.onGenreClick(genre)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding = ItemCardGenreBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false,
        )

        return GenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(genres[position])
    }

    override fun getItemCount(): Int {
        return genres.size
    }
}