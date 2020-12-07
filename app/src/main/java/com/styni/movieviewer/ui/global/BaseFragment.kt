package com.styni.movieviewer.ui.global

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.appbar.MaterialToolbar
import com.styni.movieviewer.R
import moxy.MvpAppCompatActivity
import moxy.MvpAppCompatFragment

abstract class BaseFragment : MvpAppCompatFragment() {

    abstract val layoutRes: Int
    private var toolbar: MaterialToolbar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutRes, container, false)
    }

    protected fun setToolbar(@StringRes title: Int, withBackButton: Boolean, view: View) {
        toolbar = view.findViewById(R.id.toolbar)
        (activity as MvpAppCompatActivity).setSupportActionBar(toolbar)
        val actionBar = (activity as MvpAppCompatActivity).supportActionBar
        actionBar?.setTitle(title)
//        actionBar?.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.color.cartoon))
        if (withBackButton) {
            actionBar?.setDisplayHomeAsUpEnabled(true)
            actionBar?.setDisplayShowHomeEnabled(true)
            toolbar?.setNavigationOnClickListener { onBackPressed() }
            actionBar?.setHomeAsUpIndicator(ContextCompat.getDrawable(requireContext(), R.drawable.ic_back))
        }
    }

    protected fun setToolbar(title: String, withBackButton: Boolean, view: View) {
        toolbar = view.findViewById(R.id.toolbar)
        (activity as MvpAppCompatActivity).setSupportActionBar(toolbar)
        val actionBar = (activity as MvpAppCompatActivity).supportActionBar
        actionBar?.title = title
//        actionBar?.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.color.cartoon))
        if (withBackButton) {
            actionBar?.setDisplayHomeAsUpEnabled(true)
            actionBar?.setDisplayShowHomeEnabled(true)
            toolbar?.setNavigationOnClickListener { onBackPressed() }
            actionBar?.setHomeAsUpIndicator(ContextCompat.getDrawable(requireContext(), R.drawable.ic_back))
        }
    }

    protected fun showProgressDialog(progress: Boolean) {
        val pr = view?.findViewById<View>(R.id.progress) as LottieAnimationView
        if (progress)
            pr.visibility = View.VISIBLE
        else
            pr.visibility = View.GONE
    }

    open fun onBackPressed() {
    }

    override fun onDestroy() {
        toolbar?.setNavigationOnClickListener(null)
        super.onDestroy()
    }

    companion object {
        private const val PROGRESS_TAG = "progress_dialog"
    }
}