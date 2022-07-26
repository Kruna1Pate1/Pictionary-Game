package com.kruna1pate1.pictionaryapp.presentation.ui.game.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp

@Composable
fun MessageSendBar(
    value: String,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {

        TextField(
            modifier = Modifier
                .weight(1f)
                .padding(0.dp),
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = "Type something...",
                    color = Color.LightGray,
                    fontStyle = FontStyle.Italic
                )
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words
            )
        )

        IconButton(onClick = onSend) {
            Icon(
                Icons.Filled.Send,
                contentDescription = "send",
                tint = Color.LightGray,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}