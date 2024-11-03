package com.aktasbdr.cryptocase.presentation.pairchart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.github.mikephil.charting.animation.Easing.EaseInExpo
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.aktasbdr.cryptocase.R
import com.aktasbdr.cryptocase.databinding.FragmentPairChartBinding
import com.aktasbdr.cryptocase.core.presentation.extensions.collectEvent
import com.aktasbdr.cryptocase.core.presentation.extensions.collectState
import com.aktasbdr.cryptocase.core.presentation.extensions.viewBinding
import com.aktasbdr.cryptocase.presentation.components.CustomMarker
import com.github.mikephil.charting.charts.LineChart
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PairChartFragment : Fragment() {

    private val binding by viewBinding(FragmentPairChartBinding::bind)
    private val viewModel: PairChartViewModel by viewModels()
    private val args: PairChartFragmentArgs by navArgs()
    private val customMarker by lazy {
        CustomMarker(requireContext(), R.layout.view_marker)
    }
    private var currentJob: Job? = null
    private var _chart: LineChart? = null // Chart referansını tutmak için
    private var isBackPressed = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_pair_chart, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _chart = binding.lineChart // Chart referansını kaydet
        setupChart()
        setupErrorHandling()
        collectState(viewModel.uiState, ::renderView)
        collectEvent(viewModel.uiEvent, ::handleEvent)
        viewModel.init(args.pairNormalized.replace("_", ""))
    }

    private fun setupChart() {
        _chart?.apply {
            axisLeft.isEnabled = false
            axisRight.apply {
                textColor = ContextCompat.getColor(context, R.color.light_gray)
                setDrawAxisLine(false)
            }
            xAxis.isEnabled = false
            description.isEnabled = false
            isDoubleTapToZoomEnabled = false
            legend.isEnabled = false
            marker = customMarker
            setTouchEnabled(true)
            setPinchZoom(false)
        }
    }

    private fun setupErrorHandling() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiEvent
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { event ->
                    when (event) {
                        is PairChartUiEvent.ShowError -> showError(event.message)
                    }
                }
        }
    }

    private fun handleEvent(uiEvent: PairChartUiEvent) {
        when (uiEvent) {
            is PairChartUiEvent.ShowError -> showError(uiEvent.message)
        }
    }

    private fun showError(message: String) {
        view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun renderView(uiState: PairChartUiState) {
        if (!isAdded) return

        currentJob?.cancel()

        currentJob = viewLifecycleOwner.lifecycleScope.launch {
            val titleWithChart = "            ${args.pairNormalized.replace("_", "/")} Chart"
            activity?.let {
                (it as? AppCompatActivity)?.supportActionBar?.title = titleWithChart
            }

            if (uiState.entries.isEmpty()) return@launch

            val lineDataSet = LineDataSet(uiState.entries, uiState.symbol).apply {
                setDrawFilled(true)
                setDrawValues(false)
                setDrawCircles(false)
                lineWidth = 2f
                context?.let { ctx ->
                    fillDrawable = ContextCompat.getDrawable(ctx, R.drawable.bg_pair_chart)
                }
            }

            _chart?.apply {
                data = LineData(lineDataSet)
                animateX(500, EaseInExpo)
                invalidate()
            }
        }
    }

    // Back tuşu kontrolü için
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        requireActivity().onBackPressedDispatcher.addCallback(this) {
//            isBackPressed = true
//            viewModel.clear()
//            remove() // Callback'i kaldır
//            requireActivity().onBackPressed() // Normal back işlemini yap
//        }
    }

    override fun onResume() {
        super.onResume()
        if (!isBackPressed) { // Sadece geri tuşuna basılmadıysa fetch yap
            viewModel.fetch()
        }
    }

    override fun onDestroyView() {
        currentJob?.cancel()
        _chart?.clear()
        _chart = null
        isBackPressed = true // Geri tuşuna basıldığını işaretle
        viewModel.clear() // ViewModel'a haber ver
        super.onDestroyView()
    }

    override fun onDestroy() {
        currentJob?.cancel()
        super.onDestroy()
    }
}