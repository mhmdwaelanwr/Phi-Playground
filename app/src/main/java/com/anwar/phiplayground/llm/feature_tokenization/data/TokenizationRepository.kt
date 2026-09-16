package com.anwar.phiplayground.llm.feature_tokenization.data

import com.anwar.phiplayground.llm.core.model.TokenData
import com.anwar.phiplayground.llm.core.model.TokenType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface ITokenizationRepository {
    fun analyzeTextForTokens(text: String): Flow<List<TokenData>>
}

class TokenizationRepository : ITokenizationRepository {

    /**
     * Analyzes the text and returns a Flow of TokenData list with simulated syntax highlighting.
     */
    override fun analyzeTextForTokens(text: String): Flow<List<TokenData>> {
        if (text.isBlank()) {
            return flowOf(emptyList())
        }

        val tokenTypes = TokenType.values()
        val tokens = text.split(Regex("\\s+"))
            .filter { it.isNotEmpty() }
            .map {
                TokenData(
                    value = it,
                    id = it.hashCode(),
                    type = tokenTypes.random()
                )
            }
        return flowOf(tokens)
    }
}