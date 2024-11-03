package com.aktasbdr.cryptocase.presentation.pairchart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aktasbdr.cryptocase.core.data.remote.SafeApiCall
import com.aktasbdr.cryptocase.core.data.remote.NetworkResult
import com.aktasbdr.cryptocase.domain.usecase.FetchKlineData
import com.aktasbdr.cryptocase.domain.usecase.ShowError
import com.aktasbdr.cryptocase.domain.usecase.ShowLoading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PairChartViewModel @Inject constructor(
    private val showLoading: ShowLoading,
    private val showError: ShowError,
    private val fetchKlineData: FetchKlineData,
    private val safeApiCall: SafeApiCall
) : ViewModel() {

    private val _uiState = MutableStateFlow(PairChartUiState())
    val uiState: StateFlow<PairChartUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<PairChartUiEvent>()
    val uiEvent: SharedFlow<PairChartUiEvent> = _uiEvent.asSharedFlow()

    private var fetchJob: Job? = null
    private var isCleared = false // Fragment destroy edildiğinde true olacak

    fun init(symbol: String) {
        _uiState.update { it.copy(symbol = symbol) }
    }

    fun fetch() {
        if (isCleared) return // Fragment destroy edildiyse fetch yapma

        fetchJob?.cancel()

        fetchJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("PairChart", "Fetching data for symbol: ${_uiState.value.symbol}")
                showLoading(true)

                when (val result = safeApiCall { fetchKlineData(_uiState.value.symbol) }) {
                    is NetworkResult.Success -> {
                        Log.d("PairChart", "Data fetched successfully")
                        withContext(Dispatchers.Default) {
                            _uiState.update { state ->
                                state.copy(klineData = result.data)
                            }
                        }
                    }
                    is NetworkResult.Error -> {
                        Log.e("PairChart", "Error fetching data: ${result.exception}")
                        handleError(result.exception)
                    }
                    NetworkResult.Loading -> Unit
                }
            } finally {
                showLoading(false)
            }
        }
    }



    override fun onCleared() {
        clear()
        super.onCleared()

    }
    fun clear() {
        isCleared = true
        fetchJob?.cancel()
        // İşin tamamen bitmesini bekleyin
        viewModelScope.launch {
            fetchJob?.join()
            Log.d("PairChart", "ViewModel cleared, cancelling all requests")
        }
    }

    private suspend fun handleError(throwable: Throwable) {
        showError(throwable)
        _uiEvent.emit(PairChartUiEvent.ShowError(throwable.message ?: "Unknown error occurred"))
    }

}