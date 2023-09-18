package com.sidharth.geemu.presentation.following

import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.domain.model.Tag

data class Following(
    val tag: Tag,
    val games: List<Game>
)