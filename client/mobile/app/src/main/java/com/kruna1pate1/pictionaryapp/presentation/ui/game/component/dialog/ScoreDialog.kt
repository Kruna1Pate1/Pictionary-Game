package com.kruna1pate1.pictionaryapp.presentation.ui.game.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kruna1pate1.pictionaryapp.domain.model.Player
import com.kruna1pate1.pictionaryapp.domain.model.Score
import com.kruna1pate1.pictionaryapp.domain.model.enums.PlayerStatus
import com.kruna1pate1.pictionaryapp.presentation.ui.theme.PictionaryTheme

@Composable
fun ScoreDialog(
    answer: String,
    score: Score,
    onDismiss: () -> Unit
) {

    Dialog(onDismissRequest = onDismiss) {

        Card(
            backgroundColor = MaterialTheme.colors.surface,
            shape = RoundedCornerShape(5.dp)
        ) {

            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Text(buildAnnotatedString {
                    withStyle(style = MaterialTheme.typography.h4.toSpanStyle()) {
                        append("Answer :  ")
                    }
                    withStyle(
                        style = MaterialTheme.typography.h4.toSpanStyle()
                            .copy(color = MaterialTheme.colors.primaryVariant)
                    ) {
                        append(answer)
                    }
                })

                Spacer(modifier = Modifier.height(8.dp))

                score.scores.forEach { (player, score) ->

                    Divider(color = Color.LightGray.copy(alpha = 0.5f), thickness = 0.3.dp)

                    Row(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(
                            text = player.name,
                            style = MaterialTheme.typography.subtitle1,
                            color = MaterialTheme.colors.onPrimary,
                        )

                        Text(
                            text = "$score",
                            style = MaterialTheme.typography.subtitle1,
                            color = MaterialTheme.colors.secondary,
                        )

                    }
                }
            }
        }

    }

}

@Preview(showSystemUi = true)
@Composable
fun ScorePreveiw() {
    PictionaryTheme {
        ScoreDialog(
            "Computer",
            score = Score(
                buildMap {
//                    put(Player(1, "Krunal", PlayerStatus.IDLE), 100)
//                    put(Player(2, "Harsh", PlayerStatus.IDLE), 200)
//                    put(Player(3, "Harshit", PlayerStatus.IDLE), 400)
//                    put(Player(4, "Ankur", PlayerStatus.IDLE), 530)
                }
            )
        ) {

        }
    }
}
