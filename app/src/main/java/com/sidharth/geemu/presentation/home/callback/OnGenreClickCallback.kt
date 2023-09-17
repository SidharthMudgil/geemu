package com.sidharth.geemu.presentation.home.callback

import com.sidharth.geemu.domain.model.Genre

interface OnGenreClickCallback {
    fun onGenreClick(genre: Genre)
}