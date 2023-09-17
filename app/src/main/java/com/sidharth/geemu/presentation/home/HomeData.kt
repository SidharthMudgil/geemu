package com.sidharth.geemu.presentation.home

import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.domain.model.Genre

data class HomeData(
    val genres: List<Genre>,
    val upcoming: List<Game>,
    val bestOfYear: List<Game>,
    val bestOfAllTime: List<Game>,
)