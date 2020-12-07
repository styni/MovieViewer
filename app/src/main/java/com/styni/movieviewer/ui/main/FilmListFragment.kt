package com.styni.movieviewer.ui.main

import android.os.Bundle
import android.view.View
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.styni.movieviewer.R
import com.styni.movieviewer.model.RezkaItem
import com.styni.movieviewer.presentation.main.FilmListPresenter
import com.styni.movieviewer.presentation.main.FilmListView
import com.styni.movieviewer.ui.global.BaseFragment
import com.styni.movieviewer.ui.main.list.FilmListAdapter
import com.styni.movieviewer.util.showSnackMessage
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_main_flow.*
import kotlinx.android.synthetic.main.toolbar.toolbar
import moxy.presenter.InjectPresenter

class FilmListFragment: BaseFragment(), FilmListView {
    override val layoutRes: Int = R.layout.fragment_list
    private var genre: Int? = null
    private var query: String? = null

    @InjectPresenter
    lateinit var presenter: FilmListPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        showProgressDialog(true)

        listFilms.layoutManager = GridLayoutManager(context, 3)
        listFilms.adapter = FilmListAdapter(requireContext()) {
//            tryOpenLink(RezkaApi.BACK_URL + it.url)
            presenter.detailFilm(it)
        }

        if (query.isNullOrBlank()) {
            presenter.getFilms(genre)
            parentFragment?.toolbar?.visibility = View.GONE
        } else {
            setToolbar(R.string.search, true, requireParentFragment().requireView())
            parentFragment?.bottom_navigation?.visibility = View.GONE
            parentFragment?.toolbar?.visibility = View.VISIBLE
            presenter.search(query)
        }
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    override fun setData(data: PagingData<RezkaItem>) {
        (listFilms.adapter as? FilmListAdapter)?.submitData(lifecycle, data)

        showProgressDialog(false)
    }

    override fun showError() {
        showProgressDialog(true)
        showSnackMessage(getString(R.string.unable_load_data))
    }

    override fun onDestroy() {
        super.onDestroy()
    }
    companion object {
        fun newInstance(genre: Int?): FilmListFragment {
            return FilmListFragment().apply {
                this.genre = genre
            }
        }

        fun newInstance(query: String) : FilmListFragment {
            return FilmListFragment().apply {
                this.query = query
            }
        }
    }
}