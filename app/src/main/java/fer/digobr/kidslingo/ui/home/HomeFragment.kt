package fer.digobr.kidslingo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import fer.digobr.kidslingo.core.BaseFragment
import fer.digobr.kidslingo.databinding.FragmentHomeBinding
import fer.digobr.kidslingo.theme.KidsLingoTheme
import fer.digobr.kidslingo.ui.home.view.HomeScreen
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeView.setContent {
            KidsLingoTheme {
                HomeScreen(
                    homeViewModel = homeViewModel
                )
            }
        }
        observeData()
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.startGame.collectLatest {
                svm.navigate(HomeFragmentDirections.actionNavHomeToNavGame())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
