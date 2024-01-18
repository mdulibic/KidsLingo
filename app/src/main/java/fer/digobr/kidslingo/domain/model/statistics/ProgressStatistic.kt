package fer.digobr.kidslingo.domain.model.statistics

data class ProgressStatistic(
    val playTimestamp: String,
    val numberOfCorrectAnswers: Int,
    val level: String,
    val category: String
)
