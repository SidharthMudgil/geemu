package com.sidharth.geemu.presentation.explore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sidharth.geemu.core.constant.Constants.EMPTY_EXPLORE_DATA
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.domain.usecase.game.GetGameUseCase
import com.sidharth.geemu.presentation.explore.ExploreData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val getGameUseCase: GetGameUseCase,
) : ViewModel() {
    private val _exploreData = MutableStateFlow(EMPTY_EXPLORE_DATA)
    private val _bestOfAllTime = MutableStateFlow<PagingData<Game>>(PagingData.empty())
    val exploreData: StateFlow<ExploreData> get() = _exploreData
    val bestOfAllTime: StateFlow<PagingData<Game>> get() = _bestOfAllTime

    init {
        fetchGenres()
        fetchUpcomingGames()
        fetchBestOfTheYear()
        fetchBestOfAllTime()
    }

    private fun fetchGenres() = viewModelScope.launch {
        getGameUseCase.getGenres().collect {
            _exploreData.emit(_exploreData.value.copy(genres = it))
        }
    }

    private fun fetchUpcomingGames() = viewModelScope.launch {
        getGameUseCase.getUpcomingGames().collect {
            _exploreData.emit(_exploreData.value.copy(upcoming = it))
        }
    }

    private fun fetchBestOfTheYear() = viewModelScope.launch {
        getGameUseCase.getBestOfTheYear().collect {
            _exploreData.emit(_exploreData.value.copy(bestOfYear = it))
        }
    }

    private fun fetchBestOfAllTime() = viewModelScope.launch {
        getGameUseCase.getBestOfAllTime().cachedIn(viewModelScope).collect {
            _bestOfAllTime.emit(it)
        }
    }
}