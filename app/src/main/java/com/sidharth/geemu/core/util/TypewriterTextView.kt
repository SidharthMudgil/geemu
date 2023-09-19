package com.sidharth.geemu.core.util

import android.animation.TimeInterpolator
import android.content.Context
import android.util.AttributeSet
import android.view.animation.LinearInterpolator
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TypewriterTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0,
) : MaterialTextView(context, attrs, defStyleAttr) {

    private var animationJob: Job? = null
    private var delayMillis: Long = 200
    private var textList: List<String> = listOf("Type your text here")
    private var current = 0
    private var interpolator: TimeInterpolator = LinearInterpolator()

    fun startTypewriterAnimation(
        texts: List<String>,
    ) {
        textList = texts
        animationJob?.cancel()
        animationJob = CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                animateTextWithInterpolator(texts[current++ % texts.size])
            }
        }
    }

    fun stopTypewriterAnimation() {
        animationJob?.cancel()
    }

    fun setAnimationDelay(delayMillis: Long) {
        this.delayMillis = delayMillis
    }

    fun setInterpolator(interpolator: TimeInterpolator) {
        this.interpolator = interpolator
    }

    private suspend fun animateTextWithInterpolator(text: String) {
        val totalDuration = delayMillis
        val textLength = text.length

        for (j in 0..textLength) {
            val interpolatedDuration = interpolator.getInterpolation(j / textLength.toFloat()) * totalDuration
            setText(text.substring(0, j))
            delay(interpolatedDuration.toLong())
        }
    }
}