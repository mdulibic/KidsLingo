package fer.digobr.kidslingo.domain.model

import fer.digobr.kidslingo.R

enum class GameLanguage(val value: String, val titleRes: Int) {
    ENGLISH(value = "english", titleRes = R.string.language_english),
    FRENCH(value = "french", titleRes = R.string.language_french),
    GERMAN(value = "german", titleRes = R.string.language_german),
    ITALIAN(value = "italian", titleRes = R.string.language_italian)
}
