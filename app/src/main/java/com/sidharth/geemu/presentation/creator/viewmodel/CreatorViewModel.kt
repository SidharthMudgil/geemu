package com.sidharth.geemu.presentation.creator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sidharth.geemu.core.constant.Constants.EMPTY_CREATOR_DETAILS
import com.sidharth.geemu.domain.model.CreatorDetails
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.domain.usecase.detail.GetCreatorDetailsUseCase
import com.sidharth.geemu.domain.usecase.game.GetGameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatorViewModel @Inject constructor(
    private val creatorDetailsUseCase: GetCreatorDetailsUseCase,
    private val getGameUseCase: GetGameUseCase,
) : ViewModel() {
    private val _creatorDetails = MutableStateFlow(EMPTY_CREATOR_DETAILS)
    private val _games = MutableStateFlow<List<Game>>(listOf())

    val creatorDetails: StateFlow<CreatorDetails> get() = _creatorDetails
    val games: StateFlow<List<Game>> get() = _games

    fun fetchData(id: Int) = viewModelScope.launch {
        creatorDetailsUseCase.getCreatorDetails(id).collect {
            _creatorDetails.emit(it)
        }
        getGameUseCase.getGamesByCreators(id.toString()).collect {
            _games.emit(it)
        }
    }
}