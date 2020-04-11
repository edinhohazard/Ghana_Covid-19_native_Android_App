package com.sam.ui.adapter.viewholders

import android.view.View
import androidx.annotation.StringRes
import com.sam.R
import com.sam.databinding.ItemDailyPercountryBinding
import com.sam.ui.adapter.BaseViewHolder
import com.sam.ui.base.BaseViewItem
import com.sam.util.NumberUtils
import com.sam.util.ext.getString

data class PerCountryDailyItem(
    val id: Int = 0,
    val confirmed: Int = 0,
    val totalDeath: Int = 0,
    val totalRecovered: Int = 0,
    val totalConfirmed: Int = 0,
    val date: Long = 0,
    val day: Int = 0,
    @StringRes val info: Int
) : BaseViewItem

class PerCountryDailyItemViewHolder(itemView: View) :
    BaseViewHolder<PerCountryDailyItem>(itemView) {
    private val binding: ItemDailyPercountryBinding = ItemDailyPercountryBinding.bind(itemView)

    override fun setOnClickListener(listener: (View) -> Unit) {
        binding.root.setOnClickListener { listener.invoke(it) }
    }

    override fun bind(item: PerCountryDailyItem) {
        with(binding) {
            txtInformation.text = itemView.context.getString(item.info)
            txtDate.text = "${NumberUtils.formatShortDate(item.date)} ${getString(R.string.day_on, item.day.toString())}"
            txtConfirmed.text = getString(
                R.string.confirmed_case_count,
                NumberUtils.numberFormat(item.totalConfirmed)
            )
            txtDeath.text = getString(
                R.string.death_case_count,
                NumberUtils.numberFormat(item.totalDeath)
            )
            txtRcv.text = getString(
                R.string.recovered_case_count,
                NumberUtils.numberFormat(item.totalRecovered)
            )
            txtNewCase.text = getString(
                R.string.new_case_case_count,
                NumberUtils.numberFormat(item.confirmed)
            )

        }
    }


    companion object {
        const val LAYOUT = R.layout.item_daily_percountry
    }
}