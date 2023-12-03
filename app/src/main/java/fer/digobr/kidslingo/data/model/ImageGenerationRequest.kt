package fer.digobr.kidslingo.data.model

data class ImageGenerationRequest(
    val model: String = "dall-e-3",
    val prompt: String
)

