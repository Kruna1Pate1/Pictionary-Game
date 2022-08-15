package com.kruna1pate1.pictionaryapp.presentation.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kruna1pate1.pictionaryapp.domain.model.Score
import com.kruna1pate1.pictionaryapp.presentation.ui.game.component.ScoreDialog
import com.kruna1pate1.pictionaryapp.presentation.ui.theme.PictionaryTheme

@Composable
fun ExitDialog(
    onExit: () -> Unit,
    onDismiss: () -> Unit
) {

    Dialog(onDismissRequest = onDismiss) {

        Card(
            backgroundColor = MaterialTheme.colors.surface,
            shape = RoundedCornerShape(5.dp)
        ) {

            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                Text(
                    text = "Do you want to exit ?",
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onPrimary,
                )

                Row(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    MyButton(
                        modifier = Modifier.width(110.dp),
                        text = "Cancel",
                        onClick = onDismiss
                    )

                    MyButton(
                        modifier = Modifier.width(110.dp),
                        text = "Exit",
                        isPrimary = false,
                        onClick = onExit
                    )

                }
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun ExitPreveiw() {
    PictionaryTheme {
        ExitDialog(onExit = {}, onDismiss = {})
    }
}