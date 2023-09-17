package com.sidharth.geemu.presentation.home.callback

import com.sidharth.geemu.domain.model.Game

interface OnGameClickCallback {
    fun onGameClick(game: Game)
}