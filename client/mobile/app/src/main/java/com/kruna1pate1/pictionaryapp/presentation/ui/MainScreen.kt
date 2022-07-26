package com.kruna1pate1.pictionaryapp.presentation.ui

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.kruna1pate1.pictionaryapp.presentation.ui.navigation.NavigationManager
import com.kruna1pate1.pictionaryapp.presentation.ui.navigation.SetUpNavGraph
import com.kruna1pate1.pictionaryapp.presentation.ui.theme.PictionaryTheme
import com.kruna1pate1.pictionaryapp.util.Constants.TAG

@Composable
fun MainScreen(navigationManager: NavigationManager) {

    PictionaryTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            val navController = rememberNavController()
            SetUpNavGraph(navController = navController)

            LaunchedEffect(navigationManager.commands) {
                navigationManager.commands.collect { command ->
                    if (command.route.isNotEmpty()) {
                        navController.navigate(command.route)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PictionaryTheme {
//        MainScreen(navigationManager = NavigationManager())
    }
}
