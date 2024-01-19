package fer.digobr.kidslingo.ui.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fer.digobr.kidslingo.domain.SessionManager
import fer.digobr.kidslingo.domain.usecase.GetStatisticsUseCase
import fer.digobr.kidslingo.ui.home.model.HomeUiState
import fer.digobr.kidslingo.ui.statistics.model.StatistcsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    getStatisticsUseCase: GetStatisticsUseCase,
    sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<StatistcsUiState?>(null)
    val uiState: StateFlow<StatistcsUiState?> = _uiState

    val statistics = getStatisticsUseCase(sessionManager.deviceId ?: "")
        .map {

        }
        .distinctUntilChanged()


    init {
//        viewModelScope.launch {
//            statistics.collectLatest {
//                _uiState.value = it
//            }
//        }
    }
}
