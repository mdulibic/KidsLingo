package fer.digobr.kidslingo.data.rest

import fer.digobr.kidslingo.data.model.ApiGeneratedImage
import fer.digobr.kidslingo.data.model.ImageGenerationRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface OpenAiApi {

    @POST("images/generations")
    suspend fun generateImage(
        @Body request: ImageGenerationRequest
    ): ApiGeneratedImage
}

