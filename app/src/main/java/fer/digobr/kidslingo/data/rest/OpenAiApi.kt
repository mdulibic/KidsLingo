package fer.digobr.kidslingo.data.rest

import fer.digobr.kidslingo.data.model.ApiGeneratedImage
import retrofit2.http.POST
import retrofit2.http.Query

interface OpenAiApi {

    @POST("images/generations")
    suspend fun generateImage(
        @Query("model") model: String,
        @Query("prompt") prompt: String
    ): ApiGeneratedImage
}

