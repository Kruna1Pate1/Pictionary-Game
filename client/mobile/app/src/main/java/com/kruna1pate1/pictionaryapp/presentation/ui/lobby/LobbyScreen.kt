package com.kruna1pate1.pictionaryapp.presentation.ui.lobby

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kruna1pate1.pictionaryapp.domain.model.Room
import com.kruna1pate1.pictionaryapp.presentation.ui.component.MyButton
import com.kruna1pate1.pictionaryapp.presentation.ui.lobby.component.RoomCard
import com.kruna1pate1.pictionaryapp.presentation.ui.lobby.component.SearchBar
import com.kruna1pate1.pictionaryapp.presentation.ui.theme.PictionaryTheme

@Composable
fun LobbyScreen(
    viewModel: LobbyViewModel
) {

    LaunchedEffect(true) {
        viewModel.init()
    }

    val roomList: List<Room> = viewModel.state.value.roomList

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(14.dp, 24.dp, 14.dp, 8.dp)
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            SearchBar(
                value = viewModel.searchQuery.value,
                onValueChange = viewModel::onSearch,
                onRefresh = viewModel::onRefresh
            )

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Available Rooms (${roomList.size})",
                style = MaterialTheme.typography.h5,
            )

            Spacer(modifier = Modifier.height(14.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                items(items = roomList) { room ->
                    RoomCard(room = room, onClick = viewModel::onJoinRoom)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            MyButton(
                text = "Create new room",
                onClick = viewModel::createRoom
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun DefaultPreview() {
    PictionaryTheme {
//        LobbyScreen()
    }
}
