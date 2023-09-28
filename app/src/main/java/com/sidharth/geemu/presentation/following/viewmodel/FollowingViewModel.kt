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

    private fun fetchFilteredGames(tag: String? = null) = viewModelScope.launch {
        if (tag == null && following.value.isNotEmpty()) {
            val tagIds = following.value.joinToString(",") { it.id.toString() }
            getGameUseCase.getGamesByTags(tagIds).collect { _games.emit(it) }
        } else if (tag != null) {
            getGameUseCase.getGamesByTags(tag).collect { _games.emit(it) }
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