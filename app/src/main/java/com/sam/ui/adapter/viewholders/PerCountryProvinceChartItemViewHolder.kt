package com.sam.ui.adapter.viewholders

import android.view.View
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.sam.R
import com.sam.databinding.ItemRegionPercountryGraphBinding
import com.sam.ui.adapter.BaseViewHolder
import com.sam.ui.base.BaseViewItem
import com.sam.util.ext.color
import com.sam.util.ext.getString
import com.sam.util.ext.visible

data class PerCountryRegionGraphItem(
    val listData: List<PerCountryRegionItem>
) : BaseViewItem

data class PerCountryRegionItem(
    val id: Int,
    val name: String,
    val totalConfirmed: Int,
    val totalRecovered: Int,
    val totalDeath: Int
)

class PerCountryRegionGraphItemViewHolder(itemView: View) :
    BaseViewHolder<PerCountryRegionGraphItem>(itemView) {
    private val binding: ItemRegionPercountryGraphBinding =
        ItemRegionPercountryGraphBinding.bind(itemView)

    override fun setOnClickListener(listener: (View) -> Unit) {
        binding.root.setOnClickListener { listener.invoke(it) }
    }

    override fun bind(item: PerCountryRegionGraphItem) {
        setupChart(item.listData)
        setupData(item.listData)
    }

    private fun setupChart(data: List<PerCountryRegionItem>) {
        with(binding.barChart) {
            animateX(1500)
            legend.textColor = color(R.color.white)
            setMaxVisibleValueCount(40)

            xAxis.isEnabled = false
            axisLeft.textColor = color(R.color.cool_grey)
            axisRight.textColor = color(R.color.cool_grey)
            description.isEnabled = false

            axisRight.enableGridDashedLine(10f, 10f, 2f)
            axisLeft.enableGridDashedLine(10f, 10f, 2f)
            xAxis.enableGridDashedLine(10f, 10f, 2f)

            setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
                override fun onNothingSelected() {
                }

                override fun onValueSelected(e: Entry?, h: Highlight?) {
                    val barEnrty = e as? BarEntry
                    with(binding) {
                        barEnrty?.let { dataBarEnty ->
                            layoutData.visible()
                            txtRegion.text = dataBarEnty.data.toString()
                            txtConfirmed.text = getString(
                                R.string.confirmed_case_count,
                                dataBarEnty.yVals[0].toInt().toString()
                            )
                            txtDeath.text = getString(
                                R.string.death_case_count,
                                dataBarEnty.yVals[1].toInt().toString()
                            )
                            txtRecovered.text = getString(
                                R.string.recovered_case_count,
                                dataBarEnty.yVals[2].toInt().toString()
                            )

                        }

                    }
                }
            })

        }

    }

    private fun setupData(data: List<PerCountryRegionItem>) {
        val values = data.mapIndexed { index, perCountryRegionItem ->
            BarEntry(
                index.toFloat(), floatArrayOf(
                    perCountryRegionItem.totalConfirmed.toFloat(),
                    perCountryRegionItem.totalDeath.toFloat(),
                    perCountryRegionItem.totalRecovered.toFloat()
                ), perCountryRegionItem.name
            )
        }
        val barDataSet = BarDataSet(values, getString(R.string.case_per_region_chart))
        barDataSet.stackLabels = arrayOf(
            getString(R.string.confirmed),
            getString(R.string.deaths),
            getString(R.string.recovered)
        )
        barDataSet.setColors(
            color(R.color.color_confirmed),
            color(R.color.color_death),
            color(R.color.color_recovered)
        )
        barDataSet.setDrawValues(false)
        val dataSet = arrayListOf<IBarDataSet>()
        dataSet.add(barDataSet)
        binding.barChart.data = BarData(dataSet)
        binding.barChart.invalidate()
    }


    companion object {
        const val LAYOUT = R.layout.item_region_percountry_graph
    }
}