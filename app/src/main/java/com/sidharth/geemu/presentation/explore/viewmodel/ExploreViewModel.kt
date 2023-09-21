package com.sidharth.geemu.presentation.explore.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sidharth.geemu.domain.usecase.game.GetGameUseCase
import com.sidharth.geemu.presentation.explore.ExploreData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val getGameUseCase: GetGameUseCase,
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
                    genres = getGameUseCase.getGenres(),
                    upcoming = getGameUseCase.getUpcomingGames(),
                    bestOfYear = getGameUseCase.getBestOfTheYear(),
                    bestOfAllTime = getGameUseCase.getBestOfAllTime(),
                )
            )
        }
    }
}