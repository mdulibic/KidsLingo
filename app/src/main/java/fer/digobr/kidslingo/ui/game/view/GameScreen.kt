package fer.digobr.kidslingo.ui.game.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import fer.digobr.kidslingo.R
import fer.digobr.kidslingo.domain.model.GameItem
import fer.digobr.kidslingo.theme.AppDarkGreen
import fer.digobr.kidslingo.theme.AppGreen
import fer.digobr.kidslingo.theme.AppOrange
import fer.digobr.kidslingo.ui.game.GameViewModel
import fer.digobr.kidslingo.ui.game.model.GameUiState
import fer.digobr.kidslingo.ui.game.model.SolutionUiState
import timber.log.Timber

@Composable
fun GameScreen(
    gameViewModel: GameViewModel
) {
    val gameUiState by gameViewModel.gameUiState.collectAsState()
    val solutionUiState by gameViewModel.solutionUiState.collectAsState()
    val resultsUiState by gameViewModel.resultsUiState.collectAsState()
    val userAnswer by gameViewModel.userAnswer.collectAsState()
    val isCtaEnabled by gameViewModel.isCtaEnabled.collectAsState()

    resultsUiState?.let {
        ResultsScreen(
            uiState = it,
            onContinueClick = {
                gameViewModel.onExitGame()
            }
        )
    } ?: run {
        GameLayout(
            gameUiState = gameUiState,
            userAnswer = userAnswer,
            solutionUiState = solutionUiState,
            isCtaEnabled = isCtaEnabled,
            onActionClick = { gameViewModel.onCtaActionClicked() },
            onUserAnswerChanged = { gameViewModel.onUserAnswerChanged(it) }
        )
    }
}

@Composable
fun GameLayout(
    gameUiState: GameUiState?,
    userAnswer: String?,
    isCtaEnabled: Boolean,
    solutionUiState: SolutionUiState?,
    onActionClick: () -> Unit,
    onUserAnswerChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(AppGreen),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        gameUiState?.let {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                GameHeader(gameUiState = gameUiState)
                Spacer(modifier = Modifier.height(24.dp))
                GameImage(
                    questionLabelRes = gameUiState.gameQuestionLabelRes,
                    imageUrl = it.imageUrl
                )
                Spacer(modifier = Modifier.height(24.dp))
                when (val item = it.gameItem) {
                    is GameItem.Classic -> TypingGame(
                        userAnswer = userAnswer,
                        onAnswerChanged = onUserAnswerChanged
                    )

                    is GameItem.Election -> ElectionGame(
                        gameItem = item,
                        userAnswer = userAnswer,
                        isSolutionPreview = solutionUiState != null,
                        onChoiceSelect = onUserAnswerChanged
                    )
                }
            }
            GameCTALayout(
                correctAnswer = gameUiState.gameItem.word,
                isCtaEnabled = isCtaEnabled,
                hasBottomSheetPreview = gameUiState.gameItem is GameItem.Classic,
                ctaButtonRes = gameUiState.ctaButtonRes,
                onActionClick = onActionClick,
                solutionUiState = solutionUiState,
            )
        } ?: run {
            LoadingIndicator()
        }
    }
}

@Composable
private fun GameImage(
    questionLabelRes: Int,
    imageUrl: String?,
    modifier: Modifier = Modifier
) {
    val painter = rememberImagePainter(
        data = imageUrl,
        builder = {
            crossfade(true)
        }
    )
    Text(
        text = stringResource(id = questionLabelRes),
        color = Color.White,
        fontSize = 24.sp,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(16.dp))
    Image(
        painter = painter,
        contentDescription = "",
        modifier = modifier
            .height(160.dp)
            .fillMaxWidth(),
    )
}

@Composable
private fun GameHeader(
    gameUiState: GameUiState,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedButton(
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = AppOrange),
            onClick = {}
        ) {
            Text(
                text = stringResource(id = gameUiState.levelLabelRes),
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 16.sp,
            )
        }

        Text(
            text = stringResource(
                id = R.string.round_label,
                gameUiState.roundCount,
                gameUiState.gameItemsCount
            ),
            color = Color.White,
            fontSize = 24.sp,
        )
    }
}

@Composable
private fun GameCTALayout(
    correctAnswer: String,
    hasBottomSheetPreview: Boolean,
    isCtaEnabled: Boolean,
    ctaButtonRes: Int,
    solutionUiState: SolutionUiState?,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (hasBottomSheetPreview) {
        solutionUiState?.let {
            Column(
                modifier = Modifier
                    .background(AppDarkGreen)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(
                        6.dp,
                        Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(if (it.isCorrect) R.drawable.ic_correct else R.drawable.ic_wrong),
                        contentDescription = "",
                        modifier = modifier
                            .size(24.dp)
                            .fillMaxWidth(),
                    )
                    Text(
                        text = stringResource(if (it.isCorrect) R.string.correct_answer else R.string.wrong_answer),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 20.sp,
                    )
                }
                if (!it.isCorrect) {
                    Text(
                        text = stringResource(id = R.string.correct_answer_preview, correctAnswer),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(16.dp),
                    )
                }
                GameCTAButton(
                    ctaButtonRes = ctaButtonRes,
                    isCtaEnabled = isCtaEnabled,
                    onActionClick = onActionClick,
                )
            }
        } ?: run {
            GameCTAButton(
                ctaButtonRes = ctaButtonRes,
                isCtaEnabled = isCtaEnabled,
                onActionClick = onActionClick,
            )
        }
    } else {
        GameCTAButton(
            ctaButtonRes = ctaButtonRes,
            isCtaEnabled = isCtaEnabled,
            onActionClick = onActionClick,
        )
    }
}

@Composable
private fun GameCTAButton(
    ctaButtonRes: Int,
    isCtaEnabled: Boolean,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if(isCtaEnabled) AppOrange else AppOrange.copy(alpha = 0.7f)
    Timber.d("GameCTAButton isEnabled: $isCtaEnabled")
    OutlinedButton(
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.outlinedButtonColors(backgroundColor = backgroundColor ),
        onClick = onActionClick,
        enabled = isCtaEnabled,
        contentPadding = PaddingValues(10.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = ctaButtonRes),
            textAlign = TextAlign.Center,
            color = Color.White,
            fontSize = 18.sp,
        )
    }
}
