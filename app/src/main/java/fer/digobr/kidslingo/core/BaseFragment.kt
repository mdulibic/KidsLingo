package fer.digobr.kidslingo.core

import timber.log.Timber
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController

abstract class BaseFragment : Fragment() {

    protected val svm: SharedViewModel by activityViewModels()
    private val navController by lazy { findNavController() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        svm.navCommand.observe(viewLifecycleOwner) {
            val content: NavCommand? = it.getContentIfNotHandled()
            try {
                content?.let { command ->
                    when (command) {
                        is NavCommand.To -> {
                            try {
                                navController.navigate(
                                    command.directions
                                )
                            } catch (exc: Exception) {
                                Timber.w(exc)
                                navController.navigate(command.directions)
                            }
                        }

                        is NavCommand.Up -> {
                            navController.navigateUp()
                        }
                    }
                }
            } catch (exc: Exception) {
                Timber.e(exc, "Failed to navigate $content")
            }
        }
    }
}
