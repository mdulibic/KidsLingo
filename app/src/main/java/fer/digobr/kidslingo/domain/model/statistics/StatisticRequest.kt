package fer.digobr.kidslingo.domain.model.statistics

data class StatisticRequest(
    val deviceId: String,
    val language: String,
    val level: String,
    val category: String,
    val numberOfCorrectAnswers: Int,
    val solvingSpeed: Double
)
