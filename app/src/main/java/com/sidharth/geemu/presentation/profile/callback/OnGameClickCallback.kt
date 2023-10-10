package com.sidharth.geemu.presentation.profile.callback

import com.sidharth.geemu.domain.model.Game

interface OnGameClickCallback {
    fun onGameClick(game: Game)
    fun onGameRemove(game: Game)
    fun onGameMove(game: Game, collection: Int)
}