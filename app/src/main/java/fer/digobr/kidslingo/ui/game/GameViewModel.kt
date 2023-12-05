package fer.digobr.kidslingo.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fer.digobr.kidslingo.R
import fer.digobr.kidslingo.domain.SessionManager
import fer.digobr.kidslingo.domain.model.GameCategory
import fer.digobr.kidslingo.domain.model.GameItem
import fer.digobr.kidslingo.domain.model.GameLanguage
import fer.digobr.kidslingo.domain.usecase.GetGameUseCase
import fer.digobr.kidslingo.domain.usecase.GetGeneratedImageByPrompt
import fer.digobr.kidslingo.ui.game.model.GameType
import fer.digobr.kidslingo.ui.game.model.GameUiState
import fer.digobr.kidslingo.ui.game.model.SolutionUiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.math.round

private const val MAX_ROUNDS = 5

@HiltViewModel
class GameViewModel @Inject constructor(
    sessionManager: SessionManager,
    private val getGameUseCase: GetGameUseCase,
    private val getGeneratedImageByPrompt: GetGeneratedImageByPrompt
) : ViewModel() {

    private val _gameUiState = MutableStateFlow<GameUiState?>(null)
    val gameUiState: StateFlow<GameUiState?> = _gameUiState

    private val _solutionUiState = MutableStateFlow<SolutionUiState?>(null)
    val solutionUiState: StateFlow<SolutionUiState?> = _solutionUiState

    private val _exitGame = MutableSharedFlow<Unit>()
    val exitGame: SharedFlow<Unit> = _exitGame

    private var roundCount = 0
    private var nextGameItemImageUrl: String? = null
    private var gameItems = mutableListOf<GameItem>()
    private var solutionsMap = mapOf<String, Boolean>()

    private val currentGameLanguage = sessionManager.language.mapToLanguage()
    private val currentGameCategory = sessionManager.category.mapToCategory()
    private val currentGameType = sessionManager.level.mapToType()

    init {
        getGame()
    }

    fun onCheckSolutionClicked(solution: String) {
        val isCorrect = gameItems[roundCount].word == solution
        _solutionUiState.value = SolutionUiState(
            isCorrect = isCorrect,
            userAnswer = solution,
            correctAnswer = gameItems[roundCount].word
        )
    }

    fun onNextClicked() {
        if (roundCount == MAX_ROUNDS) {
            navigateToResultsScreen()
        } else {
            roundCount++
            getGame()
        }
    }

    fun onExitGame() = viewModelScope.launch {
        _exitGame.emit(Unit)
    }

    private fun navigateToResultsScreen() {

    }

    private fun getGame() {
        val levelLabel = when (currentGameType) {
            GameType.MULTIPLE_CHOICE -> R.string.level_1
            GameType.TYPING -> R.string.level_2
        }
        val gameQuestionLabel = when (currentGameType) {
            GameType.MULTIPLE_CHOICE -> R.string.games_question_level_1
            GameType.TYPING -> R.string.games_question_level_2
        }

        viewModelScope.launch {
            getGameUseCase.invoke(
                language = currentGameLanguage,
                type = currentGameType,
                category = currentGameCategory
            ).collectLatest {
                gameItems.apply {
                    clear()
                    addAll(it.first)
                }

                _gameUiState.value = GameUiState(
                    gameItem = it.first[0],
                    imageUrl = it.second,
                    levelLabelRes = levelLabel,
                    gameQuestionLabelRes = gameQuestionLabel,
                    roundCount = roundCount + 1
                )
            }
        }
        generateNextImage(prompt = gameItems[roundCount++].wordEnglish)
    }

    private fun generateNextImage(prompt: String?) {
        viewModelScope.launch {
            getGeneratedImageByPrompt(prompt = prompt).collectLatest {
                nextGameItemImageUrl = it
            }
        }
    }

    private fun String?.mapToLanguage(): GameLanguage =
        when (this) {
            "english" -> GameLanguage.ENGLISH
            "french" -> GameLanguage.FRENCH
            "german" -> GameLanguage.GERMAN
            "italian" -> GameLanguage.ITALIAN
            else -> GameLanguage.ENGLISH
        }

    private fun String?.mapToCategory(): GameCategory =
        when (this) {
            "colors" -> GameCategory.COLORS
            "animals" -> GameCategory.ANIMALS
            "food" -> GameCategory.FOOD
            "objects" -> GameCategory.OBJECTS
            else -> GameCategory.COLORS
        }

    private fun String?.mapToType(): GameType =
        when (this) {
            "1" -> GameType.MULTIPLE_CHOICE
            "2" -> GameType.TYPING
            else -> GameType.TYPING
        }
}
