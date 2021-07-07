package com.sample.currencylayer.ui.currencies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.sample.currencylayer.R
import com.sample.currencylayer.utils.Constants
import com.sample.currencylayer.utils.Status
import com.sample.currencylayer.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_currencies.*
import kotlinx.android.synthetic.main.fragment_currencies.rootLayout
import kotlinx.android.synthetic.main.fragment_currencies.swipeRefreshLayout
import javax.inject.Inject

@AndroidEntryPoint
class CurrenciesFragment @Inject constructor(
    private val currenciesAdapter: CurrenciesAdapter?,
    private var viewModel: CurrenciesViewModel? = null
) : Fragment(R.layout.fragment_currencies) {


    /**
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    /**
     *
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(CurrenciesViewModel::class.java)
        setupRecyclerView()
        subscribeToObservers()
        listeners()
    }

    /**
     *
     */
    private fun subscribeToObservers() {
        viewModel?.currencies?.observe(viewLifecycleOwner, {
            it?.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        currenciesAdapter?.submitList(result.data ?: emptyList())
                        swipeRefreshLayout.isRefreshing = false
                    }
                    Status.ERROR -> {
                        Snackbar.make(
                            rootLayout,
                            result.message ?: Constants.SOMETHING_WENT_WRONG,
                            Snackbar.LENGTH_LONG
                        ).show()
                        swipeRefreshLayout.isRefreshing = false
                    }
                    Status.LOADING -> {
                        swipeRefreshLayout.isRefreshing = true
                    }
                }
            }
        })

        // Get currencies
        viewModel?.getCurrencies()
    }

    /**
     *
     */
    private fun setupRecyclerView() {
        rvCurrencies.apply {
            adapter = currenciesAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
        currenciesAdapter?.onCurrencyClicked = { currency ->
            findNavController().previousBackStackEntry?.savedStateHandle?.set(
                Constants.KEY_SELECTED_CURRENCY,
                currency.currency
            )
            findNavController().popBackStack()

            (activity as MainActivity).resetSearchQuery()
        }
    }

    private fun listeners() {
        val refreshListener = SwipeRefreshLayout.OnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            // Get currencies
            viewModel?.getCurrencies()
        }
        swipeRefreshLayout.setOnRefreshListener(refreshListener)
    }

    /**
     * Search currency
     */
    fun searchQueryRequest(query: String) {
        currenciesAdapter?.filter?.filter(query)
    }
}