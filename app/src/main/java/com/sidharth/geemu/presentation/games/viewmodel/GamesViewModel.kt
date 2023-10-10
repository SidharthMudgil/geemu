package com.sidharth.geemu.presentation.games.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sidharth.geemu.core.constant.Constants
import com.sidharth.geemu.core.enums.GameFilterType
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.domain.usecase.game.GetGameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor(
    private val getGameUseCase: GetGameUseCase,
) : ViewModel() {

    private val _games = MutableStateFlow(List(10) { Constants.EMPTY_GAME })
    val games: StateFlow<List<Game>> get() = _games

    fun fetchGames(query: String, filter: GameFilterType) = viewModelScope.launch {
        when (filter) {
            GameFilterType.DEVELOPER -> getGameUseCase.getGamesByDevelopers(query).collect {
                _games.emit(it)
            }

            GameFilterType.GENRES -> getGameUseCase.getGamesByGenres(query).collect {
                _games.emit(it)
            }

            GameFilterType.TAGS -> getGameUseCase.getGamesByTags(query).collect {
                _games.emit(it)
            }

            GameFilterType.PUBLISHER -> getGameUseCase.getGamesByPublishers(query).collect {
                _games.emit(it)
            }

            GameFilterType.SEARCH -> getGameUseCase.getGamesBySearch(query).collect {
                _games.emit(it)
            }
        }
    }
}