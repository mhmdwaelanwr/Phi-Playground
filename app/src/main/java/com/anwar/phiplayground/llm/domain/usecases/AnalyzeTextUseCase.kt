package com.anwar.phiplayground.llm.domain.usecases

import com.anwar.phiplayground.llm.data.models.TokenData
import com.anwar.phiplayground.llm.data.repositories.TokenizationRepository
import kotlinx.coroutines.flow.Flow

class AnalyzeTextUseCase(private val repository: TokenizationRepository = TokenizationRepository()) {

    /**
     * Invokes the use case to analyze the given text.
     */
    operator fun invoke(text: String): Flow<List<TokenData>> {
        return repository.analyzeTextForTokens(text)
    }
}