package com.kruna1pate1.pictionaryapp.presentation.ui.navigation

import com.kruna1pate1.pictionaryapp.presentation.ui.navigation.direction.NavigationCommand
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class NavigationManager @Inject constructor(
    private val externalScope: CoroutineScope
) {

    var commands = MutableSharedFlow<NavigationCommand>()

    fun navigate(
        direction: NavigationCommand
    ) {
        externalScope.launch {
            commands.emit(direction)
        }
    }
}
