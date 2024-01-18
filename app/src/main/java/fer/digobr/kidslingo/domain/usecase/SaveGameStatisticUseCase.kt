package fer.digobr.kidslingo.domain.usecase

import fer.digobr.kidslingo.data.GameRepository
import fer.digobr.kidslingo.domain.model.statistics.StatisticRequest
import javax.inject.Inject

class SaveGameStatisticUseCase @Inject constructor(private val gameRepository: GameRepository) {
    suspend operator fun invoke(request: StatisticRequest) {
        gameRepository.saveStatistic(request)
    }
}
