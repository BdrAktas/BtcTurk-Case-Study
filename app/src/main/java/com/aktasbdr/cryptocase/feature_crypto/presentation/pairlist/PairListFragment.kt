package com.aktasbdr.cryptocase.feature_crypto.presentation.pairlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.aktasbdr.cryptocase.R
import com.aktasbdr.cryptocase.core.presentation.extensions.collectEvent
import com.aktasbdr.cryptocase.core.presentation.extensions.collectState
import com.aktasbdr.cryptocase.core.presentation.extensions.viewBinding
import com.aktasbdr.cryptocase.databinding.FragmentPairListBinding
import com.aktasbdr.cryptocase.feature_crypto.presentation.pairlist.PairListUiEvent.NavigateToPairChart
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PairListFragment : Fragment() {

    private val binding by viewBinding(FragmentPairListBinding::bind)

    private val viewModel: PairListViewModel by viewModels()

    private val pairListAdapter: PairListAdapter by lazy {
        PairListAdapter(
            onFavoriteClicked = { viewModel.onFavoriteClicked(it) },
            onItemClicked = { viewModel.onPairClicked(it.ticker.pairNormalized) }
        )
    }

    private val favoriteListAdapter: FavoriteListAdapter by lazy {
        FavoriteListAdapter {
            viewModel.onPairClicked(it.favorite.pairName)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_pair_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        collectState(viewModel.uiState, ::renderView)
        collectEvent(viewModel.uiEvent, ::handleEvent)
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetch()
    }

    private fun initView() = with(binding) {
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            viewModel.fetch(showShimmer = false)        }

        rvFavorites.adapter = favoriteListAdapter
        rvPairs.adapter = pairListAdapter
    }



    private fun renderView(uiState: PairListUiState) = with(binding) {
        shimmerLayout.shimmerFrameLayout.isVisible = uiState.isShimmerVisible
        swipeRefreshLayout.isEnabled = !uiState.isShimmerVisible
        clFavorites.isVisible = uiState.isFavoriteLayoutVisible && !uiState.isShimmerVisible
        tvPairs.isVisible = !uiState.isShimmerVisible
        rvPairs.isVisible = !uiState.isShimmerVisible

        if (!uiState.isShimmerVisible) {
            favoriteListAdapter.submitList(uiState.favoriteItems)
            pairListAdapter.submitList(uiState.pairItems)
        }
    }

    private fun handleEvent(uiEvent: PairListUiEvent) {
        when (uiEvent) {
            is NavigateToPairChart -> {
                val directions = PairListFragmentDirections.navigateToPairChart(
                    pairNormalized = uiEvent.pairNormalized
                )
                findNavController().navigate(directions)
            }
        }
    }
}
