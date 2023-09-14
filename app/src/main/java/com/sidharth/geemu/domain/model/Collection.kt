package com.sidharth.geemu.domain.model

data class Collection(
    val id: Int,
    val name: String,
    val games: List<Game>,
)
