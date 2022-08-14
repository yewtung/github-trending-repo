package com.example.github_trending_repo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.github_trending_repo.R
import com.example.github_trending_repo.api.di.viewModel
import com.example.github_trending_repo.api.repository.ApiCallState
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
    private val viewModel: HomeViewModel by viewModel()

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
    }


    private fun setupUI() {

        btn_hello.setOnClickListener{
            print("111")
            viewModel.getList()
        }
    }

    private fun setupObserver() {

        viewModel.getListState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ApiCallState.ERROR -> {
                    print("error")
                }
                is ApiCallState.LOADING -> {
                    print("loading")
                    //showLoading()
                }
                is ApiCallState.COMPLETED -> {
                    print("11111")
                    print(it.responseResult)
                    // hideLoading()
                    //(it.responseResult as? Promotion)?.let {
                    //findNavController().navigate(HomeFragmentDirections.actionToPromotionDetailFragment(it))
                    //}
                }
            }
        })
    }


}