package fer.digobr.kidslingo.ui.statistics

import fer.digobr.kidslingo.data.model.ApiStatisticResponse
import fer.digobr.kidslingo.domain.model.statistics.LanguageStatistic
import fer.digobr.kidslingo.domain.model.statistics.StatisticType
import fer.digobr.kidslingo.ui.statistics.model.StatisticsUiState
import javax.inject.Inject

class StatisticsMapper @Inject constructor() {
    fun map(response: ApiStatisticResponse): StatisticsUiState = with(response) {
        return StatisticsUiState(
            englishStatistic = englishStatistic,
            frenchStatistic = frenchStatistic,
            germanStatistic = germanStatistic,
            italianStatistic = italianStatistic
        )
    }
}
