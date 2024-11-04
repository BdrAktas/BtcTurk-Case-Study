package com.aktasbdr.cryptocase.feature_crypto.presentation.pairlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aktasbdr.cryptocase.core.data.remote.NetworkResult
import com.aktasbdr.cryptocase.feature_crypto.domain.model.Ticker
import com.aktasbdr.cryptocase.feature_crypto.domain.usecase.FavoritePair
import com.aktasbdr.cryptocase.feature_crypto.domain.usecase.FetchFavoriteList
import com.aktasbdr.cryptocase.feature_crypto.domain.usecase.FetchTickerList
import com.aktasbdr.cryptocase.feature_crypto.domain.usecase.ShowError
import com.aktasbdr.cryptocase.feature_crypto.domain.usecase.ShowLoading
import com.aktasbdr.cryptocase.feature_crypto.domain.usecase.UnFavoritePair
import com.aktasbdr.cryptocase.feature_crypto.presentation.pairlist.FavoriteListAdapter.FavoriteListItem
import com.aktasbdr.cryptocase.feature_crypto.presentation.pairlist.PairListAdapter.PairListItem
import com.aktasbdr.cryptocase.feature_crypto.presentation.pairlist.PairListUiEvent.NavigateToPairChart
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PairListViewModel @Inject constructor(
    private val showLoading: ShowLoading,
    private val showError: ShowError,
    private val fetchFavoriteList: FetchFavoriteList,
    private val fetchTickerList: FetchTickerList,
    private val favoritePair: FavoritePair,
    private val unFavoritePair: UnFavoritePair
) : ViewModel() {

    private val _uiState = MutableStateFlow(PairListUiState())
    val uiState: StateFlow<PairListUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<PairListUiEvent>()
    val uiEvent: SharedFlow<PairListUiEvent> = _uiEvent.asSharedFlow()

    init {
        fetchFavoriteList()
            .map { _uiState.value.copy(favoriteItems = it.map(::FavoriteListItem)) }
            .onEach { state -> _uiState.emit(state).also { updatePairList(state.tickers) } }
            .launchIn(viewModelScope)
    }


    fun fetch() {
        viewModelScope.launch {
            showLoading(isLoading = true)

            when (val result = fetchTickerList()) {
                is NetworkResult.Success -> {
                    updatePairList(result.data)
                }
                is NetworkResult.Error -> {
                    showError(result.exception)
                }
                NetworkResult.Loading -> Unit
            }

            showLoading(isLoading = false)
        }
    }

    fun onFavoriteClicked(pairItem: PairListItem) {
        viewModelScope.launch {
            showLoading(isLoading = true)

            runCatching {
                if (pairItem.isFavorite) {
                    unFavoritePair(pairItem.ticker)
                } else {
                    favoritePair(pairItem.ticker)
                }
            }
                .onSuccess { }
                .onFailure { showError(it) }
                .also { showLoading(isLoading = false) }
        }
    }

    fun onPairClicked( pairNormalized: String) {
        viewModelScope.launch {
            val event = NavigateToPairChart(pairNormalized)
            _uiEvent.emit(event)
        }
    }

    private fun updatePairList(tickers: List<Ticker>) {
        _uiState.update { state ->
            val pairItems = tickers.map { PairListItem(it, state.isFavorite(it.pairNormalized)) }
            state.copy(pairItems = pairItems)
        }
    }
}
