package fer.digobr.kidslingo.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import fer.digobr.kidslingo.databinding.FragmentGameBinding
import fer.digobr.kidslingo.theme.KidsLingoTheme
import fer.digobr.kidslingo.ui.game.view.GameScreen
import fer.digobr.kidslingo.ui.home.HomeViewModel
import fer.digobr.kidslingo.ui.home.view.HomeScreen

@AndroidEntryPoint
class GameFragment : Fragment() {

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
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
