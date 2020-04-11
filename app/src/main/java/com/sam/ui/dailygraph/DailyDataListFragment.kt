package com.sam.ui.dailygraph

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sam.databinding.FragmentDailyListBinding
import com.sam.ui.adapter.DailyFactoryImpl
import com.sam.ui.adapter.VisitableRecyclerAdapter
import com.sam.ui.base.BaseFragment
import com.sam.util.ext.observe
import org.koin.android.viewmodel.ext.android.sharedViewModel

class DailyDataListFragment : BaseFragment() {

    private val viewModel by sharedViewModel<DailyGraphViewModel>()

    lateinit var binding: FragmentDailyListBinding
    private val dailyAdapter by lazy {
        VisitableRecyclerAdapter(DailyFactoryImpl())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDailyListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = dailyAdapter
        }
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.loadRemoteDailyData()
        }
    }

    override fun observeChange() {
        observe(viewModel.loading, ::swipeLoading)
        observe(viewModel.dailyItemsVH) { items ->
            dailyAdapter.submitList(items)
        }
    }

    private fun swipeLoading(loading: Boolean) {
        with(binding.swipeRefresh) {
            post { isRefreshing = loading }
        }
    }

    companion object {
        fun newInstance() = DailyDataListFragment()
    }

}