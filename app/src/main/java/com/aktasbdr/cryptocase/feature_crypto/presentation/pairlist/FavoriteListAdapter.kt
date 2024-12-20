package com.aktasbdr.cryptocase.feature_crypto.presentation.pairlist

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.aktasbdr.cryptocase.R
import com.aktasbdr.cryptocase.core.presentation.extensions.inflater
import com.aktasbdr.cryptocase.databinding.ItemFavoriteBinding
import com.aktasbdr.cryptocase.feature_crypto.domain.model.Favorite
import com.aktasbdr.cryptocase.feature_crypto.presentation.pairlist.FavoriteListAdapter.FavoriteListItem
import com.aktasbdr.cryptocase.feature_crypto.presentation.pairlist.FavoriteListAdapter.FavoriteListItemViewHolder
import kotlin.math.absoluteValue

class FavoriteListAdapter(
    private val onItemClicked: ((FavoriteListItem) -> Unit)? = null
) : ListAdapter<FavoriteListItem, FavoriteListItemViewHolder>(FavoriteListDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FavoriteListItemViewHolder(
        ItemFavoriteBinding.inflate(
            parent.context.inflater,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: FavoriteListItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class FavoriteListItemViewHolder(
        private val binding: ItemFavoriteBinding
    ) : ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: FavoriteListItem) = with(binding) {
            tvPairName.text = item.pairName

            tvLast.apply {
                text = context.getString(R.string.value_format, item.favorite.last)
            }

            tvDailyPercent.apply {
                text = context.getString(R.string.percentage_format, item.dailyPercent)
                setTextColor(ContextCompat.getColor(context, item.dailyPercentColorResId))
            }

            root.setOnClickListener {
                onItemClicked?.invoke(item)
            }
        }
    }

    object FavoriteListDiffCallback : DiffUtil.ItemCallback<FavoriteListItem>() {
        override fun areItemsTheSame(
            oldItem: FavoriteListItem,
            newItem: FavoriteListItem
        ): Boolean {
            return oldItem.favorite.pairName == newItem.favorite.pairName
        }

        override fun areContentsTheSame(
            oldItem: FavoriteListItem,
            newItem: FavoriteListItem
        ): Boolean {
            return oldItem.favorite == newItem.favorite
        }
    }

    data class FavoriteListItem(
        val favorite: Favorite
    ) {

        val pairName: String
            get() = favorite.pairName.replace("_", "/")

        val dailyPercent: Double
            get() = favorite.dailyPercent.absoluteValue

        val dailyPercentColorResId: Int
            get() = when {
                favorite.dailyPercent > 0 -> R.color.vibrant_green
                favorite.dailyPercent < 0 -> R.color.bright_red
                else -> R.color.light_gray
            }
    }
}
