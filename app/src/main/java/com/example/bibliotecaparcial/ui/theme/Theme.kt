package com.example.bibliotecaparcial.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Define tus colores personalizados aquí
val Brown700 = Color(0xFF4E3B31)
val Brown500 = Color(0xFF7C5C4B)
val Beige200 = Color(0xFFE4CFC0)
val Beige500 = Color(0xFFC9B09E)

@Composable
fun BibliotecaParcialTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme( // o darkColorScheme() según lo necesites
            primary = Brown500,
            secondary = Beige200,
            background = Beige500,
            surface = Color.White,
            onPrimary = Color.White,
            onSecondary = Color.Black,
            onBackground = Color.Black,
            onSurface = Color.Black
        ),
        content = content
    )
}
