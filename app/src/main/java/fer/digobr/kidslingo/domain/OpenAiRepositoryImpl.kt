package fer.digobr.kidslingo.domain

import fer.digobr.kidslingo.data.OpenAiRepository
import fer.digobr.kidslingo.data.model.ImageGenerationRequest
import fer.digobr.kidslingo.data.rest.OpenAiApi
import javax.inject.Inject

class OpenAiRepositoryImpl @Inject constructor(private val api: OpenAiApi) : OpenAiRepository {

    override suspend fun generateImage(prompt: String): String? {
        val response = api.generateImage(request = ImageGenerationRequest(prompt = prompt))
        return response.body()?.data?.first()?.url
    }
}
