package fer.digobr.kidslingo.data.rest

import fer.digobr.kidslingo.data.model.ApiGameResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface KidsLingoApi {
    @GET("/game/election/{language}/{category}")
    suspend fun getGameWithElectionCandidates(
        @Path("language") language: String,
        @Path("category") category: String
    ): List<ApiGameResponse.ElectionGame>

    @GET("/game/{language}/{category}")
    suspend fun getClassicGame(
        @Path("language") language: String,
        @Path("category") category: String
    ): List<ApiGameResponse.ClassicGame>
}
