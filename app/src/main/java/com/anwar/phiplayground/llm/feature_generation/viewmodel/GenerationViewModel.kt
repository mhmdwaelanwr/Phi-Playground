package com.anwar.phiplayground.llm.feature_generation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anwar.phiplayground.llm.feature_generation.data.GenerationRepository
import com.anwar.phiplayground.llm.feature_generation.data.IGenerationRepository
import com.anwar.phiplayground.llm.feature_generation.domain.usecases.GenerateResponseUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class GenerationUiState(
    val prompt: String = "اكتب قصة قصيرة عن رائد فضاء، ولكن اجعلها تفشل في المنتصف عن طريق كتابة fail_mid",
    val temperature: Float = 0.7f,
    val maxTokens: Int = 256,
    val streamingText: String = "",
    val isLoading: Boolean = false,
    val promptHistory: List<String> = emptyList()
)

class GenerationViewModel(
    private val generateResponseUseCase: GenerateResponseUseCase = GenerateResponseUseCase()
) : ViewModel() {

    private val _uiState = MutableStateFlow(GenerationUiState())
    val uiState: StateFlow<GenerationUiState> = _uiState.asStateFlow()

    // A flow for one-off events like showing a Snackbar
    private val _snackbarEvents = MutableSharedFlow<String>()
    val snackbarEvents: SharedFlow<String> = _snackbarEvents.asSharedFlow()

    fun onPromptChange(newPrompt: String) {
        _uiState.update { it.copy(prompt = newPrompt) }
    }

    fun onTemperatureChange(newTemp: Float) {
        _uiState.update { it.copy(temperature = newTemp) }
    }

    fun onMaxTokensChange(newMaxTokens: Int) {
        _uiState.update { it.copy(maxTokens = newMaxTokens) }
    }

    fun onHistoryItemClick(prompt: String) {
        _uiState.update { it.copy(prompt = prompt) }
    }

    fun generateResponse() {
        viewModelScope.launch {
            val currentState = _uiState.value
            val currentPrompt = currentState.prompt

            updateHistory(currentPrompt)

            generateResponseUseCase(currentPrompt, currentState.temperature, currentState.maxTokens)
                .onStart {
                    _uiState.update { it.copy(isLoading = true, streamingText = "") }
                }
                .onCompletion {
                    _uiState.update { it.copy(isLoading = false) }
                }
                .collect { result ->
                    result.onSuccess { chunk ->
                        _uiState.update { it.copy(streamingText = it.streamingText + chunk) }
                    }.onFailure { error ->
                        _snackbarEvents.emit(error.message ?: "حدث خطأ غير متوقع")
                    }
                }
        }
    }

    private fun updateHistory(prompt: String) {
        if (prompt.isNotBlank() && _uiState.value.promptHistory.firstOrNull() != prompt) {
            val updatedHistory = (listOf(prompt) + _uiState.value.promptHistory).take(3)
            _uiState.update { it.copy(promptHistory = updatedHistory) }
        }
    }
}