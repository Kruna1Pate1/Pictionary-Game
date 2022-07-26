package com.kruna1pate1.pictionaryapp.presentation.ui.navigation.direction

import androidx.navigation.NamedNavArgument

interface NavigationCommand {
    val name: String
    val route: String
    val arguments: List<NamedNavArgument>

    // Constant keys for accessing argument-value from
    // navBackStack
    companion object {
        const val KEY_ROOM_ID = "roomId"
        const val KEY_USER_ID = "userId"
    }
}
