package fer.digobr.kidslingo.ui.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import fer.digobr.kidslingo.databinding.FragmentStatisticsBinding
import fer.digobr.kidslingo.theme.KidsLingoTheme
import fer.digobr.kidslingo.ui.settings.SettingsViewModel
import fer.digobr.kidslingo.ui.settings.view.SettingsScreen

@AndroidEntryPoint
class StatisticsFragment : Fragment() {

    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!

    private val statisticsViewModel: StatisticsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeView.setContent {
            KidsLingoTheme {
                StatisticsScreen(statisticsViewModel = statisticsViewModel)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        statisticsViewModel.fetchStatistic()
    }
}
