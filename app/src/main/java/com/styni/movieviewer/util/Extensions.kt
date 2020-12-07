package com.styni.movieviewer.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.webkit.URLUtil
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.styni.movieviewer.R

fun Context.color(colorRes: Int) = ContextCompat.getColor(this, colorRes)

fun Context.getTintDrawable(drawableRes: Int, colorRes: Int): Drawable {
    val source = ContextCompat.getDrawable(this, drawableRes)!!.mutate()
    val wrapped = DrawableCompat.wrap(source)
    DrawableCompat.setTint(wrapped, color(colorRes))
    return wrapped
}

fun Context.getTintDrawable(
    drawableRes: Int,
    colorResources: IntArray,
    states: Array<IntArray>
): Drawable {
    val source = ContextCompat.getDrawable(this, drawableRes)!!.mutate()
    val wrapped = DrawableCompat.wrap(source)
    DrawableCompat.setTintList(
        wrapped,
        ColorStateList(states, colorResources.map { color(it) }.toIntArray())
    )
    return wrapped
}

fun ArrayList<String>.getString() : String {
    return this.toString().replace("[","").replace("]","")
}

fun TextView.setStartDrawable(drawable: Drawable) {
    setCompoundDrawablesRelativeWithIntrinsicBounds(
        drawable,
        null,
        null,
        null
    )
}

fun ImageView.tint(colorRes: Int) = this.setColorFilter(this.context.color(colorRes))

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun View.visible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

fun TextView.showTextOrHide(str: String?) {
    this.text = str
    this.visible(!str.isNullOrBlank())
}

fun Fragment.tryOpenLink(link: String?, basePath: String? = "https://google.com/search?q=") {
    if (link != null) {
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    when {
                        URLUtil.isValidUrl(link) -> Uri.parse(link)
                        else -> Uri.parse(basePath + link)
                    }
                )
            )
        } catch (e: Exception) {
            Log.e("tryOpenLink error: $e", e.message)
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://google.com/search?q=$link")
                )
            )
        }
    }
}

fun Fragment.shareText(text: String?) {
    text?.let {
        startActivity(
            Intent.createChooser(
                Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, text)
                },
                getString(R.string.share_to)
            )
        )
    }
}

fun Fragment.sendEmail(email: String?) {
    if (email != null) {
        startActivity(
            Intent.createChooser(
                Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null)),
                null
            )
        )
    }
}

fun Fragment.showSnackMessage(message: String) {
    view?.showSnackMessage(message)
}

fun View.showSnackMessage(message: String) {
    val ssb = SpannableStringBuilder().apply {
        append(message)
        setSpan(
            ForegroundColorSpan(Color.WHITE),
            0,
            message.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    Snackbar.make(this, ssb, Snackbar.LENGTH_INDEFINITE).setAction("Action") {
        // Responds to click on the action
    }.show()
}

fun Activity.hideKeyboard() {
    currentFocus?.apply {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}

fun Any.objectScopeName() = "${javaClass.simpleName}_${hashCode()}"

fun View.setBackgroundTintByColor(@ColorInt color: Int) {
    val wrappedDrawable = DrawableCompat.wrap(background)
    DrawableCompat.setTint(wrappedDrawable.mutate(), color)
}

fun Toolbar.setTitleEllipsize(ellipsize: TextUtils.TruncateAt) {
    val fakeTitle = "fakeTitle"
    title = fakeTitle
    for (i in 0..childCount) {
        val child = getChildAt(i)
        if (child is TextView && child.text == fakeTitle) {
            child.ellipsize = ellipsize
            break
        }
    }
    title = ""
}

fun View.updatePadding(
    left: Int = paddingLeft,
    top: Int = paddingTop,
    right: Int = paddingRight,
    bottom: Int = paddingBottom
) {
    setPadding(left, top, right, bottom)
}

fun View.addSystemTopPadding(
    targetView: View = this,
    isConsumed: Boolean = false
) {
    doOnApplyWindowInsets { _, insets, initialPadding ->
        targetView.updatePadding(
            top = initialPadding.top + insets.systemWindowInsetTop
        )
        if (isConsumed) {
            insets.replaceSystemWindowInsets(
                Rect(
                    insets.systemWindowInsetLeft,
                    0,
                    insets.systemWindowInsetRight,
                    insets.systemWindowInsetBottom
                )
            )
        } else {
            insets
        }
    }
}

fun View.addSystemBottomPadding(
    targetView: View = this,
    isConsumed: Boolean = false
) {
    doOnApplyWindowInsets { _, insets, initialPadding ->
        targetView.updatePadding(
            bottom = initialPadding.bottom + insets.systemWindowInsetBottom
        )
        if (isConsumed) {
            insets.replaceSystemWindowInsets(
                Rect(
                    insets.systemWindowInsetLeft,
                    insets.systemWindowInsetTop,
                    insets.systemWindowInsetRight,
                    0
                )
            )
        } else {
            insets
        }
    }
}

fun View.doOnApplyWindowInsets(block: (View, insets: WindowInsetsCompat, initialPadding: Rect) -> WindowInsetsCompat) {
    val initialPadding = recordInitialPaddingForView(this)
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        block(v, insets, initialPadding)
    }
    requestApplyInsetsWhenAttached()
}

private fun recordInitialPaddingForView(view: View) =
    Rect(view.paddingLeft, view.paddingTop, view.paddingRight, view.paddingBottom)

private fun View.requestApplyInsetsWhenAttached() {
    if (isAttachedToWindow) {
        ViewCompat.requestApplyInsets(this)
    } else {
        addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                v.removeOnAttachStateChangeListener(this)
                ViewCompat.requestApplyInsets(v)
            }

            override fun onViewDetachedFromWindow(v: View) = Unit
        })
    }
}
