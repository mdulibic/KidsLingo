package fer.digobr.kidslingo.domain.usecase

import fer.digobr.kidslingo.data.GameRepository
import fer.digobr.kidslingo.domain.model.GameCategory
import fer.digobr.kidslingo.domain.model.GameItem
import fer.digobr.kidslingo.domain.model.GameLanguage
import fer.digobr.kidslingo.ui.game.model.GameType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGameUseCase @Inject constructor(private val gameRepository: GameRepository) {
    operator fun invoke(
        language: GameLanguage,
        type: GameType,
        category: GameCategory
    ): Flow<Pair<List<GameItem>, String?>> =
        gameRepository.getGameByLanguageAndType(
            language = language,
            type = type,
            category = category
        )
}
