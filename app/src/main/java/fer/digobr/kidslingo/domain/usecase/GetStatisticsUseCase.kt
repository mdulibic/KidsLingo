package fer.digobr.kidslingo.domain.usecase

import fer.digobr.kidslingo.data.GameRepository
import fer.digobr.kidslingo.data.model.ApiStatisticResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStatisticsUseCase @Inject constructor(private val gameRepository: GameRepository) {
    operator fun invoke(deviceId: String): Flow<ApiStatisticResponse> {
        return gameRepository.getStatistic(deviceId)
    }
}
