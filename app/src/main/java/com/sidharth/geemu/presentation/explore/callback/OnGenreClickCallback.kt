package com.sidharth.geemu.presentation.explore.callback

import com.sidharth.geemu.domain.model.Genre

interface OnGenreClickCallback {
    fun onGenreClick(genre: Genre)
}