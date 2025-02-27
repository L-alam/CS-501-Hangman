package com.example.hangman

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast

@Composable
fun HintPanel(
    hintClicks: Int,
    guessedLetters: Set<Char>,
    selectedWord: String,
    onHintUsed: () -> Unit
) {
    val context = LocalContext.current
    Button(
        onClick = {
            if (hintClicks >= 3) {
                Toast.makeText(context, "Hint not available", Toast.LENGTH_SHORT).show()
            } else {
                onHintUsed()
            }
        },
        modifier = Modifier.padding(8.dp)
    ) {
        Text("Hint")
    }

    if (hintClicks == 1) {
        Text("Hint: The word relates to a common theme.")
    } else if (hintClicks == 2) {
        Text("Hint: Disabling half of the incorrect letters.")
    } else if (hintClicks == 3) {
        Text("Hint: Revealing vowels.")
    }
}

fun SecondHint(
    selectedWord: String,
    guessedLetters: Set<Char>,
    onUpdateGuessedLetters: (Set<Char>) -> Unit
) {
    val remainingIncorrectLetters = ('A'..'Z')
        .filter { it !in selectedWord && it !in guessedLetters }

    val lettersToDisable = remainingIncorrectLetters.shuffled().take(remainingIncorrectLetters.size / 2)

    onUpdateGuessedLetters(guessedLetters + lettersToDisable)
}

fun ThirdHint(
    selectedWord: String,
    guessedLetters: Set<Char>,
    onUpdateGuessedLetters: (Set<Char>) -> Unit
) {
    val vowels = setOf('A', 'E', 'I', 'O', 'U')
    val vowelsInWord = selectedWord.toSet().intersect(vowels)

    onUpdateGuessedLetters(guessedLetters + vowelsInWord)
}
