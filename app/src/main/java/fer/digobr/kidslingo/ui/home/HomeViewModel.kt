package fer.digobr.kidslingo.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fer.digobr.kidslingo.R
import fer.digobr.kidslingo.domain.SessionManager
import fer.digobr.kidslingo.ui.home.model.HomeUiState
import fer.digobr.kidslingo.ui.settings.model.SettingsMapper
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val settingsMapper: SettingsMapper
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

    fun initHomeUiState() {
        val currentGameLanguage = settingsMapper.mapToLanguage(sessionManager.language)
        _uiState.value = HomeUiState(
            imageRes = settingsMapper.getHomeImageByLanguage(currentGameLanguage) ?: R.drawable.vector_england,
            buttonRes = R.string.start_game,
            titleLabel = R.string.home_label,
            username = sessionManager.username ?: ""
        )
    }
}
