package com.anwar.phiplayground.llm.feature_generation.domain.usecases

import com.anwar.phiplayground.llm.feature_generation.data.GenerationRepository
import com.anwar.phiplayground.llm.feature_generation.data.IGenerationRepository
import kotlinx.coroutines.flow.Flow

class GenerateResponseUseCase(private val repository: IGenerationRepository = GenerationRepository()) {

    /**
     * Invokes the use case to generate a response.
     */
    operator fun invoke(prompt: String, temperature: Float, maxTokens: Int): Flow<Result<String>> {
        return repository.generateResponse(prompt, temperature, maxTokens)
    }
}