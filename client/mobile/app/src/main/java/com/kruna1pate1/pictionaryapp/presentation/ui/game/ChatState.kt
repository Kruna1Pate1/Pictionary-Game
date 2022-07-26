package com.kruna1pate1.pictionaryapp.presentation.ui.game

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.kruna1pate1.pictionaryapp.domain.model.Message

data class ChatState(
    val messageList: SnapshotStateList<Message> = mutableStateListOf(),
    val isLoading: Boolean = false
)
