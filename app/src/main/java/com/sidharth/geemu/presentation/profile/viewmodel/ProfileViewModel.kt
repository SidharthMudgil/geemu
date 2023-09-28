package com.sidharth.geemu.presentation.profile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sidharth.geemu.domain.model.Collection
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.domain.usecase.collection.CollectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val collectionUseCase: CollectionUseCase
) : ViewModel() {

    private val _collections = MutableLiveData(
        listOf(
            Collection(0, "Uncategorized", emptyList()),
            Collection(1, "Playing", emptyList()),
            Collection(2, "Not Played", emptyList()),
            Collection(3, "Completed", emptyList()),
            Collection(4, "Played", emptyList()),
        )
    )

    val collections: LiveData<List<Collection>> get() = _collections

    init {
        updateCollectionsData()
    }

    private fun updateCollectionsData() {
        viewModelScope.launch {
            _collections.value = collectionUseCase.getGameCollections()
        }
    }

    fun addGameToCollection(game: Game, collection: Int) {
        viewModelScope.launch {
            collectionUseCase.addGameToCollection(game, collection)
            updateCollectionsData()
        }
    }

    fun moveGameToCollection(game: Game, collection: Int) {
        viewModelScope.launch {
            collectionUseCase.moveGameToCollection(game, collection)
            updateCollectionsData()
        }
    }

    fun removeGameFromCollection(game: Game) {
        viewModelScope.launch {
            collectionUseCase.removeGameFromCollections(game)
            updateCollectionsData()
        }
    }
}