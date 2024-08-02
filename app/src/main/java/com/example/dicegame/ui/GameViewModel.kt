package com.example.dicegame.ui

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import com.example.dicegame.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.max

class GameViewModel : ViewModel() {
    private var _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private var lastResult = 6

    fun rollDice() {
        val rollResult = pickNextValue()
        val newImage = getNewImage(rollResult)
        _uiState.update {
            it.copy(
                currentScore = uiState.value.currentScore + rollResult,
                currentImage = newImage,
                rollNo = _uiState.value.rollNo.inc(),
                buttonText = "Roll ${uiState.value.rollNo + 1}"
            )
        }

        if (allRollsCompleted()) {
            onGameOver()
        }
    }

    private fun pickNextValue(): Int {
        var result = (1..6).random()
        while (result == lastResult) {
            result = (1..6).random()
        }

        lastResult = result
        return result
    }

    private fun getNewImage(result: Int): Int {
        assert(result in 1..6)

        return when (result) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            6 -> R.drawable.dice_6

            else -> R.drawable.dice_6
        }
    }

    private fun onGameOver() {
        _uiState.update {
            it.copy(
                highScore = max(uiState.value.highScore, uiState.value.currentScore),
                buttonText = "Game Over!",
                currentScoreLabel = "Final"
            )
        }
    }

    fun allRollsCompleted(): Boolean {
        return uiState.value.rollNo > uiState.value.maxNoOfTurns
    }

    fun updateMaxTurns(newVal: Int) {
        _uiState.update {
            it.copy(maxNoOfTurns = newVal)
        }
    }

    fun refreshGame() {
        _uiState.update {
            it.copy(
                highScore = max(uiState.value.highScore, _uiState.value.currentScore),
                currentScore = 0,
                rollNo = 1,
                currentImage = R.drawable.dice_6,
                buttonText = "Roll 1",
                currentScoreLabel = "Current"
            )
        }
    }

    init {
        refreshGame()
    }
}

data class GameUiState(
    val currentScore: Int = 0,
    val highScore: Int = 0,
    val rollNo: Int = 1,
    @DrawableRes val currentImage: Int = R.drawable.dice_6,
    val buttonText: String = "Roll 1",
    val currentScoreLabel: String = "Current",
    val maxNoOfTurns: Int = 3
)