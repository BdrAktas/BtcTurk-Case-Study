package com.aktasbdr.cryptocase.presentation.pairlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.aktasbdr.cryptocase.R
import com.aktasbdr.cryptocase.databinding.FragmentPairListBinding
import com.aktasbdr.cryptocase.presentation.pairlist.PairListUiEvent.NavigateToPairChart
import com.aktasbdr.cryptocase.core.presentation.extensions.collectEvent
import com.aktasbdr.cryptocase.core.presentation.extensions.collectState
import com.aktasbdr.cryptocase.core.presentation.extensions.viewBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
        setupErrorHandling()
        collectState(viewModel.uiState, ::renderView)
        collectEvent(viewModel.uiEvent, ::handleEvent)
    }

    override fun onResume() {
        super.onResume()
//        viewModel.onNavigateEnd()
        viewModel.fetch()
    }

    private fun initView() = with(binding) {
        println("InitView called") // Debug log

        // SwipeRefreshLayout setup
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            viewModel.fetch()
        }

        // Favorites RecyclerView setup
        rvFavorites.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = favoriteListAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL))
            setHasFixedSize(true)
        }

        // Pairs RecyclerView setup
        rvPairs.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = pairListAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            setHasFixedSize(true)
        }
    }

    private fun setupErrorHandling() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { state ->
                    binding.loadingView.isVisible = state.isLoading
                }
        }
    }

    private fun showError(message: String) {
        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_LONG
        ).show()
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
//                    viewModel.onNavigateStart()
                    findNavController().popBackStack()
                }
            }
        )
    }

    private fun handleEvent(uiEvent: PairListUiEvent) {
        when (uiEvent) {
            is NavigateToPairChart -> {
//                viewModel.onNavigateStart()
                findNavController().navigate(
                    PairListFragmentDirections.navigateToPairChart(uiEvent.pairNormalized)
                )
            }
            is PairListUiEvent.ShowError -> showError(uiEvent.message)
        }
    }

    private fun renderView(uiState: PairListUiState) = with(binding) {
        lifecycleScope.launch(Dispatchers.Main) {
            loadingView.isVisible = uiState.isLoading

            if (!uiState.isLoading) {
                favoriteListAdapter.submitList(uiState.favoriteItems)
                pairListAdapter.submitList(uiState.pairItems)
                clFavorites.isVisible = uiState.isFavoriteLayoutVisible
            }
        }
    }
}
