package fer.digobr.kidslingo.data

import fer.digobr.kidslingo.data.rest.OpenAiApi
import javax.inject.Inject

class OpenAiRepository @Inject constructor(private val api: OpenAiApi) {

    suspend fun generateImage(model: String, prompt: String) =
        api.generateImage(model = model, prompt = prompt)
}
