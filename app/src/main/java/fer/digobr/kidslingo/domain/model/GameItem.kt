package fer.digobr.kidslingo.domain.model

sealed class GameItem {

    abstract val wordEnglish: String
    abstract val word: String

    data class Classic(
        override val wordEnglish: String,
        override val word: String,
    ) : GameItem()

    data class Election(
        override val wordEnglish: String,
        override val word: String,
        val wrongWords: List<String>,
    ) : GameItem()
}
