package com.aktasbdr.cryptocase.feature_crypto.presentation.pairlist

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.aktasbdr.cryptocase.R
import com.aktasbdr.cryptocase.core.presentation.extensions.inflater
import com.aktasbdr.cryptocase.databinding.ItemPairBinding
import com.aktasbdr.cryptocase.feature_crypto.domain.model.Ticker
import com.aktasbdr.cryptocase.feature_crypto.presentation.pairlist.PairListAdapter.PairListItem
import com.aktasbdr.cryptocase.feature_crypto.presentation.pairlist.PairListAdapter.PairListItemViewHolder
import kotlin.math.absoluteValue

class PairListAdapter(
    private val onFavoriteClicked: ((PairListItem) -> Unit)? = null,
    private val onItemClicked: ((PairListItem) -> Unit)? = null
) : ListAdapter<PairListItem, PairListItemViewHolder>(PairListDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PairListItemViewHolder(
        ItemPairBinding.inflate(
            parent.context.inflater,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: PairListItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class PairListItemViewHolder(
        private val binding: ItemPairBinding
    ) : ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: PairListItem) = with(binding) {
            ivFavorite.apply {
                setImageResource(item.favoriteImageResId)
                setOnClickListener { onFavoriteClicked?.invoke(item) }
            }
            tvPairName.text = item.pairName

            tvLast.apply {
                text = context.getString(R.string.value_format, item.ticker.last)
            }

            tvDailyPercent.apply {
                text = context.getString(R.string.percentage_format, item.dailyPercent)
                setTextColor(ContextCompat.getColor(context, item.dailyPercentColorResId))
            }

            tvVolumeAndNumeratorName.apply {
                val formattedVolume = context.getString(R.string.value_format, item.ticker.volume)
                text = "$formattedVolume ${item.ticker.numeratorSymbol}"
            }

            root.setOnClickListener {
                onItemClicked?.invoke(item)
            }
        }
    }

    object PairListDiffCallback : DiffUtil.ItemCallback<PairListItem>() {
        override fun areItemsTheSame(
            oldItem: PairListItem,
            newItem: PairListItem
        ): Boolean {
            return oldItem.ticker.pair == newItem.ticker.pair
        }

        override fun areContentsTheSame(
            oldItem: PairListItem,
            newItem: PairListItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    data class PairListItem(
        val ticker: Ticker,
        val isFavorite: Boolean
    ) {

        val favoriteImageResId: Int
            get() = if (isFavorite) R.drawable.ic_yellow_star else R.drawable.ic_gray_star

        val pairName: String
            get() = ticker.pairNormalized.replace("_", "/")

        val dailyPercent: Double
            get() = ticker.dailyPercent.absoluteValue

        val dailyPercentColorResId: Int
            get() = when {
                ticker.dailyPercent > 0 -> R.color.vibrant_green
                ticker.dailyPercent < 0 -> R.color.bright_red
                else -> R.color.soft_gray
            }
    }
}
