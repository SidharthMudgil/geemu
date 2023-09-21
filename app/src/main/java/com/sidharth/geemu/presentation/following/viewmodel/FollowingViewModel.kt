package com.sidharth.geemu.presentation.following.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.domain.model.Tag
import com.sidharth.geemu.domain.usecase.game.GetGameUseCase
import com.sidharth.geemu.domain.usecase.tag.TagUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowingViewModel @Inject constructor(
    private val tagUseCase: TagUseCase,
    private val getGameUseCase: GetGameUseCase,
) : ViewModel() {

    private val _following = MutableLiveData<List<Tag>>()
    private val _games = MutableLiveData<List<Game>>()

    val following: LiveData<List<Tag>> get() = _following
    val games: LiveData<List<Game>> get() = _games

    init {
        fetchFollowingTags()
        fetchFilteredGames()
    }

    private fun fetchFilteredGames(tag: String? = null) {
        viewModelScope.launch {
            if (tag == null && following.value?.isNotEmpty() == true) {
                val tagIds = following.value?.joinToString(",") { it.id.toString() } ?: ""
                _games.postValue(getGameUseCase.getGamesByTags(tagIds))
            } else if (tag != null) {
                _games.postValue(getGameUseCase.getGamesByTags(tag))
            }
        }
    }

    private fun fetchFollowingTags() {
        viewModelScope.launch {
            _following.postValue(tagUseCase.getTags())
        }
    }

    fun followTag(tag: Tag) {
        viewModelScope.launch {
            val newFollowing = following.value?.toMutableList() ?: mutableListOf()
            newFollowing.add(tag)
            _following.postValue(newFollowing)

            tagUseCase.followTag(tag)
        }
    }

    fun unfollowTag(tag: Tag) {
        viewModelScope.launch {
            val newFollowing = following.value?.toMutableList() ?: mutableListOf()
            newFollowing.remove(tag)
            _following.postValue(newFollowing)

            tagUseCase.unfollowTag(tag)
        }
    }
}