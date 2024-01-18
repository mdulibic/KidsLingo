package fer.digobr.kidslingo.data.model

import fer.digobr.kidslingo.domain.model.statistics.LanguageStatistic

data class ApiStatisticResponse(
    val englishStatistic: LanguageStatistic? = null,
    val frenchStatistic: LanguageStatistic? = null,
    val germanStatistic: LanguageStatistic? = null,
    val italianStatistic: LanguageStatistic? = null
)
