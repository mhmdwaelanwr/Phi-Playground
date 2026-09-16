package com.anwar.phiplayground.llm.feature_tokenization.viewmodel

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anwar.phiplayground.llm.core.model.TokenData
import com.anwar.phiplayground.llm.feature_tokenization.data.ITokenizationRepository
import com.anwar.phiplayground.llm.feature_tokenization.data.TokenizationRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

@Stable
data class TokenizationUiState(
    val userInput: String = "أهلاً بك في عالم الذكاء الاصطناعي",
    val tokens: List<TokenData> = emptyList(),
    val selectedToken: TokenData? = null
) {
    val tokenCount: Int get() = tokens.size
}

// State Holder Class
@Stable
class TokenizationScreenState(val uiState: StateFlow<TokenizationUiState>) {
    // We can add presentation logic here in the future
}

@OptIn(ExperimentalCoroutinesApi::class)
class TokenizationViewModel(
    private val repository: ITokenizationRepository = TokenizationRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(TokenizationUiState())

    val screenState = TokenizationScreenState(
        uiState = _uiState.asStateFlow()
    )

    private val userInputFlow = MutableStateFlow(_uiState.value.userInput)

    val tokens: StateFlow<List<TokenData>> = userInputFlow
        .debounce(300L)
        .flatMapLatest { text ->
            repository.analyzeTextForTokens(text)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onUserInputChange(newText: String) {
        _uiState.update { it.copy(userInput = newText) }
        userInputFlow.value = newText
    }

    fun onTokenClicked(token: TokenData) {
        _uiState.update { it.copy(selectedToken = token) }
    }

    fun dismissTokenDialog() {
        _uiState.update { it.copy(selectedToken = null) }
    }
}