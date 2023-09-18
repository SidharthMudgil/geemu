package com.sidharth.geemu.presentation.following.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.sidharth.geemu.databinding.ItemSectionFollowingBinding
import com.sidharth.geemu.presentation.following.Following
import com.sidharth.geemu.presentation.following.callback.OnGameClickCallback
import com.sidharth.geemu.presentation.following.callback.OnUnfollowButtonClickCallback

class FollowingAdapter(
    private val followings: List<Following>,
    private val onGameClickCallback: OnGameClickCallback,
    private val onUnfollowButtonClickCallback: OnUnfollowButtonClickCallback,
) : Adapter<FollowingAdapter.FollowingViewHolder>() {

    inner class FollowingViewHolder(
        private val binding: ItemSectionFollowingBinding
    ) : ViewHolder(binding.root) {
        fun bind(following: Following) {
            binding.apply {
                btnUnfollow.setOnClickListener {
                    onUnfollowButtonClickCallback.onUnfollowButtonClick(following.tag)
                }
                tvFollowing.text = following.tag.name
                rvFollowing.layoutManager = LinearLayoutManager(
                    binding.root.context, HORIZONTAL, false
                )
                rvFollowing.adapter = GamesAdapter(
                    games = following.games,
                    onGameClickCallback = onGameClickCallback
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder {
        val binding = ItemSectionFollowingBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return FollowingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        holder.bind(followings[position])
    }

    override fun getItemCount(): Int {
        return followings.size
    }

}