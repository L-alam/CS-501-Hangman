package com.example.hangman

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Keyboard(
    selectedWord: String,
    guessedLetters: Set<Char>,
    onLetterClick: (Char) -> Unit
) {
    val letters = ('A'..'Z').toList()

    Column {
        for (row in letters.chunked(7)) { // Splitting buttons into rows
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                row.forEach { letter ->
                    LetterButton(letter, guessedLetters.contains(letter), onLetterClick)
                }
            }
        }
    }
}
