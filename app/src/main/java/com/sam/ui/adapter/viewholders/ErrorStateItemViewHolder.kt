package com.sam.ui.adapter.viewholders

import android.view.View
import com.sam.R
import com.sam.databinding.ItemErrorStateBinding
import com.sam.ui.adapter.BaseViewHolder
import com.sam.ui.base.BaseViewItem


class ErrorStateItem(
    val titleResId: Int,
    val subtitleResId: Int
): BaseViewItem

class ErrorStateItemViewHolder(itemView: View) : BaseViewHolder<ErrorStateItem>(itemView) {

    private val binding: ItemErrorStateBinding = ItemErrorStateBinding.bind(itemView)

    override fun setOnClickListener(listener: (View) -> Unit) {
        //Listener
        binding.textRetry.setOnClickListener { listener.invoke(it) }
    }

    override fun bind(item: ErrorStateItem) {
        with(binding){
            textTitle.text = itemView.context.getString(item.titleResId)
            textSubtitle.text = itemView.context.getString(item.subtitleResId)
        }
    }

    companion object {
        const val LAYOUT = R.layout.item_error_state
    }
}