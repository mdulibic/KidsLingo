package fer.digobr.kidslingo.domain.model

import fer.digobr.kidslingo.R

enum class GameLanguage(val value: String, val titleRes: Int) {
    ENGLISH(value = "english".uppercase(), titleRes = R.string.language_english),
    FRENCH(value = "french".uppercase(), titleRes = R.string.language_french),
    GERMAN(value = "german".uppercase(), titleRes = R.string.language_german),
    ITALIAN(value = "italian".uppercase(), titleRes = R.string.language_italian)
}
