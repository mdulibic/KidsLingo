package fer.digobr.kidslingo.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import fer.digobr.kidslingo.core.BaseFragment
import fer.digobr.kidslingo.databinding.FragmentGameBinding
import fer.digobr.kidslingo.theme.KidsLingoTheme
import fer.digobr.kidslingo.ui.game.view.GameScreen
import fer.digobr.kidslingo.ui.home.HomeFragmentDirections
import fer.digobr.kidslingo.ui.home.HomeViewModel
import fer.digobr.kidslingo.ui.home.view.HomeScreen
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GameFragment : BaseFragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    private val gameViewModel: GameViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeView.setContent {
            KidsLingoTheme {
                GameScreen(gameViewModel = gameViewModel)
            }
            observeData()
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            gameViewModel.exitGame.collectLatest {
                svm.navigate(GameFragmentDirections.actionNavGameToNavHome())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
