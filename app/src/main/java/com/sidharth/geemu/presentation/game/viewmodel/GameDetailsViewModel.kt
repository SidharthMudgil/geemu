package com.sidharth.geemu.presentation.game.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sidharth.geemu.domain.model.GameDetails
import com.sidharth.geemu.domain.usecase.detail.GetGameDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailsViewModel @Inject constructor(
    private val getGameDetailsUseCase: GetGameDetailsUseCase,
) : ViewModel() {

    private val _gameDetails = MutableLiveData<GameDetails>()
    val gameDetails: LiveData<GameDetails> get() = _gameDetails

    fun fetchGameDetails(id: Int) {
        viewModelScope.launch {
            getGameDetailsUseCase.getGameDetails(id)
        }
    }
}