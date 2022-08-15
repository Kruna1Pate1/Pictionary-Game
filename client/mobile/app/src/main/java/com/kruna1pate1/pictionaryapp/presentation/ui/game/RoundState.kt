package com.kruna1pate1.pictionaryapp.presentation.ui.game

import com.kruna1pate1.pictionaryapp.domain.model.WordGroup
import com.kruna1pate1.pictionaryapp.domain.model.enums.RoundStatus

data class RoundState(
    val wordGroup: WordGroup? = null,
    val canDraw: Boolean = false,
    val status: RoundStatus = RoundStatus.INITIAL,
    val totalTime: Float = 0F,
    val hint: String = "____"
)
