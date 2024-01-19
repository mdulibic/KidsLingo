package fer.digobr.kidslingo.ui.statistics

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fer.digobr.kidslingo.R
import fer.digobr.kidslingo.domain.model.statistics.LanguageStatistic
import fer.digobr.kidslingo.domain.model.statistics.ProgressStatistic
import fer.digobr.kidslingo.theme.AppDarkGreen
import fer.digobr.kidslingo.theme.AppGreen
import fer.digobr.kidslingo.theme.AppOrange
import fer.digobr.kidslingo.theme.AppWrongRed
import fer.digobr.kidslingo.ui.game.view.LoadingIndicator
import timber.log.Timber
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun StatisticsScreen(
    statisticsViewModel: StatisticsViewModel
) {
    val uiState by statisticsViewModel.uiState.collectAsState()
    uiState?.let {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                StatisticsSection(
                    sectionTitleRes = R.string.english_statistic,
                    languageStatistic = it.englishStatistic
                )
            }
            item {
                StatisticsSection(
                    sectionTitleRes = R.string.french_statistic,
                    languageStatistic = it.frenchStatistic
                )
            }
            item {
                StatisticsSection(
                    sectionTitleRes = R.string.german_statistic,
                    languageStatistic = it.germanStatistic
                )
            }
            item {
                StatisticsSection(
                    sectionTitleRes = R.string.italian_statistic,
                    languageStatistic = it.italianStatistic
                )
            }
        }
    } ?: run {
        LoadingIndicator()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun StatisticsSection(
    sectionTitleRes: Int,
    languageStatistic: LanguageStatistic
) {
    with(languageStatistic) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(id = sectionTitleRes),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AppDarkGreen.copy(alpha = 0.7f), RoundedCornerShape(4.dp))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.average_correct_answers),
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 16.sp,
                )
                Divider(
                    modifier = Modifier
                        .height(2.dp)
                        .width(100.dp)
                        .background(Color.White)
                        .padding(vertical = 2.dp),
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    StatisticsCategoryColumn(
                        category = averageCorrectAnswers.elected,
                        categoryName = stringResource(R.string.elected)
                    )
                    StatisticsCategoryColumn(
                        category = averageCorrectAnswers.typed,
                        categoryName = stringResource(R.string.typed)
                    )
                }
                Text(
                    text = stringResource(R.string.average_solving_speed),
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 16.sp,
                )
                Divider(
                    modifier = Modifier
                        .height(2.dp)
                        .width(100.dp)
                        .background(Color.White)
                        .padding(vertical = 2.dp),
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    StatisticsCategoryColumn(
                        category = averageSolvingSpeed.elected,
                        categoryName = stringResource(R.string.elected)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    StatisticsCategoryColumn(
                        category = averageSolvingSpeed.typed,
                        categoryName = stringResource(R.string.typed)
                    )
                }
                Text(
                    text = stringResource(R.string.progress_statistics),
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 16.sp,
                )
                Divider(
                    modifier = Modifier
                        .height(2.dp)
                        .width(100.dp)
                        .background(Color.White)
                        .padding(vertical = 2.dp),
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (progressStatistic.isEmpty()) {
                        Text(
                            text = stringResource(R.string.no_data),
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    } else {
                        Column(
                            modifier = Modifier
                                .background(AppGreen, RoundedCornerShape(8.dp))
                                .padding(8.dp),
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.legenda),
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center,
                                color = Color.White,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(
                                    4.dp,
                                    Alignment.CenterHorizontally
                                )
                            ) {
                                Text(
                                    text = stringResource(R.string.elected),
                                    fontSize = 12.sp,
                                    textAlign = TextAlign.Center,
                                    color = Color.White,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                                Spacer(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .background(AppOrange)
                                )
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(
                                    2.dp,
                                    Alignment.CenterHorizontally
                                )
                            ) {
                                Text(
                                    text = stringResource(R.string.typed),
                                    fontSize = 12.sp,
                                    textAlign = TextAlign.Center,
                                    color = Color.White,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                                Spacer(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .background(AppWrongRed)
                                )
                            }
                        }
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(progressStatistic) {
                                ProgressItem(it)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatisticsCategoryColumn(category: Map<String, Double>, categoryName: String) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = categoryName,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 16.sp,
        )
        if (category.isEmpty()) {
            Text(
                text = stringResource(R.string.no_data),
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        } else {
            category.forEach { (key, value) ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    val updated = when (key) {
                        "colors" -> "boje"
                        "animals" -> "životinje"
                        "food" -> "hrana"
                        "objects" -> "objekti"
                        else -> "nepoznato"
                    }
                    Text(
                        text = "${updated.uppercase()}: ",
                        fontSize = 14.sp,
                        color = AppOrange,
                    )
                    Text(
                        text = "${value.toInt()}",
                        fontSize = 14.sp,
                        color = Color.White,
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun formatDateTime(dateTimeString: String): String {
    val dateTimeWithoutFractionalSeconds = dateTimeString.substring(0, 19)

    val originalFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    val originalDateTime = LocalDateTime.parse(dateTimeWithoutFractionalSeconds, originalFormatter)

    val targetFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm")
    return originalDateTime.format(targetFormatter)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProgressItem(progress: ProgressStatistic) {
    val barColor = if (progress.level == "ELECTED") AppOrange else AppWrongRed
    val category = when (progress.category) {
        "colors".uppercase() -> "boje"
        "animals".uppercase() -> "životinje"
        "food".uppercase() -> "hrana"
        "objects".uppercase() -> "objekti"
        else -> "nepoznato"
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            modifier = Modifier.width(80.dp),
            text = formatDateTime(progress.playTimestamp),
            fontSize = 14.sp,
            color = Color.White,
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            Spacer(
                modifier = Modifier
                    .height((progress.numberOfCorrectAnswers * 8).dp)
                    .width(15.dp)
                    .background(barColor)
            )
            Text(
                text = "${progress.numberOfCorrectAnswers.toString()} ",
                fontSize = 14.sp,
                color = Color.White,
            )
        }
        Text(
            text = category,
            fontSize = 14.sp,
            color = Color.White,
        )
    }
}
