package fer.digobr.kidslingo.data.rest

import fer.digobr.kidslingo.data.model.ApiGameResponse
import fer.digobr.kidslingo.data.model.ApiStatisticResponse
import fer.digobr.kidslingo.domain.model.statistics.StatisticRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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

    @POST("/statistic")
    suspend fun saveStatistic(@Body request: StatisticRequest)

    @GET("/statistic/{deviceId}")
    suspend fun getStatistic(@Path("deviceId") deviceId: String): ApiStatisticResponse
}
