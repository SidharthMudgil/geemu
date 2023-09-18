package com.sidharth.geemu.presentation.following.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sidharth.geemu.domain.model.Tag
import com.sidharth.geemu.domain.usecase.tag.TagUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowingViewModel @Inject constructor(
    private val tagUseCase: TagUseCase
): ViewModel() {

    private val _following = MutableLiveData<List<Tag>>()

    val following get() = _following

    init {
        fetchFollowing()
    }

    private fun fetchFollowing() {
        viewModelScope.launch {
            _following.postValue(tagUseCase.getTags())
        }
    }

    fun followTag(tag: Tag) {
        viewModelScope.launch {
            tagUseCase.followTag(tag)
            fetchFollowing()
        }
    }

    fun unfollowTag(tag: Tag) {
        viewModelScope.launch {
            tagUseCase.unfollowTag(tag)
            fetchFollowing()
        }
    }
}