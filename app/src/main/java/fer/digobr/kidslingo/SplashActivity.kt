package fer.digobr.kidslingo

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import fer.digobr.kidslingo.databinding.ActivitySplashBinding
import fer.digobr.kidslingo.theme.KidsLingoTheme
import fer.digobr.kidslingo.ui.onboarding.OnboardingScreen
import fer.digobr.kidslingo.ui.onboarding.OnboardingViewModel

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    private val onboardingViewModel: OnboardingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (onboardingViewModel.isUserOnboarded()) {
            navigateToHome()
        }

        binding.composeView.setContent {
            KidsLingoTheme {
                OnboardingScreen(
                    onStartClick = {
                        onboardingViewModel.onStartClicked(it)
                        navigateToHome()
                    }
                )
            }
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
