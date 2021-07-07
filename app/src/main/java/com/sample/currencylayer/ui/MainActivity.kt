package com.sample.currencylayer.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import com.sample.currencylayer.R
import com.sample.currencylayer.ui.currencies.CurrenciesFragment
import com.sample.currencylayer.ui.exchangeRate.ExchangeRatesFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var fragmentFactory: FragmentsFactory

    private lateinit var searchView: SearchView

    /**
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = fragmentFactory
        setContentView(R.layout.activity_main)
    }


    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        return super.onPrepareOptionsMenu(menu)
    }

    /**
     * Options menu for search
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu?.findItem(R.id.menu_search)?.actionView as SearchView
        searchView.queryHint = "Search"
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                passSearchQuery(query ?: "")
                return false
            }
        })
        return true
    }

    /**
     * Route search query to particular fragment
     */
    private fun passSearchQuery(query: String) {
        val fragment: Fragment? = getCurrentVisibleFragment()
        if (fragment is ExchangeRatesFragment) {
            fragment.searchQueryRequest(query)
        } else if (fragment is CurrenciesFragment) {
            fragment.searchQueryRequest(query)
        }
    }

    /**
     * Reset search query
     */
    fun resetSearchQuery() {
        searchView.setQuery("", false)
        searchView.isIconified = true
    }

    /**
     *
     */
    private fun getCurrentVisibleFragment(): Fragment? {
        val navHostFragment = supportFragmentManager.primaryNavigationFragment as NavHostFragment?
        val fragmentManager: FragmentManager = navHostFragment?.childFragmentManager!!
        return fragmentManager.primaryNavigationFragment
    }

}