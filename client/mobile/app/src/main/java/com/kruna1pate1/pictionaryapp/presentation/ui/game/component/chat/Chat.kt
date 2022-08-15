package com.kruna1pate1.pictionaryapp.presentation.ui.game.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kruna1pate1.pictionaryapp.domain.model.Message
import com.kruna1pate1.pictionaryapp.domain.model.Player
import com.kruna1pate1.pictionaryapp.domain.model.enums.PlayerStatus

@Composable
fun Chat(
    messageList: List<Message>,
    message: String,
    onMessageChange: (String) -> Unit,
    onSend: () -> Unit
) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(MaterialTheme.colors.surface)

    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            items(items = messageList) { msg ->
                MessageCard(msg = msg)
                Divider(color = Color.LightGray.copy(alpha = 0.5f), thickness = 0.3.dp)
            }
        }

    }

    MessageSendBar(
        value = message,
        onValueChange = onMessageChange,
        onSend = onSend
    )

}