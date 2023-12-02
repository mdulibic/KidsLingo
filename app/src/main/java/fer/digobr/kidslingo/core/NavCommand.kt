package fer.digobr.kidslingo.core

import androidx.navigation.NavDirections

sealed class NavCommand {
    data class To(val directions: NavDirections) : NavCommand()
    object Up : NavCommand()
}
