package fer.digobr.kidslingo.domain.mapper

import fer.digobr.kidslingo.data.model.ApiGameResponse
import fer.digobr.kidslingo.domain.model.GameItem
import javax.inject.Inject

class GameMapper @Inject constructor() {
    fun mapToGameItems(
        apiGameList: List<ApiGameResponse>,
        startImageUrl: String?
    ): Pair<List<GameItem>, String?> {
        val gameItems = apiGameList.map { apiGame ->
            when (apiGame) {
                is ApiGameResponse.ClassicGame -> GameItem.Classic(
                    wordEnglish = apiGame.wordEnglish,
                    word = apiGame.word,
                )

                is ApiGameResponse.ElectionGame -> GameItem.Election(
                    wordEnglish = apiGame.wordEnglish,
                    word = apiGame.word,
                    wrongWords = apiGame.wrongWords
                )
            }
        }
        return Pair(gameItems, startImageUrl)
    }
}
