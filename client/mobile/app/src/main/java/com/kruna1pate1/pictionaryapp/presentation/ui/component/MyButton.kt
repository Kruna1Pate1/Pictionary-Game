package com.kruna1pate1.pictionaryapp.presentation.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun MyButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    isPrimary: Boolean = true,
    onClick: () -> Unit
) {

    Button(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(5.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (isPrimary) MaterialTheme.colors.surface else MaterialTheme.colors.secondary),
        contentPadding = PaddingValues(20.dp, 10.dp),
    ) {
        text?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.button,
                color = if (isPrimary) MaterialTheme.colors.primary else MaterialTheme.colors.onPrimary,
                textAlign = TextAlign.Center
            )
        }
    }
}