package com.example.github_trending_repo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.github_trending_repo.R
import com.example.github_trending_repo.api.di.viewModel
import com.example.github_trending_repo.api.entity.TrendingRepository
import com.example.github_trending_repo.api.repository.ApiCallState
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import kotlin.coroutines.CoroutineContext


class HomeFragment : Fragment(), CoroutineScope, KodeinAware {
    override val kodein by kodein()
    private lateinit var job: Job
    private var cacheView: View? = null
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
    private val viewModel: SharedViewModel by viewModel()
    private val repoListAdapter: GroupAdapter<GroupieViewHolder> = GroupAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.home_fragment, container, false).also { cacheView = it }
    }

    override fun onStart() {
        super.onStart()
        setupObserver()
        setupUI()
        viewModel.getList(0, requireContext())
    }


    private fun setupUI() {
        rv_repo.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = repoListAdapter
        }
        swipe_refresh_layout.setOnRefreshListener {
            viewModel.getList(0, requireContext())
            swipe_refresh_layout.isRefreshing = false
        }
    }

    private fun setupObserver() {
        viewModel.getListState.observe(viewLifecycleOwner, Observer { it ->
            when (it) {
                is ApiCallState.ERROR -> {
                    swipe_refresh_layout.visibility = View.GONE
                    error_layout.visibility = View.VISIBLE
                }
                is ApiCallState.LOADING -> {
                    swipe_refresh_layout.isEnabled = false
                    repoListAdapter.clear()
                    for (i in 1 until 10) {
                        repoListAdapter.add(LoadingListCellItem())
                    }
                }
                is ApiCallState.COMPLETED -> {
                    swipe_refresh_layout.visibility = View.VISIBLE
                    error_layout.visibility = View.GONE
                }
            }
        })

        viewModel.mediator.observe(viewLifecycleOwner, Observer {})

        viewModel.trendingList.observe(viewLifecycleOwner, Observer {
            repoListAdapter.clear()
            if (it != null) {
                repoListAdapter.addAll(
                    it.map { item ->
                        (item as? TrendingRepository)?.let { it1 ->
                            ListCellItem(it1)
                        }
                    }
                )
                it.map { item ->
                    (item as? TrendingRepository)?.let { it1 ->
                        viewModel.insertTrendingRepository(it1, requireContext())
                    }
                }
            }
        });
    }
}