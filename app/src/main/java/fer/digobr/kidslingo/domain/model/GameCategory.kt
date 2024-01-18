package fer.digobr.kidslingo.domain.model

import fer.digobr.kidslingo.R

enum class GameCategory(val value: String, val titleRes: Int) {
    COLORS(value = "colors".uppercase(), titleRes = R.string.category_colors),
    ANIMALS(value = "animals".uppercase(), titleRes = R.string.category_animals),
    FOOD(value = "food".uppercase(), titleRes = R.string.category_food),
    OBJECTS(value = "objects".uppercase(), titleRes = R.string.category_objects),
}
