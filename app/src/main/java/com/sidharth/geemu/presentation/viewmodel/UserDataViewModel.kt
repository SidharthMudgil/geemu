package com.sidharth.geemu.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sidharth.geemu.domain.model.Collection
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.domain.model.Tag
import com.sidharth.geemu.domain.usecase.collection.CollectionUseCase
import com.sidharth.geemu.domain.usecase.game.GetGameUseCase
import com.sidharth.geemu.domain.usecase.tag.TagUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDataViewModel @Inject constructor(
    private val tagUseCase: TagUseCase,
    private val collectionUseCase: CollectionUseCase,
    private val getGameUseCase: GetGameUseCase,
) : ViewModel() {
    private val _collections = MutableStateFlow(
        listOf(
            Collection(0, "Playing", emptyList()),
            Collection(1, "Not Played", emptyList()),
            Collection(2, "Completed", emptyList()),
            Collection(3, "Played", emptyList()),
        )
    )
    private val _following = MutableStateFlow<List<Tag>>(emptyList())
    private val _games = MutableStateFlow<List<Game>>(emptyList())

    val collections: StateFlow<List<Collection>> get() = _collections
    val following: StateFlow<List<Tag>> get() = _following
    val games: StateFlow<List<Game>> get() = _games

    init {
        updateCollectionsData()
        fetchFollowingTags()
    }

    private fun updateCollectionsData() = viewModelScope.launch {
        collectionUseCase.getGameCollections().collect {
            _collections.emit(it)
        }
    }

    fun addGameToCollection(game: Game, collection: Int) = viewModelScope.launch {
        collectionUseCase.addGameToCollection(game, collection)
        updateCollectionsData()
    }

    fun moveGameToCollection(game: Game, collection: Int) = viewModelScope.launch {
        collectionUseCase.moveGameToCollection(game, collection)
        updateCollectionsData()
    }

    fun removeGameFromCollection(game: Game) = viewModelScope.launch {
        collectionUseCase.removeGameFromCollections(game)
        updateCollectionsData()
    }

    private fun fetchFollowingTags() = viewModelScope.launch {
        tagUseCase.getTags().collect {
            _following.emit(it)
            fetchFilteredGames(
                it.joinToString(",") { tag -> tag.id.toString() }
            )
        }
    }

    fun followTag(tag: Tag) = viewModelScope.launch {
        tagUseCase.followTag(tag)
        fetchFollowingTags()
    }

    fun unfollowTag(tag: Tag) = viewModelScope.launch {
        tagUseCase.unfollowTag(tag)
        fetchFollowingTags()
    }

    fun fetchFilteredGames(tags: String) = viewModelScope.launch {
        getGameUseCase.getGamesByTags(tags).collect {
            _games.emit(it)
        }
    }
}