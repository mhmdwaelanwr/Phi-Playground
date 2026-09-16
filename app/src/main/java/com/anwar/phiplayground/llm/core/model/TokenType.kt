package com.anwar.phiplayground.llm.core.model

import androidx.compose.ui.graphics.Color

/**
 * A simulated enum for token types for syntax highlighting.
 */
enum class TokenType(val color: Color) {
    KEYWORD(Color(0xFFCF86E8)), // Purple
    VARIABLE(Color(0xFF66D9EF)), // Cyan
    LITERAL(Color(0xFFE6DB74)), // Yellow
    COMMENT(Color.Gray),
    DEFAULT(Color.White)
}