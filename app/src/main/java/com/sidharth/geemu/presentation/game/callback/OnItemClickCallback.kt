package com.sidharth.geemu.presentation.game.callback

import com.sidharth.geemu.core.enums.GameFilterType

interface OnItemClickCallback {

    fun onItemClick(id: Int, name: String, type: GameFilterType)
}