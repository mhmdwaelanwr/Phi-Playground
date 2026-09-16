package com.anwar.phiplayground.llm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anwar.phiplayground.llm.feature_generation.ui.GenerationScreen
import com.anwar.phiplayground.llm.feature_tokenization.ui.TokenizationScreen
import com.anwar.phiplayground.llm.ui.theme.PhiPlaygroundTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PhiPlaygroundTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    PhiPlaygroundNavGraph()
                }
            }
        }
    }
}

@Composable
fun PhiPlaygroundNavGraph() {
    val navController = rememberNavController()
    Scaffold {
        NavHost(
            navController = navController,
            startDestination = "main_menu",
            modifier = Modifier.padding(it)
        ) {
            composable("main_menu") {
                MainMenuScreen(navController = navController)
            }
            composable("tokenization") {
                TokenizationScreen()
            }
            composable("generation") {
                GenerationScreen()
            }
        }
    }
}

@Composable
fun MainMenuScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { navController.navigate("tokenization") }) {
            Text("Advanced Tokenization Visualizer")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("generation") }) {
            Text("Advanced Interactive Generation")
        }
    }
}