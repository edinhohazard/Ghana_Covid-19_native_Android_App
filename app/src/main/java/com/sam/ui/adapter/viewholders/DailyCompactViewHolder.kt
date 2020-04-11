package com.sam.ui.adapter.viewholders

import android.view.View
import com.sam.R
import com.sam.databinding.ItemDailyCompactBinding
import com.sam.ui.adapter.BaseViewHolder
import com.sam.util.NumberUtils

class DailyCompactViewHolder(itemView: View) : BaseViewHolder<DailyItem>(itemView) {

    private val binding = ItemDailyCompactBinding.bind(itemView)

    override fun bind(item: DailyItem) {
        with(binding) {
            txtDate.text = NumberUtils.formatStringDate(item.reportDate)

            root.context?.let {
                txtConfirmed.text = it.getString(R.string.confirmed_case_count, NumberUtils.numberFormat(item.deltaConfirmed))
                txtRecovered.text = it.getString(R.string.recovered_case_count, NumberUtils.numberFormat(item.deltaRecovered))
            }
        }
    }

    override fun setOnClickListener(listener: (View) -> Unit) {
        // no op
    }

    companion object {
        val LAYOUT = R.layout.item_daily_compact
    }
}