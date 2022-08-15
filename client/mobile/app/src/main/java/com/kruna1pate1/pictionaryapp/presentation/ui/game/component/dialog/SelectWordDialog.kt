package com.kruna1pate1.pictionaryapp.presentation.ui.game.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kruna1pate1.pictionaryapp.presentation.ui.theme.PictionaryTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SelectWordDialog(
    wordList: List<String>?,
    totalTime: Float,
    selectWord: (Int) -> Unit,
    onDismiss: () -> Unit
) {

    Dialog(onDismissRequest = onDismiss) {

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TimeBar(
                totalTime = totalTime,
                inactiveBarColor = MaterialTheme.colors.surface,
                activeBarColor = MaterialTheme.colors.primary
            )

            Spacer(modifier = Modifier.height(18.dp))

            wordList?.forEachIndexed { i, word ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    backgroundColor = MaterialTheme.colors.surface,
                    shape = RoundedCornerShape(5.dp),
                    onClick = { selectWord(i) }) {
                    Text(
                        modifier = Modifier
                        .padding(10.dp),
                        text = word,
                        style = MaterialTheme.typography.h5,
                        color = MaterialTheme.colors.onPrimary)
                }
            }
        }

    }
}

@Preview(showSystemUi = true)
@Composable
fun SelectWordPreveiw() {
    PictionaryTheme {
        SelectWordDialog(
            wordList = listOf("One", "Two", "Three"),
            totalTime = 15F,
            selectWord = {}) {

        }
    }
}
