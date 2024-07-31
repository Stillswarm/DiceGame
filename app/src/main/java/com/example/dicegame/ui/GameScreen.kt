package com.example.dicegame.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dicegame.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    gameViewModel: GameViewModel = viewModel()
) {

    val gameUiState = gameViewModel.uiState.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.dice_game))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },

        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = gameViewModel::refreshGame,
                icon = { Icon(Icons.Filled.Refresh, "Extended floating action button.") },
                text = { Text(text = "New Game", fontSize = 20.sp) },
                modifier = Modifier.padding(bottom = 36.dp)
            )
        },

        floatingActionButtonPosition = FabPosition.Center
    ) {
        Column(
            modifier = modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "High Score: ${gameUiState.highScore}",
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "${gameUiState.currentScoreLabel} Score: ${gameUiState.currentScore}",
                fontSize = 28.sp,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = gameUiState.currentImage),
                contentDescription = null
            )

            if (!gameViewModel.allRollsCompleted()) {
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = gameViewModel::rollDice,
                ) {
                    Text(text = gameUiState.buttonText, style = MaterialTheme.typography.titleLarge)
                }
            }
        }
    }
}

@Preview
@Composable
private fun MainScreenPreview() {
    GameScreen()
}