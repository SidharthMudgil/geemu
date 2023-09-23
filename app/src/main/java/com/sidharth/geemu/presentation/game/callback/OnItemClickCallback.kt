package com.sidharth.geemu.presentation.game.callback

import com.sidharth.geemu.presentation.game.adapter.ItemsAdapter

interface OnItemClickCallback {

    fun onItemClick(id: Int, type: ItemsAdapter.CardType)
}