package com.anwar.phiplayground.llm.feature_tokenization.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.anwar.phiplayground.llm.core.model.TokenData
import com.anwar.phiplayground.llm.core.model.TokenType
import com.anwar.phiplayground.llm.feature_tokenization.viewmodel.TokenizationScreenState
import com.anwar.phiplayground.llm.feature_tokenization.viewmodel.TokenizationUiState
import com.anwar.phiplayground.llm.feature_tokenization.viewmodel.TokenizationViewModel
import com.anwar.phiplayground.llm.ui.theme.PhiPlaygroundTheme

@Composable
fun TokenizationScreen(viewModel: TokenizationViewModel = viewModel()) {
    val screenState = viewModel.screenState
    val uiState by screenState.uiState.collectAsState()
    val tokens by viewModel.tokens.collectAsState()

    if (uiState.selectedToken != null) {
        TokenInfoDialog(
            token = uiState.selectedToken!!,
            onDismiss = viewModel::dismissTokenDialog
        )
    }

    TokenizationContent(
        userInput = uiState.userInput,
        tokens = tokens, // Use the debounced tokens
        tokenCount = tokens.size,
        onUserInputChange = viewModel::onUserInputChange,
        onTokenClick = viewModel::onTokenClicked
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TokenizationContent(
    userInput: String,
    tokens: List<TokenData>,
    tokenCount: Int,
    onUserInputChange: (String) -> Unit,
    onTokenClick: (TokenData) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = userInput,
            onValueChange = onUserInputChange,
            label = { Text("أدخل النص هنا وانتظر...") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("إجمالي التوكينات: $tokenCount")
        Spacer(modifier = Modifier.height(8.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            tokens.forEach { token ->
                Text(
                    text = token.value,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(token.type.color)
                        .clickable { onTokenClick(token) }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }
    }
}

@Composable
fun TokenInfoDialog(token: TokenData, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "تفاصيل التوكين: ${token.value}") },
        text = {
            Column {
                Text("المعرف (ID): ${token.id}")
                Text("النوع: ${token.type.name}")
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("حسناً")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun TokenizationScreenPreview() {
    PhiPlaygroundTheme {
        val dummyTokens = listOf(
            TokenData("val", 1, TokenType.KEYWORD),
            TokenData("x", 2, TokenType.VARIABLE),
            TokenData("=", 3, TokenType.DEFAULT),
            TokenData("\"Hello\"", 4, TokenType.LITERAL)
        )
        TokenizationContent(
            userInput = "val x = \"Hello\"",
            tokens = dummyTokens,
            tokenCount = 4,
            onUserInputChange = {},
            onTokenClick = {}
        )
    }
}