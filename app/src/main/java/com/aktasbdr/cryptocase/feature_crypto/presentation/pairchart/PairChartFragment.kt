package com.aktasbdr.cryptocase.feature_crypto.presentation.pairchart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.aktasbdr.cryptocase.R
import com.aktasbdr.cryptocase.core.presentation.extensions.collectState
import com.aktasbdr.cryptocase.core.presentation.extensions.viewBinding
import com.aktasbdr.cryptocase.databinding.FragmentPairChartBinding
import com.aktasbdr.cryptocase.feature_crypto.presentation.components.CustomMarker
import com.github.mikephil.charting.animation.Easing.EaseInExpo
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PairChartFragment : Fragment() {

    private val binding by viewBinding(FragmentPairChartBinding::bind)

    private val viewModel: PairChartViewModel by viewModels()

    private val args: PairChartFragmentArgs by navArgs()

    private val customMarker by lazy {
        CustomMarker(requireContext(), R.layout.view_marker)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_pair_chart, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectState(viewModel.uiState, ::renderView)
        viewModel.init(args.pairNormalized.replace("_", ""))
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetch()
    }

    private fun renderView(uiState: PairChartUiState) = with(binding) {
        val titleWithChart = "            ${args.pairNormalized.replace("_", "/")} Chart"
        (requireActivity() as AppCompatActivity).supportActionBar?.title = titleWithChart

        val lineDataSet = LineDataSet(uiState.entries, uiState.symbol).apply {
            setDrawFilled(true)
            setDrawValues(false)
            setDrawCircles(false)
            lineWidth = 2f
            fillDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.bg_pair_chart)
        }

        lineChart.apply {
            data = LineData(lineDataSet)
            axisLeft.isEnabled = false
            axisRight.textColor = ContextCompat.getColor(context, R.color.light_gray)
            axisRight.setDrawAxisLine(false)
            xAxis.isEnabled = false
            description.isEnabled = false
            isDoubleTapToZoomEnabled = false
            legend.isEnabled = false
            marker = customMarker
            setTouchEnabled(true)
            setPinchZoom(false)
            animateX(1000, EaseInExpo)
        }
    }

}
