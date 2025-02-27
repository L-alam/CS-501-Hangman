package com.example.hangman

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

// fix keyboard
// fix hint implementation
// customize board and change look


@Composable
fun HangmanGame(){
    val words = listOf("BOSTON", "CAMBRIDGE", "CHICAGO", "MANHATTAN")
    var selectedWord by remember { mutableStateOf(words.random()) }
    var guessedLetters by remember { mutableStateOf(setOf<Char>()) }
    var incorrectGuesses by remember { mutableStateOf(0) }
    var hintClicks by remember { mutableStateOf(0) }
    val maxIncorrectGuesses = 6

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        Row(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                HangmanDisplay(incorrectGuesses)
                WordDisplay(selectedWord, guessedLetters)
            }
            Column(modifier = Modifier.weight(1f)) {
                Keyboard(selectedWord, guessedLetters, onLetterClick = { letter ->
                    if (letter in selectedWord) guessedLetters += letter else incorrectGuesses++
                })
                HintPanel(hintClicks, guessedLetters, selectedWord, onHintUsed = {
                    hintClicks++
                    if (hintClicks == 2 || hintClicks == 3) incorrectGuesses++
                })
            }
        }
    } else {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            HangmanDisplay(incorrectGuesses)
            WordDisplay(selectedWord, guessedLetters)
            Keyboard(selectedWord, guessedLetters, onLetterClick = { letter ->
                if (letter in selectedWord) guessedLetters += letter else incorrectGuesses++
            })
        }
    }

    // New Game Button
    Button(
        onClick = {
            selectedWord = words.random()
            guessedLetters = setOf()
            incorrectGuesses = 0
            hintClicks = 0
        },
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp)),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black,  // Black background
            contentColor = Color.White  // White text
        )
    ) {
        Text(
            text = "New Game",
            fontSize = 18.sp,
            color = Color.White  // Explicitly set white text
        )
    }

}

@Composable
fun HangmanDisplay(incorrectGuesses: Int) {
    val hangmanImages = listOf(
        R.drawable.hangman0, // Starting image (no incorrect guesses)
        R.drawable.hangman1,
        R.drawable.hangman2,
        R.drawable.hangman3,
        R.drawable.hangman4,
        R.drawable.hangman5,
        R.drawable.hangman6  // Final image (user loses)
    )

    Image(
        painter = painterResource(id = hangmanImages[incorrectGuesses]),
        contentDescription = "Hangman Stage",
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(16.dp)
    )
}

@Composable
fun WordDisplay(selectedWord: String, guessedLetters: Set<Char>) {
    val displayedWord = selectedWord.uppercase().map { letter ->
        if (letter in guessedLetters) letter else '_'
    }.joinToString(" ")

    Text(
        text = displayedWord,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

