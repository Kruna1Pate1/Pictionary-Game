package com.kruna1pate1.pictionaryapp.presentation.ui.navigation.direction

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.kruna1pate1.pictionaryapp.presentation.ui.navigation.direction.NavigationCommand.Companion.KEY_ROOM_ID

sealed interface Direction {

    object Home : NavigationCommand {

        override val name: String
            get() = "Home"

        override val route: String
            get() = "home_route"

        override val arguments: List<NamedNavArgument>
            get() = emptyList()
    }

    object CreateRoom : NavigationCommand {

        override val name: String
            get() = "Create Room"

        override val route: String
            get() = "create_room"

        override val arguments: List<NamedNavArgument>
            get() = emptyList()
    }

    object JoinRoom : NavigationCommand {

        override val name: String
            get() = "Join Room"

        override val route: String
            get() = "join_room"

        override val arguments: List<NamedNavArgument>
            get() = emptyList()
    }

    object Lobby : NavigationCommand {

        override val name: String
            get() = "Lobby"

        override val route: String
            get() = "lobby"

        override val arguments: List<NamedNavArgument>
            get() = emptyList()
    }

    object Game : NavigationCommand {

        override val name: String
            get() = "Game"

        override val route: String
            get() = "game/{$KEY_ROOM_ID}"

        override val arguments: List<NamedNavArgument>
            get() = listOf(
                navArgument(KEY_ROOM_ID) { type = NavType.StringType }
            )

        fun game(roomId: String) = object : NavigationCommand {
            override val name: String
                get() = this@Game.name

            override val route: String
                get() = "game/$roomId"

            override val arguments: List<NamedNavArgument>
                get() = this@Game.arguments

        }
    }
}
