package com.sidharth.geemu.presentation.following.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sidharth.geemu.domain.model.Tag
import com.sidharth.geemu.domain.usecase.game.GameUseCase
import com.sidharth.geemu.domain.usecase.tag.TagUseCase
import com.sidharth.geemu.presentation.following.Following
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowingViewModel @Inject constructor(
    private val tagUseCase: TagUseCase,
    private val gameUseCase: GameUseCase,
) : ViewModel() {

    private val _following = MutableLiveData<List<Following>>()

    val following: LiveData<List<Following>> get() = _following

    init {
        fetchFollowing()
    }

    private fun fetchFollowing() {
        viewModelScope.launch {
            val followings = mutableListOf<Following>()

            tagUseCase.getTags().forEach {
                followings.add(
                    Following(
                        tag = it,
                        games = gameUseCase.getGamesByTags(it.id.toString())
                    )
                )
            }

            _following.postValue(followings)
        }
    }

    fun followTag(tag: Tag) {
        viewModelScope.launch {
            tagUseCase.followTag(tag)

            val newFollowing = following.value?.toMutableList() ?: mutableListOf()
            newFollowing.add(
                Following(
                    tag = tag,
                    games = gameUseCase.getGamesByTags(tag.id.toString()),
                )
            )
            _following.postValue(newFollowing)
        }
    }

    fun unfollowTag(tag: Tag) {
        viewModelScope.launch {
            tagUseCase.unfollowTag(tag)

            val newFollowing = following.value?.toMutableList() ?: mutableListOf()
            newFollowing.removeAll { it.tag == tag }
            _following.postValue(newFollowing)
        }
    }
}