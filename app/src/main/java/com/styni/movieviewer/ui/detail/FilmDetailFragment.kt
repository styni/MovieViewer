package com.styni.movieviewer.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import com.bumptech.glide.Glide
import com.styni.movieviewer.R
import com.styni.movieviewer.model.RezkaFilmDetail
import com.styni.movieviewer.model.RezkaItem
import com.styni.movieviewer.presentation.detail.FilmDetailPresenter
import com.styni.movieviewer.presentation.detail.FilmDetailView
import com.styni.movieviewer.ui.global.BaseFragment
import com.styni.movieviewer.util.getString
import com.styni.movieviewer.util.tryOpenLink
import kotlinx.android.synthetic.main.fragment_film_detail.*
import kotlinx.android.synthetic.main.fragment_main_flow.*
import kotlinx.android.synthetic.main.toolbar.toolbar
import moxy.presenter.InjectPresenter

class FilmDetailFragment : BaseFragment(), FilmDetailView {

    override val layoutRes: Int = R.layout.fragment_film_detail
    private lateinit var quickInfo: RezkaItem

    @InjectPresenter
    lateinit var presenter: FilmDetailPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        showProgressDialog(true)
        presenter.detailFilm(quickInfo)
    }

    @SuppressLint("SetTextI18n")
    override fun setData(data: RezkaFilmDetail) {
        setToolbar(data.title, true, requireView())

        title.text = data.title
        data.originalTitle.let {
            if (it == null) {
                originTitle.visibility = View.GONE
            } else {
                originTitle.text = it
            }
        }
        data.imdbRating.let {
            if (it == null) {
                imdbContainer.visibility = View.GONE
            } else {
                imdbRating.text = it
            }
        }
        data.kpRating.let {
            if (it == null) {
                kpContainer.visibility = View.GONE
            } else {
                kpRating.text = it
            }
        }
        Glide.with(poster)
            .load(data.image)
            .into(poster)

        val allGenres = data.genre.getString()
        val ssb = SpannableStringBuilder().apply {

        }

        genre.text = getString(R.string.genre) + " $allGenres"
        director.text = getString(R.string.director) + " ${data.director}"
        description.text = getString(R.string.description) + " ${data.description}"
        duration.text = getString(R.string.duration) + " ${data.duration}"
        actors.text = getString(R.string.actors) + " ${data.actors.getString()}"

        showProgressDialog(false)
    }

    override fun onBackPressed() {
        presenter.onBaskPressed()
    }

    companion object {
        fun newInstance(item: RezkaItem): FilmDetailFragment {
            return FilmDetailFragment().apply {
                quickInfo = item
            }
        }
    }

}