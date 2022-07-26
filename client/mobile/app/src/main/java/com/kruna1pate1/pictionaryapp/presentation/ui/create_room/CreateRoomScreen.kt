package com.kruna1pate1.pictionaryapp.presentation.ui.create_room

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kruna1pate1.pictionaryapp.presentation.ui.component.InputField
import com.kruna1pate1.pictionaryapp.presentation.ui.component.MyButton
import com.kruna1pate1.pictionaryapp.presentation.ui.theme.PictionaryTheme

@Composable
fun CreateRoomScreen(
    viewModel: CreateRoomViewModel
) {

    val state = viewModel.state.value

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text(
                text = "Create New Room",
                style = MaterialTheme.typography.h3,
            )

            Spacer(modifier = Modifier.height(40.dp))

            InputField(
                value = state.name,
                onValueChange = viewModel::onNameChange,
                hint = "Room Name"
            )

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(40.dp),
            ) {
                Text(
                    text = "Capacity :",
                    style = MaterialTheme.typography.subtitle1
                )
                InputField(
                    modifier = Modifier.width(80.dp),
                    value = state.capacity,
                    onValueChange = viewModel::onCapacityChange,
                    textAlign = TextAlign.Center,
                    keyboardType = KeyboardType.Decimal
                )
            }

            Spacer(modifier = Modifier.height(50.dp))

            MyButton(
                text = "Create",
                onClick = viewModel::createRoom
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun DefaultPreview() {
    PictionaryTheme {
//        CreateRoomScreen()
    }
}
