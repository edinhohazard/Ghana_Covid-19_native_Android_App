package com.sam.ui.adapter.viewholders

import android.view.View
import com.sam.R
import com.sam.databinding.ItemTextBinding
import com.sam.ui.adapter.BaseViewHolder
import com.sam.ui.base.BaseViewItem
import com.sam.util.ext.gone
import com.sam.util.ext.visible


data class TextItem(
    val textResId: Int? = null,
    val textActionResId: Int? = null
) : BaseViewItem

class TextItemViewHolder(itemView: View) : BaseViewHolder<TextItem>(itemView) {
    private val binding: ItemTextBinding = ItemTextBinding.bind(itemView)

    override fun setOnClickListener(listener: (View) -> Unit) {
        binding.textAction.setOnClickListener(listener)
    }

    override fun bind(item: TextItem) {
        with(binding) {
            root.context?.let { context ->
                textTitle.text = item.textResId?.let { context.getString(it) }.orEmpty()
                if (item.textActionResId != null) {
                    with(textAction) {
                        visible()
                        text = context.getString(item.textActionResId)
                    }
                } else {
                    textAction.gone()
                }
            }
        }
    }

    companion object {
        const val LAYOUT = R.layout.item_text
    }
}