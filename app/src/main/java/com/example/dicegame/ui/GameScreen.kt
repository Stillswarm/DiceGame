package com.example.dicegame.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
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
                modifier = Modifier.padding(bottom = 36.dp),
                containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                contentColor = MaterialTheme.colorScheme.primary
            )
        },

        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->

        Box(
            modifier = modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                TurnsSelection(
                    onSelectionChange = { gameViewModel.updateMaxTurns(it) },
                    gameUiState = gameUiState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Start)
                )

                Text(
                    text = "High Score: ${gameUiState.highScore}",
                    fontSize = 24.sp
                )

                Text(
                    text = "${gameUiState.currentScoreLabel} Score: ${gameUiState.currentScore}",
                    fontSize = 28.sp,
                )

                Image(
                    painter = painterResource(id = gameUiState.currentImage),
                    contentDescription = null
                )


                Button(
                    onClick = gameViewModel::rollDice,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    enabled = !gameViewModel.allRollsCompleted()
                ) {
                    Text(
                        text = gameUiState.buttonText,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}

@Composable
fun TurnsSelection(
    onSelectionChange: (Int) -> Unit,
    gameUiState: GameUiState,
    modifier: Modifier = Modifier
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    Row(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .padding(bottom = 28.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Max no. of turns:  ",
            fontSize = 18.sp
        )
        
        Column {
            OutlinedButton(
                onClick = { expanded = !expanded },
                shape = RectangleShape,
                border = BorderStroke(
                    0.5.dp,
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                ),
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = gameUiState.maxNoOfTurns.toString())

                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "Dropdown Menu Icon"
                    )
                }
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                for (i in 1..10) {
                    DropdownMenuItem(
                        text = { Text(text = i.toString()) },
                        onClick = {
                            onSelectionChange(i)
                            expanded = false
                        }
                    )
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