package fer.digobr.kidslingo.data

import fer.digobr.kidslingo.data.model.ApiStatisticResponse
import fer.digobr.kidslingo.domain.model.GameCategory
import fer.digobr.kidslingo.domain.model.GameItem
import fer.digobr.kidslingo.domain.model.GameLanguage
import fer.digobr.kidslingo.domain.model.statistics.StatisticRequest
import fer.digobr.kidslingo.ui.game.model.GameLevel
import kotlinx.coroutines.flow.Flow

interface GameRepository {
    fun getGameByLanguageAndType(
        language: GameLanguage,
        type: GameLevel,
        category: GameCategory
    ): Flow<Pair<List<GameItem>, String?>>

    fun generateImageByPrompt(
        prompt: String?
    ): Flow<String?>

    suspend fun saveStatistic(request: StatisticRequest)

    fun getStatistic(deviceId: String): Flow<ApiStatisticResponse>
}
