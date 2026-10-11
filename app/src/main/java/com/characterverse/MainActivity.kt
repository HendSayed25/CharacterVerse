package com.characterverse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.characterverse.presentation.CharactersScreen
import com.characterverse.ui.theme.CharacterVerseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CharacterVerseTheme {
                CharactersScreen()
            }
        }
    }
}
