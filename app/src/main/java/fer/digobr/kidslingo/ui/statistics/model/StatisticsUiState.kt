package fer.digobr.kidslingo.ui.statistics.model

import fer.digobr.kidslingo.domain.model.statistics.LanguageStatistic

data class StatisticsUiState(
    val englishStatistic: LanguageStatistic,
    val frenchStatistic: LanguageStatistic,
    val germanStatistic: LanguageStatistic,
    val italianStatistic: LanguageStatistic
)
