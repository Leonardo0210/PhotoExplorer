package com.example.photoexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.photoexplorer.ui.screens.PhotoScreen
import com.example.photoexplorer.ui.theme.PhotoExplorerTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PhotoExplorerTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    PhotoScreen()
                }
            }
        }
    }
}
