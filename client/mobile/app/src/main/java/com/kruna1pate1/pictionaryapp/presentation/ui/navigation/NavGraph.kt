package com.kruna1pate1.pictionaryapp.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kruna1pate1.pictionaryapp.presentation.ui.create_room.CreateRoomScreen
import com.kruna1pate1.pictionaryapp.presentation.ui.create_room.CreateRoomViewModel
import com.kruna1pate1.pictionaryapp.presentation.ui.game.GameScreen
import com.kruna1pate1.pictionaryapp.presentation.ui.game.GameViewModel
import com.kruna1pate1.pictionaryapp.presentation.ui.home.HomeScreen
import com.kruna1pate1.pictionaryapp.presentation.ui.home.HomeViewModel
import com.kruna1pate1.pictionaryapp.presentation.ui.lobby.LobbyScreen
import com.kruna1pate1.pictionaryapp.presentation.ui.lobby.LobbyViewModel
import com.kruna1pate1.pictionaryapp.presentation.ui.navigation.direction.Direction

@Composable
fun SetUpNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Direction.Home.route
    ) {

        composable(
            route = Direction.Home.route,
            arguments = Direction.Home.arguments
        ) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            HomeScreen(homeViewModel)
        }

        composable(
            route = Direction.CreateRoom.route,
            arguments = Direction.CreateRoom.arguments
        ) {
            val createRoomViewModel: CreateRoomViewModel = hiltViewModel()
            CreateRoomScreen(createRoomViewModel)
        }

        composable(
            route = Direction.JoinRoom.route,
            arguments = Direction.JoinRoom.arguments
        ) {

        }

        composable(
            route = Direction.Lobby.route,
            arguments = Direction.Lobby.arguments
        ) {
            val lobbyViewModel: LobbyViewModel = hiltViewModel()
            LobbyScreen(lobbyViewModel)
        }

        composable(
            route = Direction.Game.route,
            arguments = Direction.Game.arguments
        ) {
            val gameViewModel: GameViewModel = hiltViewModel()
            GameScreen(gameViewModel)

        }
    }
}
