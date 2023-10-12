package com.sidharth.geemu.presentation.games.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
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

    private val _games = MutableStateFlow(PagingData.from(List(10) { Constants.EMPTY_GAME }))
    val games: StateFlow<PagingData<Game>> get() = _games

    fun fetchGames(query: String, filter: GameFilterType) = viewModelScope.launch {
        when (filter) {
            GameFilterType.DEVELOPER -> getGameUseCase.getGamesByDevelopers(query)
                .cachedIn(viewModelScope)
                .collect {
                _games.emit(it)
            }

            GameFilterType.GENRES -> getGameUseCase.getGamesByGenres(query)
                .cachedIn(viewModelScope)
                .collect {
                _games.emit(it)
            }

            GameFilterType.TAGS -> getGameUseCase.getGamesByTags(query)
                .cachedIn(viewModelScope)
                .collect {
                _games.emit(it)
            }

            GameFilterType.PUBLISHER -> getGameUseCase.getGamesByPublishers(query)
                .cachedIn(viewModelScope)
                .collect {
                _games.emit(it)
            }

            GameFilterType.SEARCH -> getGameUseCase.getGamesBySearch(query)
                .cachedIn(viewModelScope)
                .collect {
                _games.emit(it)
            }
        }
    }
}