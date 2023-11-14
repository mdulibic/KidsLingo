package fer.digobr.kidslingo.ui.gameinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import fer.digobr.kidslingo.databinding.FragmentGameInfoBinding
import fer.digobr.kidslingo.ui.game.GameViewModel
@AndroidEntryPoint

class GameInfoFragment : Fragment() {

    private var _binding: FragmentGameInfoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val gameViewModel =
            ViewModelProvider(this).get(GameViewModel::class.java)

        _binding = FragmentGameInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO: Function calls, etc.
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
