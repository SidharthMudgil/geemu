package com.sidharth.geemu.presentation.following.callback

import com.sidharth.geemu.domain.model.Tag

interface OnUnfollowButtonClickCallback {
    fun onUnfollowButtonClick(tag: Tag)
}