package com.styni.movieviewer.ui.main

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter
import com.styni.movieviewer.ui.global.BaseFragment
import com.styni.movieviewer.R
import com.styni.movieviewer.presentation.main.MainFlowPresenter
import com.styni.movieviewer.presentation.main.MainView
import com.styni.movieviewer.util.Screens
import com.styni.movieviewer.util.hideKeyboard
import kotlinx.android.synthetic.main.fragment_main_flow.*
import moxy.presenter.InjectPresenter
import ru.terrakok.cicerone.android.support.SupportAppScreen
import java.util.*
import kotlin.collections.ArrayList

class MainFlowFragment : BaseFragment(), MainView {
    override val layoutRes: Int = R.layout.fragment_main_flow

    private val currentTabFragment: BaseFragment?
        get() = childFragmentManager.fragments.firstOrNull { !it.isHidden} as BaseFragment?

    @InjectPresenter
    lateinit var presenter: MainFlowPresenter

    private val stackView: ArrayList<Int> = arrayListOf(0)

    lateinit var bottomAdapter: AHBottomNavigationAdapter

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        bottomAdapter = AHBottomNavigationAdapter(activity, R.menu.main_bottom_menu).apply {
            val tabColors = context?.resources?.getIntArray(R.array.tab_colors)!!
            setupWithBottomNavigation(bottom_navigation, tabColors)
        }

        with(bottom_navigation) {
            accentColor = context.getColor(R.color.colorPrimary)
            inactiveColor = context.getColor(R.color.silver)
            isColored = true

            setOnTabSelectedListener { position, wasSelected ->
                if (!wasSelected) {
                    selectTab(
                        when (position) {
                            in 1 .. 3 -> Screens.FilmList(position)
                            else -> DEFAULT_VIEW
                        }
                    )
                    addViewToStack(position)
                }
                true
            }
        }

        selectTab(DEFAULT_VIEW)

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                search(query ?: "")
                requireActivity().hideKeyboard()
                return true
            }

            override fun onQueryTextChange(newText: String?) = false
        })
    }

    private fun addViewToStack(position: Int) {
        stackView.remove(position)
        stackView.add(position)
    }

    private fun createTabFragment(tab: SupportAppScreen) = tab.fragment!!

    private fun selectTab(tab: SupportAppScreen) {
        val newFragment = createTabFragment(tab)
        openNewWindow(tab.screenKey, newFragment)
    }

    private fun search(query: String) {
        val newFragment = createTabFragment(Screens.FilmListQuery(query))
        openNewWindow("SearchFragment", newFragment)
    }

    private fun openNewWindow(tag: String, newFragment: Fragment) {
        val currentFragment = currentTabFragment

        if (currentFragment != null && currentFragment == newFragment) return

        childFragmentManager.beginTransaction().apply {
            add(R.id.list_container, newFragment, tag)

            currentFragment?.let {
                hide(it)
                it.userVisibleHint = false
            }
            newFragment.let {
                show(it)
                it.userVisibleHint = true
            }
        }.commitNow()
    }


    override fun onBackPressed() {
        bottom_navigation?.visibility = View.VISIBLE
        if (stackView.isEmpty()) {
            currentTabFragment?.onBackPressed()
        } else {
            stackView.removeLast()
            bottom_navigation.currentItem = stackView.removeLast()
        }
    }

    companion object {
        private val DEFAULT_VIEW = Screens.FilmList(null)
    }
}