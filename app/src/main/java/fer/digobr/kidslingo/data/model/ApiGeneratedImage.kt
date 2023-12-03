package fer.digobr.kidslingo.data.model

data class ApiGeneratedImage(
    val created: Long,
    val data: List<ApiImageUrl>
)

data class ApiImageUrl(
    val url: String
)
