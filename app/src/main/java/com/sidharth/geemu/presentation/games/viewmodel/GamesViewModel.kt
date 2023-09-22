package com.sidharth.geemu.presentation.games.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sidharth.geemu.core.enum.GameFilterType
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.domain.usecase.game.GetGameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor(
    private val getGameUseCase: GetGameUseCase
) : ViewModel() {

    private val _games = MutableLiveData<List<Game>>()
    val games: LiveData<List<Game>> get() = _games

    fun fetchGames(filter: GameFilterType, data: String) {
        viewModelScope.launch {
            _games.postValue(
                when (filter) {
                    GameFilterType.DEVELOPER -> getGameUseCase.getGamesByDevelopers(data)
                    GameFilterType.GENRES -> getGameUseCase.getGamesByGenres(data)
                    GameFilterType.TAGS -> getGameUseCase.getGamesByTags(data)
                    GameFilterType.PUBLISHER -> getGameUseCase.getGamesByPublishers(data)
                }
            )
        }
    }
}