package fer.digobr.kidslingo.data

interface OpenAiRepository {

    suspend fun generateImage(prompt: String): String?
}
