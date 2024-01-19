package fer.digobr.kidslingo.data.model

import fer.digobr.kidslingo.domain.model.statistics.LanguageStatistic

data class ApiStatisticResponse(
    val englishStatistic: LanguageStatistic,
    val frenchStatistic: LanguageStatistic,
    val germanStatistic: LanguageStatistic,
    val italianStatistic: LanguageStatistic
)
