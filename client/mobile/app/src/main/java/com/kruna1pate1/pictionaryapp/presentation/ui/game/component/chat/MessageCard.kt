package com.kruna1pate1.pictionaryapp.presentation.ui.game.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kruna1pate1.pictionaryapp.domain.model.Message
import com.kruna1pate1.pictionaryapp.domain.model.Player
import com.kruna1pate1.pictionaryapp.domain.model.enums.PlayerStatus
import com.kruna1pate1.pictionaryapp.presentation.ui.theme.PictionaryTheme

@Composable
fun MessageCard(
    msg: Message
) {

    Card(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .height(65.dp),
        backgroundColor = MaterialTheme.colors.surface
    ) {
       Row(
           modifier = Modifier.padding(8.dp, 8.dp),
           verticalAlignment = Alignment.Bottom
       ) {

           Column(
               modifier = Modifier
                   .fillMaxHeight()
                   .weight(1f)
               ,
               verticalArrangement = Arrangement.SpaceBetween,

           ) {
               Text(text = msg.player.name,
                   style = MaterialTheme.typography.subtitle1,
                   color = MaterialTheme.colors.onPrimary,
               )

               Text(text = msg.body,
                   style = MaterialTheme.typography.body2,
                   color = MaterialTheme.colors.onSurface,)
           }
           
           Text(text = msg.time,
               style = MaterialTheme.typography.body1,
               color = MaterialTheme.colors.onSurface,)

       }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    PictionaryTheme {
        MessageCard(msg = Message(Player(1, "Krunal", PlayerStatus.GUESSING), "Sun flower", "12:59:00"))
    }
}