package com.sidharth.geemu.presentation.games.callback

import com.sidharth.geemu.domain.model.Game

interface OnGameClickCallback {
    fun onGameClick(game: Game)
}