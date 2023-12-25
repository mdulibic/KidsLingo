package fer.digobr.kidslingo.ui.settings.model

import fer.digobr.kidslingo.domain.model.GameCategory
import fer.digobr.kidslingo.domain.model.GameLanguage
import fer.digobr.kidslingo.domain.model.GameLevel

data class SettingsUiState(
    val selectedLanguage: GameLanguage,
    val selectedCategory: GameCategory,
    val selectedLevel: GameLevel
)
