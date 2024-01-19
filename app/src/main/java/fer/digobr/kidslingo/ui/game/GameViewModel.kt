package fer.digobr.kidslingo.ui.game

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fer.digobr.kidslingo.R
import fer.digobr.kidslingo.domain.SessionManager
import fer.digobr.kidslingo.domain.model.GameCategory
import fer.digobr.kidslingo.domain.model.GameItem
import fer.digobr.kidslingo.domain.model.GameLanguage
import fer.digobr.kidslingo.domain.model.statistics.StatisticRequest
import fer.digobr.kidslingo.domain.usecase.GetGameUseCase
import fer.digobr.kidslingo.domain.usecase.GetGeneratedImageByPrompt
import fer.digobr.kidslingo.domain.usecase.SaveGameStatisticUseCase
import fer.digobr.kidslingo.ui.game.model.GameLevel
import fer.digobr.kidslingo.ui.game.model.GameUiState
import fer.digobr.kidslingo.ui.game.model.ResultsUiState
import fer.digobr.kidslingo.ui.game.model.SolutionUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

private const val MAX_ROUNDS = 4

@HiltViewModel
class GameViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val getGameUseCase: GetGameUseCase,
    private val getGeneratedImageByPrompt: GetGeneratedImageByPrompt,
    private val saveGameStatisticUseCase: SaveGameStatisticUseCase
) : ViewModel() {

    private val _gameUiState = MutableStateFlow<GameUiState?>(null)
    val gameUiState: StateFlow<GameUiState?> = _gameUiState

    private val _solutionUiState = MutableStateFlow<SolutionUiState?>(null)
    val solutionUiState: StateFlow<SolutionUiState?> = _solutionUiState

    private val _userAnswer = MutableStateFlow<String?>(null)
    val userAnswer: StateFlow<String?> = _userAnswer

    private val _exitGame = MutableSharedFlow<Unit>()
    val exitGame: SharedFlow<Unit> = _exitGame

    private val _resultsUiState = MutableStateFlow<ResultsUiState?>(null)
    val resultsUiState: StateFlow<ResultsUiState?> = _resultsUiState

    private val _isCtaEnabled = MutableStateFlow(false)
    val isCtaEnabled: StateFlow<Boolean> = _isCtaEnabled

    private var roundCount = 0
    private var maxRoundsCount: Int = 0
    private var nextGameItemImageUrl: String? = null
    private var gameItems = mutableListOf<GameItem>()
    private var solutionsMap = mutableMapOf<String, Boolean>()

    private var currentGameLanguage: GameLanguage
    private var currentGameCategory: GameCategory
    private var currentGameLevel: GameLevel

    private var elapsedTimeInSeconds: Double = 0.0
    private var countDownTimer = object : CountDownTimer(Long.MAX_VALUE, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            elapsedTimeInSeconds++
        }

        override fun onFinish() {
            Timber.d("Game finished!")
        }
    }

    init {
        currentGameLanguage = sessionManager.language.mapToLanguage()
        currentGameCategory = sessionManager.category.mapToCategory()
        currentGameLevel = sessionManager.level.mapToType()
        getGame()
    }

    fun onCtaActionClicked() {
        solutionUiState.value?.let {
            _solutionUiState.value = null
            _userAnswer.value = null
            onNextClicked()
        } ?: kotlin.run {
            onCheckSolutionClicked(solution = _userAnswer.value)
        }
    }

    fun onUserAnswerChanged(answer: String) {
        _userAnswer.value = answer
    }

    /**
     * Election game.
     */
    private fun onCheckSolutionClicked(solution: String?) {
        solution?.let {
            val word = gameItems[roundCount].word
            val isCorrect = word == it
            // Save result to map for statistics
            solutionsMap[word] = isCorrect
            Timber.d("Answer: $word isCorrect $isCorrect")

            _solutionUiState.value = SolutionUiState(
                isCorrect = isCorrect,
                userAnswer = it,
                correctAnswer = gameItems[roundCount].word
            )
            _gameUiState.value = gameUiState.value?.copy(
                ctaButtonRes = R.string.btn_continue
            )
        }
    }

    private fun onNextClicked() {
        if (roundCount == maxRoundsCount) {
            countDownTimer.cancel()

            val score = solutionsMap.values.count { it }
            val questionCount = solutionsMap.size

            Timber.d("Solution: $score/$questionCount")

            val messageRes = getMessageRes(score, questionCount)
            viewModelScope.launch {
                _resultsUiState.emit(
                    ResultsUiState(
                        score = score,
                        questionCount = questionCount,
                        messageRes = messageRes
                    )
                )
            }
            saveGameStatistic()
        } else {
            continueGame()
        }
    }

    private fun saveGameStatistic() {
        viewModelScope.launch(Dispatchers.Default) {
            val numberOfCorrectAnswers = solutionsMap.values.filter { it }.count()
            val request = StatisticRequest(
                deviceId = sessionManager.deviceId ?: "",
                language = currentGameLanguage.value,
                level = currentGameLevel.name,
                category = currentGameCategory.value,
                numberOfCorrectAnswers = numberOfCorrectAnswers,
                solvingSpeed = elapsedTimeInSeconds
            )
            Timber.d("Saving game statistic: $request")
            saveGameStatisticUseCase(
                request = request
            )
        }
    }

    private fun getMessageRes(score: Int, questionCount: Int): Int {
        val proportion = score.toFloat() / questionCount

        return when {
            proportion.toInt() == 1 -> R.string.excellent_message
            proportion >= 0.6 -> R.string.good_message
            proportion >= 0.4 -> R.string.average_message
            else -> R.string.average_message
        }
    }

    fun onExitGame() = viewModelScope.launch {
        _exitGame.emit(Unit)
    }

    private fun continueGame() {
        roundCount++
        _gameUiState.value = gameUiState.value?.copy(
            roundCount = roundCount + 1,
            gameItem = gameItems[roundCount],
            imageUrl = nextGameItemImageUrl,
        )
        if (roundCount != MAX_ROUNDS) {
            generateNextImage(prompt = gameItems[roundCount + 1].wordEnglish)
        }
    }

    private fun getGame() {
        val levelLabel = when (currentGameLevel) {
            GameLevel.ELECTED -> R.string.level_1
            GameLevel.TYPED -> R.string.level_2
        }
        val gameQuestionLabel =
            when (currentGameCategory) {
                GameCategory.COLORS -> R.string.games_question_colors
                GameCategory.ANIMALS -> R.string.games_question_animals
                GameCategory.FOOD -> R.string.games_question_food
                GameCategory.OBJECTS -> R.string.games_question_object
            }

        viewModelScope.launch {
            getGameUseCase.invoke(
                language = currentGameLanguage,
                type = currentGameLevel,
                category = currentGameCategory
            ).collectLatest {
                gameItems.clear()
                it.first.forEach { item ->
                    gameItems.add(item)
                }

                Timber.d("Game items: $gameItems")

                maxRoundsCount = gameItems.size - 1

                Timber.d("Max rounds count $maxRoundsCount")

                _gameUiState.value = GameUiState(
                    gameItem = it.first[roundCount],
                    imageUrl = it.second,
                    levelLabelRes = levelLabel,
                    gameQuestionLabelRes = gameQuestionLabel,
                    roundCount = roundCount + 1,
                    ctaButtonRes = R.string.btn_check,
                    gameItemsCount = it.first.count()
                )
                countDownTimer.start()
            }

            generateNextImage(prompt = gameItems[roundCount + 1].wordEnglish)
        }
    }

    private fun generateNextImage(prompt: String?) {
        _isCtaEnabled.update { false }
        viewModelScope.launch {
            getGeneratedImageByPrompt(prompt = prompt).collectLatest {
                nextGameItemImageUrl = it
                _isCtaEnabled.update { true }
            }
        }
    }

    private fun String?.mapToLanguage(): GameLanguage =
        when (this) {
            "english".uppercase() -> GameLanguage.ENGLISH
            "french".uppercase() -> GameLanguage.FRENCH
            "german".uppercase() -> GameLanguage.GERMAN
            "italian".uppercase() -> GameLanguage.ITALIAN
            else -> GameLanguage.ENGLISH
        }

    private fun String?.mapToCategory(): GameCategory =
        when (this) {
            "colors".uppercase() -> GameCategory.COLORS
            "animals".uppercase() -> GameCategory.ANIMALS
            "food".uppercase() -> GameCategory.FOOD
            "objects".uppercase() -> GameCategory.OBJECTS
            else -> GameCategory.COLORS
        }

    private fun String?.mapToType(): GameLevel {
        Timber.d("mapToType $this")
        return when (this) {
            "1" -> GameLevel.ELECTED
            "2" -> GameLevel.TYPED
            else -> GameLevel.ELECTED
        }
    }
}
