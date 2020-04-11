package com.sam.ui.adapter.viewholders

import android.view.View
import com.sam.R
import com.sam.ui.adapter.BaseViewHolder
import com.sam.ui.base.BaseViewItem


class LoadingStateItem: BaseViewItem

class LoadingStateItemViewHolder(itemView: View) : BaseViewHolder<LoadingStateItem>(itemView) {

    override fun setOnClickListener(listener: (View) -> Unit) {
        //Listener
    }

    override fun bind(item: LoadingStateItem) {

    }

    companion object {
        const val LAYOUT = R.layout.item_loading_state
    }
}