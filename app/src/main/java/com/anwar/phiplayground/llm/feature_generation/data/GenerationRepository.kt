package com.anwar.phiplayground.llm.feature_generation.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

interface IGenerationRepository {
    fun generateResponse(prompt: String, temperature: Float, maxTokens: Int): Flow<Result<String>>
}

class GenerationRepository : IGenerationRepository {

    private val dummyStoryChunks = listOf(
        "هبطت المركبة ",
        "بهدوء على سطح ",
        "الكوكب ذي الرمال ",
        "البنفسجية. خرج ",
        "الكابتن ليث، وهو أول إنسان ",
        "تطأ قدماه هذا العالم، ",
        "ليجد أمامه غابة من ",
        "البلورات المتلألئة التي ",
        "تصدر موسيقى هادئة مع ",
        "كل نسمة ريح."
    )

    override fun generateResponse(prompt: String, temperature: Float, maxTokens: Int): Flow<Result<String>> = flow {
        // Simulate initial delay
        delay(300)

        // Simulate initial failure
        if (prompt.contains("fail_initial", ignoreCase = true)) {
            emit(Result.failure(Exception("محاكاة فشل في الاتصال بالخادم.")))
            return@flow
        }

        // Simulate streaming response
        for ((index, chunk) in dummyStoryChunks.withIndex()) {
            delay(150) // Delay between chunks

            // Simulate a failure mid-stream
            if (index == dummyStoryChunks.size / 2 && prompt.contains("fail_mid", ignoreCase = true)) {
                emit(Result.failure(Exception("انقطع الاتصال أثناء بث الرد.")))
                return@flow
            }

            emit(Result.success(chunk))
        }
    }
}