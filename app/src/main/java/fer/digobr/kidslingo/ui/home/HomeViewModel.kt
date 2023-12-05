package fer.digobr.kidslingo.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fer.digobr.kidslingo.R
import fer.digobr.kidslingo.domain.SessionManager
import fer.digobr.kidslingo.domain.model.GameLanguage
import fer.digobr.kidslingo.ui.home.model.HomeUiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState?>(null)
    val uiState: StateFlow<HomeUiState?> = _uiState

    private val _startGame = MutableSharedFlow<Unit>()
    val startGame: SharedFlow<Unit> = _startGame

    init {
        initHomeUiState()
    }

    fun onStartGameClick() {
        viewModelScope.launch {
            _startGame.emit(Unit)
        }
    }

    private fun initHomeUiState() {
        val currentGameLanguage = sessionManager.language?.mapToLanguage()
        _uiState.value = HomeUiState(
            imageRes = currentGameLanguage?.getHomeImageByLanguage() ?: R.drawable.vector_england,
            buttonRes = R.string.start_game,
            titleLabel = R.string.home_label,
            username = sessionManager.username ?: ""
        )
    }

    private fun GameLanguage.getHomeImageByLanguage() =
        when (this) {
            GameLanguage.ENGLISH -> R.drawable.vector_england
            GameLanguage.FRENCH -> R.drawable.vector_france
            GameLanguage.GERMAN -> R.drawable.vector_german
            GameLanguage.ITALIAN -> R.drawable.vector_italia
        }


    private fun String.mapToLanguage() =
        when (this) {
            "en" -> GameLanguage.ENGLISH
            "fr" -> GameLanguage.FRENCH
            "de" -> GameLanguage.GERMAN
            "it" -> GameLanguage.ITALIAN
            else -> GameLanguage.ENGLISH
        }
}
