package com.anwar.phiplayground.llm.feature_generation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.anwar.phiplayground.llm.feature_generation.viewmodel.GenerationUiState
import com.anwar.phiplayground.llm.feature_generation.viewmodel.GenerationViewModel
import com.anwar.phiplayground.llm.ui.components.AdvancedSlider
import com.anwar.phiplayground.llm.ui.theme.PhiPlaygroundTheme
import java.text.DecimalFormat

@Composable
fun GenerationScreen(viewModel: GenerationViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    // Listen for snackbar events
    LaunchedEffect(Unit) {
        viewModel.snackbarEvents.collect { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { if (!uiState.isLoading) viewModel.generateResponse() },
            ) {
                if (uiState.isLoading) {
                    Icon(Icons.Filled.Autorenew, contentDescription = "Loading")
                } else {
                    Icon(Icons.Filled.Send, contentDescription = "Generate")
                }
            }
        }
    ) { paddingValues ->
        GenerationContent(
            modifier = Modifier.padding(paddingValues),
            uiState = uiState,
            onPromptChange = viewModel::onPromptChange,
            onTemperatureChange = viewModel::onTemperatureChange,
            onMaxTokensChange = { viewModel.onMaxTokensChange(it.toInt()) },
            onHistoryItemClick = viewModel::onHistoryItemClick
        )
    }
}

@Composable
fun GenerationContent(
    modifier: Modifier = Modifier,
    uiState: GenerationUiState,
    onPromptChange: (String) -> Unit,
    onTemperatureChange: (Float) -> Unit,
    onMaxTokensChange: (Float) -> Unit,
    onHistoryItemClick: (String) -> Unit
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        OutlinedTextField(
            value = uiState.prompt,
            onValueChange = onPromptChange,
            label = { Text("أدخل المطالبة هنا") },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        AdvancedSlider(
            label = "Temperature",
            value = uiState.temperature,
            onValueChange = onTemperatureChange,
            valueRange = 0.0f..1.0f,
            formatter = DecimalFormat("0.0")
        )

        AdvancedSlider(
            label = "Max Tokens",
            value = uiState.maxTokens.toFloat(),
            onValueChange = onMaxTokensChange,
            valueRange = 50f..512f,
            steps = (512 - 50) / 10
        )

        if (uiState.streamingText.isNotBlank()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text("الرد:", fontWeight = FontWeight.Bold)
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(2.dp),
                colors = CardDefaults.cardColors(containerColor = Color.DarkGray.copy(alpha = 0.2f))
            ) {
                Text(
                    text = uiState.streamingText,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        if (uiState.promptHistory.isNotEmpty()) {
            Spacer(modifier = Modifier.height(24.dp))
            Text("آخر المطالبات:", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(uiState.promptHistory) { prompt ->
                    Text(
                        text = prompt,
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.LightGray.copy(alpha = 0.5f))
                            .clickable { onHistoryItemClick(prompt) }
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        maxLines = 1,
                        color = Color.Black
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(80.dp)) // Spacer for FAB
    }
}


@Preview(showBackground = true)
@Composable
fun InteractiveGenerationScreenPreview() {
    PhiPlaygroundTheme {
        GenerationContent(
            uiState = GenerationUiState(
                isLoading = false,
                streamingText = "هذا هو الرد المتدفق من النموذج...",
                promptHistory = listOf("اكتب قصة قصيرة", "ما هو الذكاء الاصطناعي؟")
            ),
            onPromptChange = {},
            onTemperatureChange = {},
            onMaxTokensChange = {},
            onHistoryItemClick = {}
        )
    }
}