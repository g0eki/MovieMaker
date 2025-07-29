package com.firstapp.moviemaker

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import com.firstapp.moviemaker.ui.elements.Navigation
import com.firstapp.moviemaker.ui.elements.TopBar
import com.firstapp.moviemaker.ui.theme.MovieMakerTheme

var cnt = mutableIntStateOf(1)

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MovieMakerTheme {
                Scaffold(
                    topBar = { TopBar() }
                ) { padding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding), // padding durch scaffold vorgegeben
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Log.i("MainActivity", "MainActivity: ${cnt.value}")
                        Navigation()
                        cnt.value = cnt.value + 1
                    }
                }
            }
        }
    }
}