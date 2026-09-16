package com.anwar.phiplayground.llm.data.repositories

import androidx.compose.ui.graphics.Color
import com.anwar.phiplayground.llm.data.models.TokenData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlin.random.Random

class TokenizationRepository {

    /**
     * Analyzes the text and returns a Flow of TokenData list.
     * This is a dummy implementation.
     */
    fun analyzeTextForTokens(text: String): Flow<List<TokenData>> {
        if (text.isBlank()) {
            return flowOf(emptyList())
        }

        val tokens = text.split(Regex("\\s+"))
            .filter { it.isNotEmpty() }
            .map {
                TokenData(
                    value = it,
                    id = it.hashCode(),
                    color = Color(
                        red = Random.nextFloat(),
                        green = Random.nextFloat(),
                        blue = Random.nextFloat(),
                        alpha = 0.4f // Fixed alpha for readability
                    )
                )
            }
        return flowOf(tokens)
    }
}