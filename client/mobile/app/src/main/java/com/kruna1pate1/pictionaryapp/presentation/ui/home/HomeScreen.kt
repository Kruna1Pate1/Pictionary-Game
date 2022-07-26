package com.kruna1pate1.pictionaryapp.presentation.ui.home

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kruna1pate1.pictionaryapp.presentation.ui.component.InputField
import com.kruna1pate1.pictionaryapp.presentation.ui.component.MyButton
import com.kruna1pate1.pictionaryapp.presentation.ui.theme.PictionaryTheme
import com.kruna1pate1.pictionaryapp.util.Constants.TAG

@Composable
fun HomeScreen(
    viewModel: HomeViewModel
) {

    LaunchedEffect(true) {
        viewModel.getPlayer()
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {

        if (viewModel.state.value.isLoading) {
            CircularProgressIndicator()
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Pick Your Name",
                style = MaterialTheme.typography.h3,
            )

            Spacer(modifier = Modifier.height(40.dp))

            InputField(
                value = viewModel.name.value,
                onValueChange = viewModel::onNameChange,
                hint = "Harry Potter"
            )

            Spacer(modifier = Modifier.height(50.dp))

            MyButton(
                text = "Let's Go",
            onClick = viewModel::onClick
            )
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun DefaultPreview() {
    PictionaryTheme {
//        HomeScreen()
    }
}