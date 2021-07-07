package com.sample.currencylayer.utils

import android.widget.Filter
import com.sample.currencylayer.ui.exchangeRate.ExchangeRatesAdapter
import com.sample.currencylayer.data.local.currencies.CurrencyItem
import com.sample.currencylayer.data.local.exchangerates.ExchangeItem

/**
 * Filter / Search in local list
 */
class ExchangeRateSearchFilter(
    private val mAdapter: ExchangeRatesAdapter,
    var currenciesList: ArrayList<ExchangeItem>
) : Filter() {

    private var filteredList: ArrayList<ExchangeItem> = ArrayList()

    override fun performFiltering(search: CharSequence): FilterResults {
        filteredList.clear()
        val results = FilterResults()
        if (search.isEmpty()) {
            filteredList.addAll(currenciesList)
        } else {
            for (contact in currenciesList) {
                if (contact.currency.contains(search, ignoreCase = true)) {
                    filteredList.add(contact)
                }
            }
        }
        results.values = filteredList
        results.count = filteredList.size
        return results
    }

    override fun publishResults(constraint: CharSequence, results: FilterResults) {
        mAdapter.updateSearchResults(results.values as ArrayList<ExchangeItem>)
    }

}