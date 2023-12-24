package fer.digobr.kidslingo.ui.game.model

import fer.digobr.kidslingo.domain.model.GameItem

data class GameUiState(
    val gameItem: GameItem,
    val imageUrl: String?,
    val levelLabelRes: Int,
    val gameQuestionLabelRes: Int,
    val roundCount: Int,
    val ctaButtonRes: Int,
    val gameItemsCount: Int
)
