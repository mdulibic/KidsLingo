package fer.digobr.kidslingo.ui.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fer.digobr.kidslingo.domain.SessionManager
import fer.digobr.kidslingo.domain.usecase.GetStatisticsUseCase
import fer.digobr.kidslingo.ui.statistics.model.StatisticsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val getStatisticsUseCase: GetStatisticsUseCase,
    private val sessionManager: SessionManager,
    private val statisticsMapper: StatisticsMapper
) : ViewModel() {

    private val _uiState = MutableStateFlow<StatisticsUiState?>(null)
    val uiState: StateFlow<StatisticsUiState?> = _uiState

    val statistics = getStatisticsUseCase(sessionManager.deviceId ?: "")
        .map {
            Timber.d("statistics $it")
            statisticsMapper.map(it)
        }
        .distinctUntilChanged()

    init {
        fetchStatistic()
    }

    fun fetchStatistic() {
        val deviceId = sessionManager.deviceId ?: ""

        // Execute the API call use case
        getStatisticsUseCase(deviceId)
            .map {
                Timber.d("statistics $it")
                statisticsMapper.map(it)
            }
            .distinctUntilChanged()
            .onEach {
                _uiState.value = it
            }
            .launchIn(viewModelScope)
    }
}
