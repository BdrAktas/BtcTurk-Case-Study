package com.aktasbdr.cryptocase.presentation.pairlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aktasbdr.cryptocase.core.data.remote.SafeApiCall
import com.aktasbdr.cryptocase.core.data.remote.NetworkResult
import com.aktasbdr.cryptocase.domain.model.Ticker
import com.aktasbdr.cryptocase.domain.usecase.*
import com.aktasbdr.cryptocase.presentation.pairlist.FavoriteListAdapter.FavoriteListItem
import com.aktasbdr.cryptocase.presentation.pairlist.PairListAdapter.PairListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PairListViewModel @Inject constructor(
    private val showLoading: ShowLoading,
    private val showError: ShowError,
    private val fetchFavoriteList: FetchFavoriteList,
    private val fetchTickerList: FetchTickerList,
    private val favoritePair: FavoritePair,
    private val unFavoritePair: UnFavoritePair,
    private val safeApiCall: SafeApiCall
) : ViewModel() {

    private val _uiState = MutableStateFlow(PairListUiState())
    val uiState: StateFlow<PairListUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<PairListUiEvent>()
    val uiEvent: SharedFlow<PairListUiEvent> = _uiEvent.asSharedFlow()

    private var fetchJob: Job? = null
    private var isNavigating = false

    init {
        observeFavorites()
    }

    private fun observeFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            fetchFavoriteList()
                .catch { error -> handleError(error) }
                .collect { favorites ->
                    val favoriteItems = favorites.map(::FavoriteListItem)
                    _uiState.update { state ->
                        state.copy(
                            favoriteItems = favoriteItems,
                            isFavoriteLayoutVisible = favoriteItems.isNotEmpty()
                        )
                    }

                    // Immediately update the pair list with current tickers
                    updatePairList(_uiState.value.tickers)
                }
        }
    }

    fun onFavoriteClicked(pairItem: PairListItem) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val result = safeApiCall {
                if (pairItem.isFavorite) {
                    unFavoritePair(pairItem.ticker)
                } else {
                    favoritePair(pairItem.ticker)
                }
            }

            when (result) {
                is NetworkResult.Success -> {
                    // Favorileri hemen güncelle, tam fetch'e gerek yok
                    updatePairListAfterFavoriteChange(pairItem)
                }
                is NetworkResult.Error -> {
                    handleError(result.exception)
                }
                NetworkResult.Loading -> Unit
            }

            _uiState.update { it.copy(isLoading = false) }
        }
    }
    fun fetch() {
        if (isNavigating) return

        fetchJob?.cancel()
        fetchJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                _uiState.update { it.copy(isLoading = true) }
                showLoading(true)

                when (val result = fetchTickerList()) {
                    is NetworkResult.Success -> {
                        withContext(Dispatchers.Default) {
                            updatePairList(result.data)
                        }
                    }
                    is NetworkResult.Error -> handleError(result.exception)
                    NetworkResult.Loading -> Unit
                }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
                showLoading(false)
            }
        }
    }


    fun onNavigateStart() {
        isNavigating = true
        fetchJob?.cancel()
    }

    fun onNavigateEnd() {
        isNavigating = false
    }

    private fun updatePairList(tickers: List<Ticker>) {
        _uiState.update { state ->
            val pairItems = tickers.map { ticker ->
                PairListItem(
                    ticker = ticker,
                    isFavorite = state.favoriteItems.any {
                        it.favorite.pairName == ticker.pairNormalized
                    }
                )
            }
            state.copy(
                pairItems = pairItems,
                tickers = tickers
            )
        }
    }




    fun onPairClicked(pairNormalized: String) {
        viewModelScope.launch {
            _uiEvent.emit(PairListUiEvent.NavigateToPairChart(pairNormalized))
        }
    }

    private fun updatePairListAfterFavoriteChange(changedItem: PairListItem) {
        _uiState.update { state ->
            val updatedPairItems = state.pairItems.map { item ->
                if (item.ticker.pairNormalized == changedItem.ticker.pairNormalized) {
                    item.copy(isFavorite = !item.isFavorite)
                } else {
                    item
                }
            }
            state.copy(pairItems = updatedPairItems)
        }
    }


    private suspend fun handleError(throwable: Throwable) {
        showError(throwable)
        _uiEvent.emit(PairListUiEvent.ShowError(throwable.message ?: "Unknown error occurred"))
    }
}

