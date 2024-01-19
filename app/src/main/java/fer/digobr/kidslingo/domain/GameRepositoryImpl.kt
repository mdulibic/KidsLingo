package fer.digobr.kidslingo.domain

import fer.digobr.kidslingo.data.GameRepository
import fer.digobr.kidslingo.data.model.ApiStatisticResponse
import fer.digobr.kidslingo.data.model.ImageGenerationRequest
import fer.digobr.kidslingo.data.rest.KidsLingoApi
import fer.digobr.kidslingo.data.rest.OpenAiApi
import fer.digobr.kidslingo.domain.mapper.GameMapper
import fer.digobr.kidslingo.domain.model.GameCategory
import fer.digobr.kidslingo.domain.model.GameItem
import fer.digobr.kidslingo.domain.model.GameLanguage
import fer.digobr.kidslingo.domain.model.statistics.StatisticRequest
import fer.digobr.kidslingo.ui.game.model.GameLevel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val api: KidsLingoApi,
    private val openAiApi: OpenAiApi,
    private val mapper: GameMapper
) : GameRepository {
    private fun getGameWithElectionCandidates(
        gameLanguage: String,
        category: String
    ) = flow {
        emit(
            api.getGameWithElectionCandidates(language = gameLanguage, category = category)
        )
    }.distinctUntilChanged()


    private fun getClassicGame(
        gameLanguage: String,
        category: String
    ) = flow {
        emit(
            api.getClassicGame(language = gameLanguage, category = category)
        )
    }.distinctUntilChanged()

    private suspend fun generateImage(prompt: String?): String? {
        return try {
            val response = openAiApi.generateImage(
                request = ImageGenerationRequest(
                    prompt = prompt ?: ""
                )
            )
            response.data.firstOrNull()?.url
        } catch (e: Exception) {
            Timber.e(e, "Error in generateImage")
            "https://placekitten.com/300/200"
        }
    }


    private fun gameStream(
        language: GameLanguage,
        type: GameLevel,
        category: GameCategory
    ) =
        when (type) {
            GameLevel.ELECTED -> getGameWithElectionCandidates(
                gameLanguage = language.value,
                category = category.value
            )

            GameLevel.TYPED -> getClassicGame(
                gameLanguage = language.value,
                category = category.value
            )

        }
            .catch {
                Timber.d("gameStream $it")
                emit(emptyList())
            }
            .flatMapLatest { apiGameList ->
                val words = apiGameList.mapNotNull { it.wordEnglish }

                Timber.d("Api game list $apiGameList")

                flow {
                    coroutineScope {
                        val imageJob =
                            async {
                                generateImage(prompt = words[0])
                            }

                        val startImageUrl = imageJob.await()
                       // val startImageUrl = "https://placekitten.com/300/200"

                        val game = mapper.mapToGameItems(
                            apiGameList = apiGameList,
                            startImageUrl = startImageUrl
                        )
                        emit(game)
                    }
                }
            }


    override fun getGameByLanguageAndType(
        language: GameLanguage,
        type: GameLevel,
        category: GameCategory
    ): Flow<Pair<List<GameItem>, String?>> = gameStream(
        language = language,
        type = type,
        category = category
    )

    override fun generateImageByPrompt(prompt: String?): Flow<String?> = flow {
        try {
            emit(generateImage(prompt = prompt))
        } catch (e: Exception) {
            Timber.e(e, "Error in generateImageByPrompt")
            emit("https://placekitten.com/300/200")
        }
    }

    override suspend fun saveStatistic(request: StatisticRequest) {
        try {
            api.saveStatistic(request = request)
        } catch (e: Exception) {
            Timber.e(e, "Error in saveStatistic")
        }
    }

    override fun getStatistic(deviceId: String): Flow<ApiStatisticResponse> = flow {
        try {
            emit(api.getStatistic(deviceId = deviceId))
        } catch (e: Exception) {
            Timber.e(e, "Error in getStatistic")
            emit(ApiStatisticResponse())
        }
    }

}
