package com.sidharth.geemu.presentation.game.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sidharth.geemu.core.constant.Constants.EMPTY_GAME_DETAILS
import com.sidharth.geemu.domain.model.GameDetails
import com.sidharth.geemu.domain.usecase.detail.GetGameDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailsViewModel @Inject constructor(
    private val getGameDetailsUseCase: GetGameDetailsUseCase,
) : ViewModel() {

    private val _gameDetails = MutableStateFlow(EMPTY_GAME_DETAILS)
    val gameDetails: StateFlow<GameDetails> get() = _gameDetails

    fun fetchGameDetails(id: Int) = viewModelScope.launch {
        getGameDetailsUseCase.getGameDetails(id).collect {
            _gameDetails.emit(it)
        }
    }
}