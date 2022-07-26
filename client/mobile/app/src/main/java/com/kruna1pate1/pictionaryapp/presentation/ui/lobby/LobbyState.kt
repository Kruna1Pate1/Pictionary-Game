package com.kruna1pate1.pictionaryapp.presentation.ui.lobby

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.kruna1pate1.pictionaryapp.domain.model.Player
import com.kruna1pate1.pictionaryapp.domain.model.Room

data class LobbyState(
    val player: Player? = null,
    val roomList: SnapshotStateList<Room> = mutableStateListOf(),
    val isLoading: Boolean = false
)
