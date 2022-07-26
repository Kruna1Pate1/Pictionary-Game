package com.kruna1pate1.pictionaryapp.presentation.ui.lobby.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.kruna1pate1.pictionaryapp.domain.model.Room

@Composable
fun RoomDetailText(room: Room) {
    Column {
        Text(
            text = room.name,
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.onPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(buildAnnotatedString {
            withStyle(style = MaterialTheme.typography.body2.toSpanStyle(),) {
                append("Room Code :  ")
            }
            withStyle(style = MaterialTheme.typography.body2.toSpanStyle()) {
                append(room.roomId)
            }
        })

        Text(buildAnnotatedString {
            withStyle(style = MaterialTheme.typography.body2.toSpanStyle()) {
                append("Capacity :  ")
            }
            withStyle(style = MaterialTheme.typography.body2.toSpanStyle()) {
                append("${room.playerCount} / ${room.capacity}")
            }
        })

        Text(buildAnnotatedString {
            withStyle(style = MaterialTheme.typography.body2.toSpanStyle()) {
                append("Status :  ")
            }
            withStyle(style = MaterialTheme.typography.body2.toSpanStyle()) {
                append(room.status.name)
            }
        })
    }
}