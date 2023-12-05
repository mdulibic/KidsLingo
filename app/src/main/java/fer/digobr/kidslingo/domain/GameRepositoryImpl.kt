package fer.digobr.kidslingo.domain

import fer.digobr.kidslingo.data.GameRepository
import fer.digobr.kidslingo.data.model.ImageGenerationRequest
import fer.digobr.kidslingo.data.rest.KidsLingoApi
import fer.digobr.kidslingo.data.rest.OpenAiApi
import fer.digobr.kidslingo.domain.mapper.GameMapper
import fer.digobr.kidslingo.domain.model.GameCategory
import fer.digobr.kidslingo.domain.model.GameItem
import fer.digobr.kidslingo.domain.model.GameLanguage
import fer.digobr.kidslingo.ui.game.model.GameType
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

    private suspend fun generateImage(prompt: String?): String? =
        openAiApi.generateImage(
            request = ImageGenerationRequest(
                prompt = prompt ?: ""
            )
        ).data.first().url


    private fun gameStream(
        language: GameLanguage,
        type: GameType,
        category: GameCategory
    ) =
        when (type) {
            GameType.MULTIPLE_CHOICE -> getGameWithElectionCandidates(
                gameLanguage = language.value,
                category = category.value
            )

            GameType.TYPING -> getClassicGame(
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

                flow {
                    coroutineScope {
                        val imageJob =
                            async {
                                generateImage(prompt = words[0])
                            }

                        val startImageUrl = imageJob.await()

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
        type: GameType,
        category: GameCategory
    ): Flow<Pair<List<GameItem>, String?>> = gameStream(
        language = language,
        type = type,
        category = category
    )

    override fun generateImageByPrompt(prompt: String?): Flow<String?> = flow {
        emit(generateImage(prompt = prompt))
    }
}
