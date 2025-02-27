package com.example.hangman

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LetterButton(
    letter: Char,
    isDisabled: Boolean,
    onLetterClick: (Char) -> Unit
) {
    Button(
        onClick = { onLetterClick(letter) },
        enabled = !isDisabled,
        modifier = Modifier
            .padding(3.dp)
            .size(50.dp)
            .clip(RoundedCornerShape(8.dp)),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black,
            disabledContainerColor = Color.Gray
        )
    ) {
        Text(
            text = letter.toString(),
            fontSize = 20.sp,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}
