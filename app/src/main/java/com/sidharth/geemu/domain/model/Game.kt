package com.sidharth.geemu.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Game(
    val id: Int,
    val name: String,
    val image: String,
    val genres: String,
    val release: String,
    val rating: Double,
) : Parcelable