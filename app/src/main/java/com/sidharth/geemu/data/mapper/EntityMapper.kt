package com.sidharth.geemu.data.mapper

import com.sidharth.geemu.data.local.GameEntity
import com.sidharth.geemu.data.local.TagEntity
import com.sidharth.geemu.domain.model.Collection
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.domain.model.Tag

object EntityMapper {

    fun toGameCollections(games: List<GameEntity>): List<Collection> {
        return games.groupBy { it.collection }.map {
            Collection(
                id = it.key,
                name = it.key.toCollectionName(),
                games = it.value.map { gameEntity -> gameEntity.toGame() }
            )
        }
    }

    private fun Int.toCollectionName(): String {
        return when (this) {
            0 -> "Playing"
            1 -> "Not Played"
            2 -> "Completed"
            3 -> "Played"
            else -> "Uncategorized"
        }
    }

    private fun GameEntity.toGame(): Game {
        return Game(
            id = this.id,
            name = this.name,
            image = this.image,
            genres = this.genres,
            release = this.release,
            rating = this.rating,
        )
    }

    fun Game.toGameEntity(collection: Int): GameEntity {
        return GameEntity(
            id = this.id,
            name = this.name,
            image = this.image,
            genres = this.genres,
            release = this.release,
            rating = this.rating,
            collection = collection,
        )
    }

    fun TagEntity.toTag(): Tag {
        return Tag(
            id = this.id,
            name = this.name,
            image = this.image,
            count = this.count,
        )
    }

    fun Tag.toTagEntity(): TagEntity {
        return TagEntity(
            id = this.id,
            name = this.name,
            image = this.image,
            count = this.count,
        )
    }
}