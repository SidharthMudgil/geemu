package com.sidharth.geemu.presentation.creator.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sidharth.geemu.domain.model.CreatorDetails
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.domain.usecase.detail.GetCreatorDetailsUseCase
import com.sidharth.geemu.domain.usecase.game.GetGameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatorViewModel @Inject constructor(
    private val creatorDetailsUseCase: GetCreatorDetailsUseCase,
    private val getGameUseCase: GetGameUseCase,
) : ViewModel() {
    private val _creatorDetails = MutableLiveData<CreatorDetails>()
    private val _games = MutableLiveData<List<Game>>()

    val creatorDetails: LiveData<CreatorDetails> get() = _creatorDetails
    val games: LiveData<List<Game>> get() = _games

    fun fetchData(id: Int) {
        viewModelScope.launch {
            _creatorDetails.postValue(creatorDetailsUseCase.getCreatorDetails(id))
            _games.postValue(getGameUseCase.getGamesByCreators(id.toString()))
        }
    }
}