package com.kruna1pate1.pictionaryapp.presentation.ui.lobby.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kruna1pate1.pictionaryapp.presentation.ui.component.InputField

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onRefresh: () -> Unit
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {

        InputField(
            modifier = Modifier.weight(1f),
            value = value,
            onValueChange = onValueChange,
            hint = "Search for room...",
            icon = {
                Icon(
                    Icons.Outlined.Search, contentDescription = "search",
                    tint = Color.LightGray
                )
            }
        )

        IconButton(onClick = onRefresh) {
            Icon(
                Icons.Filled.Refresh,
                contentDescription = "refresh",
                tint = Color.LightGray,
                modifier = Modifier.size(28.dp)
            )
        }

    }
}
