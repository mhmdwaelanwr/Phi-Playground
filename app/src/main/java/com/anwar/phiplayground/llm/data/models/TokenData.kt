package com.anwar.phiplayground.llm.data.models

import androidx.compose.ui.graphics.Color

/**
 * Represents a single token with its associated data.
 *
 * @param value The string value of the token.
 * @param id A unique identifier for the token.
 * @param color The background color to be used for the token's chip.
 */
data class TokenData(
    val value: String,
    val id: Int,
    val color: Color
)
