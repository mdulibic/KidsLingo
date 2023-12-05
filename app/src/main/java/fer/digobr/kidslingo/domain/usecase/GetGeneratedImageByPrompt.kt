package fer.digobr.kidslingo.domain.usecase

import fer.digobr.kidslingo.data.GameRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGeneratedImageByPrompt @Inject constructor(private val gameRepository: GameRepository) {
    operator fun invoke(
        prompt: String?
    ): Flow<String?> =
        gameRepository.generateImageByPrompt(prompt = prompt)
}

