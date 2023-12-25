package fer.digobr.kidslingo.ui.settings.model

import fer.digobr.kidslingo.R
import fer.digobr.kidslingo.domain.model.GameCategory
import fer.digobr.kidslingo.domain.model.GameLanguage
import fer.digobr.kidslingo.domain.model.GameLevel
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
            "english" -> GameLanguage.ENGLISH
            "french" -> GameLanguage.FRENCH
            "german" -> GameLanguage.GERMAN
            "italian" -> GameLanguage.ITALIAN
            else -> GameLanguage.ENGLISH
        }
    }

    fun mapToCategory(value: String?) = with(value) {
        when (this) {
            "animals" -> GameCategory.ANIMALS
            "objects" -> GameCategory.OBJECTS
            "food" -> GameCategory.FOOD
            "colors" -> GameCategory.COLORS
            else -> GameCategory.ANIMALS
        }
    }

    fun mapToLevel(value: String?) = with(value) {
        when (this) {
            "typing" -> GameLevel.TYPING
            "election" -> GameLevel.ELECTION
            else -> GameLevel.ELECTION
        }
    }


}
