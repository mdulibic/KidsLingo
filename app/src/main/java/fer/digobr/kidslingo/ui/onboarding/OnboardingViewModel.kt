package fer.digobr.kidslingo.ui.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fer.digobr.kidslingo.domain.SessionManager
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

    fun isUserOnboarded() = sessionManager.username != null
    fun onStartClicked(username: String) {
        sessionManager.username = username
    }

    fun saveDeviceId(deviceId: String) {
        if (sessionManager.deviceId == null) {
            sessionManager.deviceId = deviceId
        }
    }
}
