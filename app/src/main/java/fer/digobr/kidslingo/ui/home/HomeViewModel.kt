package fer.digobr.kidslingo.ui.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fer.digobr.kidslingo.R
import fer.digobr.kidslingo.domain.SessionManager
import fer.digobr.kidslingo.domain.model.Language
import fer.digobr.kidslingo.ui.home.model.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState?>(null)
    val uiState: StateFlow<HomeUiState?> = _uiState

    init {
        initHomeUiState()
    }

    fun onStartGameClick() {
        // Navigate to game screen
    }

    private fun initHomeUiState() {
        val currentGameLanguage = sessionManager.language?.mapToLanguage()
        _uiState.value = HomeUiState(
            imageRes = currentGameLanguage?.getHomeImageByLanguage() ?: R.drawable.vector_england,
            buttonRes = R.string.start_game,
            titleLabel = R.string.home_label,
            username = sessionManager?.username ?: ""
        )
    }

    private fun Language.getHomeImageByLanguage() =
        when (this) {
            Language.ENGLISH -> R.drawable.vector_england
            Language.FRENCH -> R.drawable.vector_england
            Language.GERMAN -> R.drawable.vector_england
            Language.ITALIAN -> R.drawable.vector_england
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
