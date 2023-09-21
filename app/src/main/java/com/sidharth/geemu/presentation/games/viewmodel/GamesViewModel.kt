package com.sidharth.geemu.presentation.games.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.domain.usecase.game.GameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor(
    private val gameUseCase: GameUseCase
) : ViewModel() {
    enum class FilterOption {
        CREATOR, DEVELOPER, GENRES, TAGS, PUBLISHER, SEARCH
    }

    private val _games = MutableLiveData<List<Game>>()
    val games: LiveData<List<Game>> get() = _games

    fun fetchGames(filter: FilterOption, data: String) {
        viewModelScope.launch {
            _games.postValue(
                when (filter) {
                    FilterOption.CREATOR -> gameUseCase.getGamesByCreators(data)
                    FilterOption.DEVELOPER -> gameUseCase.getGamesByDevelopers(data)
                    FilterOption.GENRES -> gameUseCase.getGamesByGenres(data)
                    FilterOption.TAGS -> gameUseCase.getGamesByTags(data)
                    FilterOption.PUBLISHER -> gameUseCase.getGamesByPublishers(data)
                    FilterOption.SEARCH -> gameUseCase.getGamesBySearch(data)
                }
            )
        }
    }
}