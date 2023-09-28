package com.sidharth.geemu.presentation.explore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sidharth.geemu.core.constant.Constants.EMPTY_EXPLORE_DATA
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.domain.model.Genre
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
    val exploreData: StateFlow<ExploreData> get() = _exploreData

    init {
        fetchExploreData()
    }

    private fun fetchExploreData() = viewModelScope.launch {
        var genres: List<Genre> = listOf()
        var upcoming: List<Game> = listOf()
        var bestOfYear: List<Game> = listOf()
        var bestOfAllTime: List<Game> = listOf()

        getGameUseCase.getGenres().collect { genres = it }
        getGameUseCase.getUpcomingGames().collect { upcoming = it }
        getGameUseCase.getBestOfTheYear().collect { bestOfYear = it }
        getGameUseCase.getBestOfAllTime().collect { bestOfAllTime = it }

        _exploreData.emit(
            ExploreData(
                genres = genres,
                upcoming = upcoming,
                bestOfYear = bestOfYear,
                bestOfAllTime = bestOfAllTime,
            )
        )
    }
}