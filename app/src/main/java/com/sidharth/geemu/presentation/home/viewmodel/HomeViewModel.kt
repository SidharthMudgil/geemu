package com.sidharth.geemu.presentation.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.domain.model.Genre
import com.sidharth.geemu.domain.usecase.game.GameUseCase
import com.sidharth.geemu.presentation.home.HomeData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val gameUseCase: GameUseCase,
) : ViewModel() {
    private val _genres = MutableLiveData<List<Genre>>()
    private val _upcoming = MutableLiveData<List<Game>>()
    private val _bestOfYear = MutableLiveData<List<Game>>()
    private val _bestOfAllTime = MutableLiveData<List<Game>>()

    private val _homeData = MutableLiveData<HomeData>()

    val homeData: LiveData<HomeData> get() = _homeData

    init {
        viewModelScope.launch {
            _genres.postValue(gameUseCase.getGenres())
            _upcoming.postValue(gameUseCase.getUpcomingGames())
            _bestOfYear.postValue(gameUseCase.getBestOfTheYear())
            _bestOfAllTime.postValue(gameUseCase.getBestOfAllTime())

            _homeData.postValue(
                HomeData(
                    genres = _genres.value ?: listOf(),
                    upcoming = _upcoming.value ?: listOf(),
                    bestOfYear = _bestOfYear.value ?: listOf(),
                    bestOfAllTime = _bestOfAllTime.value ?: listOf(),
                )
            )
        }
    }
}