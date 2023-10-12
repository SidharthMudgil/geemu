package com.sidharth.geemu.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.sidharth.geemu.core.constant.Constants
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
    private val _filteredGames =
        MutableStateFlow(PagingData.from(List(10) { Constants.EMPTY_GAME }))

    val collections: StateFlow<List<Collection>> get() = _collections
    val following: StateFlow<List<Tag>> get() = _following
    val filteredGames: StateFlow<PagingData<Game>> get() = _filteredGames

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

    fun fetchFilteredGames(tags: String, fetchAll: Boolean = false) = viewModelScope.launch {
        getGameUseCase.getGamesByTags(
            when (fetchAll) {
                true -> following.value.joinToString(",") { it.id.toString() }
                else -> tags
            }
        ).collect {
            _filteredGames.emit(it)
        }
    }
}