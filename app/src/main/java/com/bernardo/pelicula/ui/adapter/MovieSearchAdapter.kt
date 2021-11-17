package com.bernardo.pelicula.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bernardo.pelicula.R
import com.bernardo.pelicula.base.BaseViewHolder
import com.bernardo.pelicula.data.model.Movie
import com.bernardo.pelicula.databinding.ItemMovieSearchBinding
import com.bernardo.pelicula.utils.AppConstants
import com.bumptech.glide.Glide

class MovieSearchAdapter(
    private val context: Context, private val moviesList: MutableList<Movie>,
    private val itemClickListener: OnMovieClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnMovieClickListener {
        fun onMovieCLick(movie: Movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return MainViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_movie_search, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is MainViewHolder -> holder.bind(moviesList[position], position)
        }
    }

    inner class MainViewHolder(view: View) : BaseViewHolder<Movie>(view) {
        val binding = ItemMovieSearchBinding.bind(view)
        override fun bind(item: Movie, position: Int) {
            Glide.with(context).load("${AppConstants.IMAGE_URL}${item.posterPath}")
                .placeholder(R.drawable.ic_img_loading).fitCenter()
                .into(binding.movieImg)

            binding.movieTitle.text = item.title
            binding.movieDesc.text = item.overview
            itemView.setOnClickListener { itemClickListener.onMovieCLick(item) }
            binding.movieReleaseYear.text = item.releaseDate.split("-")[0]
        }
    }
}