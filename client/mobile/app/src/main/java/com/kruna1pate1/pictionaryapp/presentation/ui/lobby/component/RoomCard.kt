package com.kruna1pate1.pictionaryapp.presentation.ui.lobby.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kruna1pate1.pictionaryapp.R
import com.kruna1pate1.pictionaryapp.domain.model.Room

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RoomCard(
    room: Room,
    onClick: (Room) -> Unit
) {

    Card(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.surface,
        shape = RoundedCornerShape(5.dp),
        onClick = { onClick(room) }
    ) {
        Row(
            modifier = Modifier.padding(8.dp, 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(randomImage()),
                contentDescription = room.roomId,
                modifier = Modifier
                    .size(80.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))

            RoomDetailText(room)
        }
    }
}

private fun randomImage(): Int {
    val list = listOf(R.drawable.alps, R.drawable.avalanche, R.drawable.beach, R.drawable.swamp)

    return list.random()
}
