package fer.digobr.kidslingo.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import fer.digobr.kidslingo.databinding.FragmentSettingsBinding
import fer.digobr.kidslingo.domain.SessionManager
import javax.inject.Inject
import fer.digobr.kidslingo.R


@AndroidEntryPoint
class SettingsFragment : Fragment() {

    @Inject
    lateinit var sessionManager: SessionManager
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonEngleski.setOnClickListener {
            onLanguageButtonClicked("Engleski")
        }

        binding.buttonNjemacki.setOnClickListener {
            onLanguageButtonClicked("Njemački")
        }

        binding.buttonFrancuski.setOnClickListener {
            onLanguageButtonClicked("Francuski")
        }

        binding.buttonSpanjolski.setOnClickListener {
            onLanguageButtonClicked("Španjolski")
        }

    }
    private fun onLanguageButtonClicked(language: String) {
        sessionManager.selectedLanguage = language
        updateButtonColor(language)
    }

    private fun updateButtonColor(selectedLanguage: String?) {

        binding.buttonEngleski.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.app_green))
        binding.buttonFrancuski.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.app_green))
        binding.buttonSpanjolski.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.app_green))
        binding.buttonNjemacki.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.app_green))

        when (selectedLanguage) {
            "Engleski" -> binding.buttonEngleski.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.app_orange))
            "Njemački" -> binding.buttonNjemacki.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.app_orange))
            "Francuski" -> binding.buttonFrancuski.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.app_orange))
            "Španjolski" -> binding.buttonSpanjolski.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.app_orange))

        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}