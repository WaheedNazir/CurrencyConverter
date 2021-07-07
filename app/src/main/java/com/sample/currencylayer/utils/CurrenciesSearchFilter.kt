package com.sample.currencylayer.utils

import android.widget.Filter
import com.sample.currencylayer.ui.currencies.CurrenciesAdapter
import com.sample.currencylayer.data.local.currencies.CurrencyItem

/**
 * Filter / Search in local list
 */
class CurrenciesSearchFilter(
    private val mAdapter: CurrenciesAdapter,
    private var currenciesList: ArrayList<CurrencyItem>
) : Filter() {

    private var filteredList: ArrayList<CurrencyItem> = ArrayList()

    override fun performFiltering(search: CharSequence): FilterResults {
        filteredList.clear()
        val results = FilterResults()
        if (search.isEmpty()) {
            filteredList.addAll(currenciesList)
        } else {
            for (contact in currenciesList) {
                if (contact.currency.contains(search, ignoreCase = true) ||
                    contact.currencyLabel.contains(search, ignoreCase = true)
                ) {
                    filteredList.add(contact)
                }
            }
        }
        results.values = filteredList
        results.count = filteredList.size
        return results
    }

    override fun publishResults(constraint: CharSequence, results: FilterResults) {
        mAdapter.updateSearchResults(results.values as ArrayList<CurrencyItem>)
    }

}