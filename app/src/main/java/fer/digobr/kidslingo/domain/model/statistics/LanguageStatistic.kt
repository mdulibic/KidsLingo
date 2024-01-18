package fer.digobr.kidslingo.domain.model.statistics

data class LanguageStatistic(
    val averageCorrectAnswers: StatisticType,
    val averageSolvingSpeed: StatisticType,
    val progressStatistic: List<ProgressStatistic>
)
