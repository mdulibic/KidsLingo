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

                is ApiGameResponse.ElectionGame -> apiGame.mapElectionGameItem()
            }
        }
        return Pair(gameItems, startImageUrl)
    }

    private fun ApiGameResponse.ElectionGame.mapElectionGameItem(): GameItem.Election =
        with(this) {
            val items = mutableListOf<String>().let {
                it.addAll(wrongWords)
                it
            }
            items.add(word)
            items.shuffle()

            val wordMap = mutableMapOf<String, Boolean>().apply {
                items.forEach { item ->
                    val isSolution = item == word
                    put(item, isSolution)
                }
            }
            GameItem.Election(
                wordEnglish = wordEnglish,
                word = word,
                wordMap = wordMap
            )
        }
}

