package fer.digobr.kidslingo.data.model

data class ImageGenerationRequest(
    val model: String = "dall-e-2",
    val prompt: String,
    val style: String = "natural",
)

