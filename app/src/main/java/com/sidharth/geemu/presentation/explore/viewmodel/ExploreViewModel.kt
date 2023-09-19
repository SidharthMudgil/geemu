package com.sidharth.geemu.presentation.explore.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sidharth.geemu.domain.usecase.game.GameUseCase
import com.sidharth.geemu.presentation.explore.ExploreData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val gameUseCase: GameUseCase,
) : ViewModel() {
    private val _exploreData = MutableLiveData<ExploreData>()
    val exploreData: LiveData<ExploreData> get() = _exploreData

    init {
        fetchExploreData()
    }

    private fun fetchExploreData() {
        viewModelScope.launch {

            _exploreData.postValue(
                ExploreData(
                    genres = gameUseCase.getGenres(),
                    upcoming = gameUseCase.getUpcomingGames(),
                    bestOfYear = gameUseCase.getBestOfTheYear(),
                    bestOfAllTime = gameUseCase.getBestOfAllTime(),
                )
            )
        }
    }
}