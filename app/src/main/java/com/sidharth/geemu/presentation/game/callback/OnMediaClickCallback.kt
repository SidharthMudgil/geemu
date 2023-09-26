package com.sidharth.geemu.presentation.game.callback

interface OnMediaClickCallback {

    fun onImageClick(url: String)
    fun onVideoClick(preview: String, low: String, high: String)
}