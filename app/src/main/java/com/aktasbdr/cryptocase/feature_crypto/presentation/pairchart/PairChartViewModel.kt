package com.aktasbdr.cryptocase.feature_crypto.presentation.pairchart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aktasbdr.cryptocase.core.data.remote.NetworkResult
import com.aktasbdr.cryptocase.feature_crypto.domain.usecase.FetchKlineData
import com.aktasbdr.cryptocase.feature_crypto.domain.usecase.ShowError
import com.aktasbdr.cryptocase.feature_crypto.domain.usecase.ShowLoading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PairChartViewModel @Inject constructor(
    private val showLoading: ShowLoading,
    private val showError: ShowError,
    private val fetchKlineData: FetchKlineData
) : ViewModel() {

    private val _uiState = MutableStateFlow(PairChartUiState())
    val uiState: StateFlow<PairChartUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<PairChartUiEvent>()
    val uiEvent: SharedFlow<PairChartUiEvent> = _uiEvent.asSharedFlow()

    fun init(pairName: String) {
        _uiState.update { it.copy(symbol = pairName) }
    }

    fun fetch() {
        viewModelScope.launch {
            showLoading(isLoading = true)

            when (val result = fetchKlineData(_uiState.value.symbol)) {
                is NetworkResult.Success -> {
                    _uiState.update { state ->
                        state.copy(klineData = result.data)
                    }
                }

                is NetworkResult.Error -> {
                    showError(result.exception)
                }

                NetworkResult.Loading -> Unit
            }

            showLoading(isLoading = false)
        }
    }
}
