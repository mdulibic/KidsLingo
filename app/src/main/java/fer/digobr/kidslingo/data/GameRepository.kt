package fer.digobr.kidslingo.data

import fer.digobr.kidslingo.domain.model.GameCategory
import fer.digobr.kidslingo.domain.model.GameItem
import fer.digobr.kidslingo.domain.model.GameLanguage
import fer.digobr.kidslingo.ui.game.model.GameType
import kotlinx.coroutines.flow.Flow

interface GameRepository {
    fun getGameByLanguageAndType(
        language: GameLanguage,
        type: GameType,
        category: GameCategory
    ): Flow<Pair<List<GameItem>, String?>>

    fun generateImageByPrompt(
        prompt: String?
    ): Flow<String?>
}
