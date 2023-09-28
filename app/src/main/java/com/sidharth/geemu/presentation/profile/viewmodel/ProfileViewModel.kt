package com.sidharth.geemu.presentation.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sidharth.geemu.domain.model.Collection
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.domain.usecase.collection.CollectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val collectionUseCase: CollectionUseCase
) : ViewModel() {

    private val _collections = MutableStateFlow(
        listOf(
            Collection(0, "Playing", emptyList()),
            Collection(1, "Not Played", emptyList()),
            Collection(2, "Completed", emptyList()),
            Collection(3, "Played", emptyList()),
        )
    )

    val collections: StateFlow<List<Collection>> get() = _collections

    init {
        updateCollectionsData()
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
}