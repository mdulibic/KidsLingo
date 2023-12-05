package fer.digobr.kidslingo.data.model

sealed class ApiGameResponse {

    abstract val wordEnglish: String
    abstract val word: String
    abstract val imageUrl: String?

    data class ClassicGame(
        override val wordEnglish: String,
        override val word: String,
        override val imageUrl: String?,
    ) : ApiGameResponse()

    data class ElectionGame(
        override val wordEnglish: String,
        override val word: String,
        override val imageUrl: String?,
        val wrongWords: List<String>,
    ) : ApiGameResponse()
}
