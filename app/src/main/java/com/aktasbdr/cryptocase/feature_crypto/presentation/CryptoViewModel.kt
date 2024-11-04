package com.aktasbdr.cryptocase.feature_crypto.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aktasbdr.cryptocase.feature_crypto.domain.usecase.ShowError
import com.aktasbdr.cryptocase.feature_crypto.domain.usecase.ShowLoading
import com.aktasbdr.cryptocase.feature_crypto.presentation.CryptoUiEvent.ShowErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class CryptoViewModel @Inject constructor(
    showLoading: ShowLoading,
    showError: ShowError
) : ViewModel() {

    private val _uiState = MutableStateFlow(CryptoUiState())
    val uiState: StateFlow<CryptoUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<CryptoUiEvent>()
    val uiEvent: SharedFlow<CryptoUiEvent> = _uiEvent.asSharedFlow()

    init {
        showLoading()
            .map { _uiState.value.copy(isLoading = it) }
            .onEach { _uiState.emit(it) }
            .launchIn(viewModelScope)

        showError()
            .map { ShowErrorMessage(it) }
            .onEach { _uiEvent.emit(it) }
            .launchIn(viewModelScope)
    }
}
