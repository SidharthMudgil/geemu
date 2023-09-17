package com.sidharth.geemu.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import coil.load
import com.sidharth.geemu.databinding.ItemCardGenreBinding
import com.sidharth.geemu.domain.model.Genre
import com.sidharth.geemu.presentation.home.callback.OnGenreClickCallback

class GenresAdapter(
    private val genres: List<Genre>,
    private val onGenreClickListener: OnGenreClickCallback,
) : Adapter<GenresAdapter.GenreViewHolder>() {

    class GenreViewHolder(
        val binding: ItemCardGenreBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding = ItemCardGenreBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false,
        )

        return GenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        genres[position].apply {
            holder.binding.ivGenre.load(this.image)
            holder.binding.tvGenre.text = this.name
            holder.binding.cvGenre.setOnClickListener {
                onGenreClickListener.onGenreClick(this)
            }
        }
    }

    override fun getItemCount(): Int {
        return genres.size
    }
}