package com.sam.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sam.R
import com.sam.ui.adapter.viewholders.DailyItem
import com.sam.ui.adapter.viewholders.DailyItemViewHolder


/**
 * fiificodes2020-02-06.
 */


class DailyAdapter(
    private val listener: () -> Unit = {}
) : RecyclerView.Adapter<DailyItemViewHolder>() {
    private val itemList = mutableListOf<DailyItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DailyItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_daily,
                parent,
                false
            )
        )

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: DailyItemViewHolder, position: Int) {
        holder.bind(itemList[holder.adapterPosition])
    }

    fun addAll(list: List<DailyItem>){
        with(itemList){
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }


}