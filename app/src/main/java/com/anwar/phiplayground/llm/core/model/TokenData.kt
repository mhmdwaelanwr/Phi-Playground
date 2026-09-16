package com.anwar.phiplayground.llm.core.model

/**
 * Represents a single token with its associated data for syntax highlighting.
 *
 * @param value The string value of the token.
 * @param id A unique identifier for the token.
 * @param type The simulated type of the token for highlighting.
 */
data class TokenData(
    val value: String,
    val id: Int,
    val type: TokenType
)
