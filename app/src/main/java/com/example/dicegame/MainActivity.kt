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
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.max

class MainActivity : ComponentActivity() {
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
    val noOfRounds = 3  //should stay 3 till I find a better way

    var currentScore by remember {
        mutableIntStateOf(0)
    }

    var highScore by rememberSaveable {
        mutableIntStateOf(0)
    }

    var rollResult by remember {
        mutableIntStateOf(6)
    }

    val rollEnabled = remember {
        mutableStateListOf(true, true, true)
    }

    var allRollsCompleted by remember {
        mutableStateOf(false)
    }

    val scoreType = if (allRollsCompleted) "Final" else "Current"

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
                    rollEnabled.fill(true)
                    highScore = max(highScore, currentScore)
                    currentScore = 0
                    allRollsCompleted = false
                },
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
                text = "High Score: $highScore",
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "$scoreType Score: $currentScore",
                fontSize = 28.sp,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = currentImage),
                contentDescription = rollResult.toString()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                for (i in (0..<noOfRounds)) {
                    Button(
                        onClick = {
                            rollResult = (1..6).random()    //pick a random number
                            currentScore += rollResult  //update current score
                            rollEnabled[i] = false  //disable button after use

                            //after last round
                            if (i == noOfRounds - 1) {
                                allRollsCompleted = true
                                highScore = max(highScore, currentScore)
                            }
                        },
                        enabled = rollEnabled[i]
                    ) {
                        Text(text = "Roll ${i + 1}", fontSize = 20.sp)
                    }
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