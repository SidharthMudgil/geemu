package com.sidharth.geemu.presentation.explore

import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.domain.model.Genre

data class ExploreData(
    val genres: List<Genre>,
    val upcoming: List<Game>,
    val bestOfYear: List<Game>,
)