package com.sam.ui.percountry.ghana

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.sam.databinding.ActivityCountryGhanaBinding
import com.sam.ui.adapter.ItemTypeFactoryImpl
import com.sam.ui.adapter.VisitableRecyclerAdapter
import com.sam.ui.adapter.viewholders.DailyItem
import com.sam.ui.adapter.viewholders.TextItem
import com.sam.ui.base.BaseActivity
import com.sam.ui.base.BaseViewItem
import com.sam.ui.dailygraph.DailyGraphActivity
import com.sam.util.ext.observe
import org.koin.android.viewmodel.ext.android.viewModel

class CountryGhanaActivity : BaseActivity() {
    private val viewModel by viewModel<CountryGhanaViewModel>()
    private lateinit var binding: ActivityCountryGhanaBinding
    private val viewAdapter by lazy {
        VisitableRecyclerAdapter(
            ItemTypeFactoryImpl(),
            ::onItemClicked
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryGhanaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBarWithBackButton(binding.toolbar)
        initView()

        viewModel.loadData()

    }

    private fun initView() {
        with(binding.recyclerView) {
            adapter = viewAdapter
            setHasFixedSize(true)
        }
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.loadData()
        }
    }

    override fun observeChange() {
        observe(viewModel.items, ::onDataLoaded)
        observe(viewModel.toastMessage, ::showSnackbarMessage)
        observe(viewModel.loading, ::loadingSwipeRefresh)
    }

    private fun loadingSwipeRefresh(loading: Boolean) {
        with(binding.swipeRefresh) {
            post {
                isRefreshing = loading
            }
        }
    }

    private fun onDataLoaded(items: List<BaseViewItem>) {
        viewAdapter.submitList(items)
    }


    private fun onItemClicked(viewItem: BaseViewItem, view: View) {
        when (viewItem) {
            is DailyItem -> {

            }
            is TextItem -> {
                DailyGraphActivity.startActivity(this)
            }
        }
    }


    companion object {
        @JvmStatic
        fun startActivity(context: Context?) =
            context?.startActivity(Intent(context, CountryGhanaActivity::class.java))
    }
}
