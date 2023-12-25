package fer.digobr.kidslingo.domain.model

import fer.digobr.kidslingo.R

enum class GameLevel(val value: Int, val titleRes: Int) {
    ELECTION(value = 1, titleRes = R.string.level_election),
    TYPING(value = 2, titleRes = R.string.level_typing)
}
