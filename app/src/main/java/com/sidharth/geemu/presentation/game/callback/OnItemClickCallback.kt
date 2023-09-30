package com.sidharth.geemu.presentation.game.callback

import com.sidharth.geemu.core.enums.GameFilterType
import com.sidharth.geemu.domain.model.Tag

interface OnItemClickCallback {

    fun onItemClick(id: Int, name: String, type: GameFilterType, tag: Tag? = null)
}