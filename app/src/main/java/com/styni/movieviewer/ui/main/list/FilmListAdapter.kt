package com.styni.movieviewer.ui.main.list

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.*
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.styni.movieviewer.R
import com.styni.movieviewer.model.RezkaItem
import kotlinx.android.synthetic.main.item_list.view.*

class FilmListAdapter(private val context: Context, private val selectedFilms : (item: RezkaItem) -> Unit) :
    PagingDataAdapter<RezkaItem, FilmListAdapter.FilmListViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return FilmListViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilmListViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class FilmListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(item: RezkaItem?) {
            if (item == null) {
                return
            }

            view.setOnClickListener { selectedFilms(item) }

            view.title.text = item.title
            view.description.text = item.info
            Glide.with(view.image)
                .load(item.image)
                .into(view.image)

            var icon = getDrawable(context, R.drawable.ic_all)
            var color: Int = getColor(context, R.color.black_20)
            when (item.type.toLowerCase()) {
                "сериал" -> {
                    icon = getDrawable(context, R.drawable.ic_serial)!!
                    color = getColor(context, R.color.serials)
                }
                "фильм" -> {
                    icon = getDrawable(context, R.drawable.ic_movie)!!
                    color = getColor(context, R.color.films)
                }
                "мультфильм" -> {
                    icon = getDrawable(context, R.drawable.ic_cartoon)!!
                    color = getColor(context, R.color.cartoon)
                }
                "аниме" -> {
                    icon = getDrawable(context, R.drawable.ic_cartoon)!!
                    color = getColor(context, R.color.grey)
                }
            }

            view.type.setImageDrawable(icon)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                view.type.setColorFilter(color)
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<RezkaItem>() {
            override fun areItemsTheSame(oldItem: RezkaItem, newItem: RezkaItem): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: RezkaItem, newItem: RezkaItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}