package com.sidharth.geemu.domain.model

data class CreatorDetails(
    val id: Int,
    val name: String,
    val image: String,
    val background: String,
    val description: String,
    val rating: String,
    val gamesCount: Int,
    val ratings: List<Rating>,
    val timeline: List<Timeline>,
)

data class Timeline(
    val year: Int,
    val count: Int,
)