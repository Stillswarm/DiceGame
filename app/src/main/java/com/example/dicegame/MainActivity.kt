package com.example.dicegame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
                MainScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var currentScore by remember {
        mutableIntStateOf(0)
    }

    var rollResult by remember {
        mutableIntStateOf(6)
    }

    var roll1enabled by remember {
        mutableStateOf(true)
    }

    var roll2enabled by remember {
        mutableStateOf(true)
    }

    var roll3enabled by remember {
        mutableStateOf(true)
    }

    var rollsCompleted by remember {
        mutableStateOf(false)
    }

    val scoreType = if (rollsCompleted) "Final" else "Current"

    val currentImage = when (rollResult) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }

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
                onClick = {
                    roll1enabled = true
                    roll2enabled = true
                    roll3enabled = true
                    currentScore = 0
                    rollsCompleted = false
                },
                icon = { Icon(Icons.Filled.Refresh, "Extended floating action button.") },
                text = { Text(text = "New Game") },
                modifier = Modifier.padding(bottom = 36.dp)
            )
        },

        floatingActionButtonPosition = FabPosition.Center
    ) {
        Column(
            modifier = modifier.padding(it).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "$scoreType Score: $currentScore",
                fontSize = 24.sp,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = currentImage),
                contentDescription = rollResult.toString()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = {
                        rollResult = (1..6).random()    //pick a random number
                        currentScore += rollResult  //update current score
                        roll1enabled = false    //to disable button after use
                    },
                    enabled = roll1enabled
                ) {
                    Text(text = "Roll 1", fontSize = 20.sp)
                }

                Button(
                    onClick = {
                        rollResult = (1..6).random()
                        currentScore += rollResult
                        roll2enabled = false
                    },
                    enabled = roll2enabled
                ) {
                    Text(text = "Roll 2", fontSize = 20.sp)
                }

                Button(
                    onClick = {
                        rollResult = (1..6).random()
                        currentScore += rollResult
                        roll3enabled = false
                        rollsCompleted = true   //all rolls are over
                    },
                    enabled = roll3enabled
                ) {
                    Text(text = "Roll 3", fontSize = 20.sp)
                }
            }
        }
    }
}

@Preview
@Composable
private fun MainScreenPreview() {
    MainScreen()
}