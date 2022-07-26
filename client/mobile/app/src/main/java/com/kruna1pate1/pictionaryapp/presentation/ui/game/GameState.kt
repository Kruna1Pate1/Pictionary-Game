package com.kruna1pate1.pictionaryapp.presentation.ui.game

import com.kruna1pate1.pictionaryapp.domain.model.Player

data class GameState(
    val player: Player,
    val isLoading: Boolean = false
)
