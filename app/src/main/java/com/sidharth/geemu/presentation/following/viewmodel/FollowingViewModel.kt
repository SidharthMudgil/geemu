package com.sidharth.geemu.presentation.following.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class FollowingViewModel @Inject constructor(
    private val tagUseCase: TagUseCase,
    private val getGameUseCase: GetGameUseCase,
) : ViewModel() {

    private val _following = MutableStateFlow<List<Tag>>(emptyList())
    private val _games = MutableStateFlow<List<Game>>(emptyList())

    val following: StateFlow<List<Tag>> get() = _following
    val games: StateFlow<List<Game>> get() = _games

    init {
        fetchFollowingTags()
        fetchFilteredGames()
    }

    fun fetchFilteredGames(id: String? = null) = viewModelScope.launch {
        if (id == null && following.value.isNotEmpty()) {
            val tagIds = following.value.joinToString(",") { it.id.toString() }
            getGameUseCase.getGamesByTags(tagIds).collect { _games.emit(it) }
        } else if (id != null) {
            getGameUseCase.getGamesByTags(id).collect { _games.emit(it) }
        }
    }

    private fun fetchFollowingTags() = viewModelScope.launch {
        tagUseCase.getTags().collect {
            _following.emit(it)
        }
    }

    fun unfollowTag(tag: Tag) = viewModelScope.launch {
        val newFollowing = following.value.toMutableList()
        newFollowing.remove(tag)
        _following.emit(newFollowing)
        fetchFilteredGames()
        tagUseCase.unfollowTag(tag)
    }
}