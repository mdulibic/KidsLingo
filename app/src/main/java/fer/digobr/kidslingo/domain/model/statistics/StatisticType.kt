package fer.digobr.kidslingo.domain.model.statistics

data class StatisticType(
    val elected: Map<String, Double>,
    val typed: Map<String, Double>,
)
