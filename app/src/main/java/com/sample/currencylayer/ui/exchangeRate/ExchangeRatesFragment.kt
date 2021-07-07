package com.sample.currencylayer.ui.exchangeRate

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.sample.currencylayer.R
import com.sample.currencylayer.utils.Constants
import com.sample.currencylayer.utils.Constants.SEARCH_TIME_DELAY
import com.sample.currencylayer.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_exchange_rate.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class ExchangeRatesFragment @Inject constructor(
    private val exchangeRatesAdapter: ExchangeRatesAdapter,
    var viewModel: ExchangeRateViewModel? = null
) : Fragment(R.layout.fragment_exchange_rate) {

    /**
     *
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            viewModel ?: ViewModelProvider(requireActivity()).get(ExchangeRateViewModel::class.java)

        subscribeToObservers()

        setupRecyclerView()

        listeners()
    }


    private fun listeners() {
        //Get the updated selected currency
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(Constants.KEY_SELECTED_CURRENCY)
            ?.observe(
                viewLifecycleOwner
            ) { result ->
                // Selected currency updated fetch new exchange rates
                updateSelectedCurrency(result)
            }

        //Change currency listener
        llSelectedCurrency.setOnClickListener {
            navigateToCurrenciesFragment()
        }

        // Currency amount input listener
        var job: Job? = null
        etAmount.addTextChangedListener { editable ->
            job?.cancel()
            job = lifecycleScope.launch {
                delay(SEARCH_TIME_DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        exchangeRatesAdapter.updateExchangeRate(editable.toString().toDouble())
                    } else {
                        exchangeRatesAdapter.updateExchangeRate(1.0)
                    }
                }
            }
        }

        val refreshListener = SwipeRefreshLayout.OnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            viewModel?.getExchangeRates(getSelectedCurrency())
        }
        swipeRefreshLayout.setOnRefreshListener(refreshListener)
    }

    /**
     *
     */
    private fun subscribeToObservers() {
        //Exchange rate
        viewModel?.exchangeRates?.observe(viewLifecycleOwner, {
            it?.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        exchangeRatesAdapter.submitList(
                            result.data ?: emptyList(),
                            getInputAmount()
                        )
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
    }

    /**
     *
     */
    private fun setupRecyclerView() {
        rvExchangeRates.apply {
            adapter = exchangeRatesAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }

    /**
     *
     */
    private fun getInputAmount(): Double {
        val amount = etAmount.text.toString()
        return if (amount.isEmpty()) 1.0 else amount.toDouble()
    }

    /**
     * Launch Currencies fragment
     */
    private fun navigateToCurrenciesFragment() {
        val navHostFragment =
            activity?.supportFragmentManager?.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navHostFragment.navController.navigate(R.id.action_exchangeRatesFragment_to_currenciesFragment)
    }

    /**
     * Get selected currency
     */
    private fun getSelectedCurrency(): String = tvSelectedCurrency.text.toString()

    /**
     * Update selected currency
     */
    private fun updateSelectedCurrency(selectedCurrency: String) {
        tvSelectedCurrency.text = selectedCurrency
        //Get Currency Exchange Rates
        viewModel?.getExchangeRates(selectedCurrency)
    }

    /**
     * Search exchange rate
     */
    fun searchQueryRequest(query: String) {
        exchangeRatesAdapter.filter.filter(query)
    }
}
















