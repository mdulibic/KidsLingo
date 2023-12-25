package fer.digobr.kidslingo.domain.model

import fer.digobr.kidslingo.R

enum class GameCategory(val value: String, val titleRes: Int) {
    COLORS(value = "colors", titleRes = R.string.category_colors),
    ANIMALS(value = "animals", titleRes = R.string.category_animals),
    FOOD(value = "food", titleRes = R.string.category_food),
    OBJECTS(value = "objects", titleRes = R.string.category_objects),
}
