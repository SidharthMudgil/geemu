package com.sidharth.geemu.presentation.game.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.domain.usecase.collection.CollectionUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val collectionUseCaseImpl: CollectionUseCaseImpl
) : ViewModel() {
    private val _savedGames: MutableStateFlow<List<Game>> = MutableStateFlow(emptyList())
    val savedGames: StateFlow<List<Game>> get() = _savedGames

    fun getCollections() = viewModelScope.launch {
        collectionUseCaseImpl.getGameCollections().collect {
            _savedGames.emit(
                it.flatMap { collection ->
                    collection.games
                }
            )
        }
    }

    fun addGameToCollection(game: Game, collection: Int) = viewModelScope.launch {
        savedGames.value.toMutableList().apply {
            add(game)
            _savedGames.emit(this)
        }
        collectionUseCaseImpl.addGameToCollection(
            game = game,
            collection = collection
        )
    }

    fun deleteGameFromCollection(game: Game) = viewModelScope.launch {
        savedGames.value.toMutableList().apply {
            remove(game)
            _savedGames.emit(this)
        }
        collectionUseCaseImpl.removeGameFromCollections(game)
    }
}

