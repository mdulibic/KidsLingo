package fer.digobr.kidslingo.ui.settings

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fer.digobr.kidslingo.domain.SessionManager
import fer.digobr.kidslingo.domain.model.GameCategory
import fer.digobr.kidslingo.domain.model.GameLanguage
import fer.digobr.kidslingo.domain.model.GameLevel
import fer.digobr.kidslingo.ui.settings.model.SettingsMapper
import fer.digobr.kidslingo.ui.settings.model.SettingsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    settingsMapper: SettingsMapper
) : ViewModel() {

    private val _uiState = MutableStateFlow<SettingsUiState?>(null)
    val uiState: StateFlow<SettingsUiState?> = _uiState

    init {
        _uiState.value = SettingsUiState(
            selectedLanguage = settingsMapper.mapToLanguage(sessionManager.language),
            selectedCategory = settingsMapper.mapToCategory(sessionManager.category),
            selectedLevel = settingsMapper.mapToLevel(sessionManager.level?.toInt())
        )
    }

    fun onLanguageChanged(language: GameLanguage) {
        sessionManager.language = language.value
        _uiState.update { state ->
            state?.copy(selectedLanguage = language)
        }
    }

    fun onCategoryChanged(category: GameCategory) {
        Timber.d("onCategoryChanged ${category.value}")
        sessionManager.category = category.value
        _uiState.update { state ->
            state?.copy(selectedCategory = category)
        }
    }

    fun onLevelChanged(level: GameLevel) {
        sessionManager.level = level.value.toString()
        _uiState.update { state ->
            state?.copy(selectedLevel = level)
        }
    }
}
