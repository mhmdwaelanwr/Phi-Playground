package com.anwar.phiplayground.llm.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anwar.phiplayground.llm.data.models.TokenData
import com.anwar.phiplayground.llm.domain.usecases.AnalyzeTextUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TokenizationUiState(
    val userInput: String = "أهلاً بك في عالم الذكاء الاصطناعي",
    val tokens: List<TokenData> = emptyList(),
    val selectedToken: TokenData? = null
) {
    val tokenCount: Int get() = tokens.size
}

class TokenizationViewModel(
    private val analyzeTextUseCase: AnalyzeTextUseCase = AnalyzeTextUseCase()
) : ViewModel() {

    private val _uiState = MutableStateFlow(TokenizationUiState())
    val uiState: StateFlow<TokenizationUiState> = _uiState.asStateFlow()

    init {
        // Initial analysis
        analyzeText()
    }

    fun onUserInputChange(newText: String) {
        _uiState.update { it.copy(userInput = newText) }
    }

    fun analyzeText() {
        viewModelScope.launch {
            analyzeTextUseCase(_uiState.value.userInput)
                .collect { newTokens ->
                    _uiState.update { it.copy(tokens = newTokens) }
                }
        }
    }

    fun onTokenClicked(token: TokenData) {
        _uiState.update { it.copy(selectedToken = token) }
    }

    fun dismissTokenDialog() {
        _uiState.update { it.copy(selectedToken = null) }
    }
}