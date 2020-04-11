package com.sam.ui.base

import com.sam.ui.adapter.ItemTypeFactory

interface BaseViewItem {
    fun typeOf(itemFactory: ItemTypeFactory): Int = itemFactory.type(this)
}
