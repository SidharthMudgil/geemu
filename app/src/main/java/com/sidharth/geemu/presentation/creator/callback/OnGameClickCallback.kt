package com.sidharth.geemu.presentation.creator.callback

import com.sidharth.geemu.domain.model.Game

interface OnGameClickCallback {
    fun onGameClick(game: Game)
}