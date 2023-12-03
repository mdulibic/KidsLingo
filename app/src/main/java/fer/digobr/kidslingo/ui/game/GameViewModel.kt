package fer.digobr.kidslingo.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fer.digobr.kidslingo.R
import fer.digobr.kidslingo.domain.OpenAiRepositoryImpl
import fer.digobr.kidslingo.domain.SessionManager
import fer.digobr.kidslingo.domain.model.Language
import fer.digobr.kidslingo.ui.game.model.GameUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val openAiRepository: OpenAiRepositoryImpl
) : ViewModel() {

    private val _uiState = MutableStateFlow<GameUiState?>(null)
    val uiState: StateFlow<GameUiState?> = _uiState

    private val _url = MutableStateFlow<String?>(null)
    val url: StateFlow<String?> = _url

    init {
        generateImageFromPrompt()
        initGameUiState()
    }

    private fun initGameUiState() {
        val currentGameLanguage = sessionManager.language?.mapToLanguage()


    }

    private fun generateImageFromPrompt() {
        viewModelScope.launch {
            _url.value = openAiRepository.generateImage("cat")
        }
    }

    private fun Language.getHomeImageByLanguage() =
        when (this) {
            Language.ENGLISH -> R.drawable.vector_england
            Language.FRENCH -> R.drawable.vector_france
            Language.GERMAN -> R.drawable.vector_german
            Language.ITALIAN -> R.drawable.vector_italia
        }


    private fun String.mapToLanguage() =
        when (this) {
            "en" -> Language.ENGLISH
            "fr" -> Language.FRENCH
            "de" -> Language.GERMAN
            "it" -> Language.ITALIAN
            else -> Language.ENGLISH
        }
}
