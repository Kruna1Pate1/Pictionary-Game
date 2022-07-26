package com.kruna1pate1.pictionaryapp.presentation.ui.home

import com.kruna1pate1.pictionaryapp.domain.model.Player
import com.kruna1pate1.pictionaryapp.domain.model.enums.PlayerStatus

data class HomeState(
    val player: Player = Player(0, "", PlayerStatus.IDLE),
    val isLoading: Boolean = false
)
