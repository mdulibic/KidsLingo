package fer.digobr.kidslingo.ui.settings.model

import fer.digobr.kidslingo.R
import fer.digobr.kidslingo.domain.model.GameCategory
import fer.digobr.kidslingo.domain.model.GameLanguage
import fer.digobr.kidslingo.domain.model.GameLevel
import timber.log.Timber
import javax.inject.Inject

class SettingsMapper @Inject constructor() {
    fun getHomeImageByLanguage(language: GameLanguage) = with(language) {
        when (this) {
            GameLanguage.ENGLISH -> R.drawable.vector_england
            GameLanguage.FRENCH -> R.drawable.vector_france
            GameLanguage.GERMAN -> R.drawable.vector_german
            GameLanguage.ITALIAN -> R.drawable.vector_italia
        }
    }

    fun mapToLanguage(value: String?) = with(value) {
        when (this) {
            "english".uppercase() -> GameLanguage.ENGLISH
            "french".uppercase() -> GameLanguage.FRENCH
            "german".uppercase() -> GameLanguage.GERMAN
            "italian".uppercase() -> GameLanguage.ITALIAN
            else -> GameLanguage.ENGLISH
        }
    }

    fun mapToCategory(value: String?) = with(value) {
        when (this) {
            "animals".uppercase() -> GameCategory.ANIMALS
            "objects".uppercase() -> GameCategory.OBJECTS
            "food".uppercase() -> GameCategory.FOOD
            "colors".uppercase() -> GameCategory.COLORS
            else -> GameCategory.ANIMALS
        }
    }

    fun mapToLevel(value: Int?) = with(value) {
        when (this) {
            1 -> GameLevel.ELECTION
            2 -> GameLevel.TYPING
            else -> GameLevel.ELECTION
        }
    }
}
