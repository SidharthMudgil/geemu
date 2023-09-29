package com.sidharth.geemu.presentation.games.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sidharth.geemu.core.enum.GameFilterType
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.domain.model.Tag
import com.sidharth.geemu.domain.usecase.game.GetGameUseCase
import com.sidharth.geemu.domain.usecase.tag.TagUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor(
    private val getGameUseCase: GetGameUseCase,
    private val tagUseCase: TagUseCase,
) : ViewModel() {

    private val _games = MutableStateFlow<List<Game>>(emptyList())
    private val _following = MutableStateFlow<List<Tag>>(emptyList())

    val games: StateFlow<List<Game>> get() = _games
    val following: StateFlow<List<Tag>> get() = _following

    fun fetchGames(query: String, filter: GameFilterType) = viewModelScope.launch {
        when (filter) {
            GameFilterType.DEVELOPER -> getGameUseCase.getGamesByDevelopers(query).collect {
                _games.emit(it)
            }

            GameFilterType.GENRES -> getGameUseCase.getGamesByGenres(query).collect {
                _games.emit(it)
            }

            GameFilterType.TAGS -> getGameUseCase.getGamesByTags(query).collect {
                _games.emit(it)
            }

            GameFilterType.PUBLISHER -> getGameUseCase.getGamesByPublishers(query).collect {
                _games.emit(it)
            }

            GameFilterType.SEARCH -> getGameUseCase.getGamesBySearch(query).collect {
                _games.emit(it)
            }
        }
    }

    private fun fetchFollowingTags() = viewModelScope.launch {
        tagUseCase.getTags().collect {
            _following.emit(it)
        }
    }

    fun followTag(tag: Tag) = viewModelScope.launch {
        val newFollowing = following.value.toMutableList()
        newFollowing.add(tag)
        _following.emit(newFollowing)
        tagUseCase.followTag(tag)
    }

    fun unfollowTag(tag: Tag) = viewModelScope.launch {
        val newFollowing = following.value.toMutableList()
        newFollowing.remove(tag)
        _following.emit(newFollowing)
        tagUseCase.unfollowTag(tag)
    }
}