package com.sidharth.geemu.domain.model

data class Game(
    val id: Int,
    val name: String,
    val image: String,
    val genres: List<Genre>,
    val release: String,
    val rating: String,
)

