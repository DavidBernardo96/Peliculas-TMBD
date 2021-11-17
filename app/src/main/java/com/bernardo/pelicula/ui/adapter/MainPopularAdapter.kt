package com.bernardo.pelicula.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bernardo.pelicula.R
import com.bernardo.pelicula.base.BaseViewHolder
import com.bernardo.pelicula.data.model.Movie
import com.bernardo.pelicula.databinding.ItemMoviePopularBinding
import com.bernardo.pelicula.utils.AppConstants
import com.bumptech.glide.Glide

class MainPopularAdapter(
    private val context: Context, private val moviesList: MutableList<Movie>,
    private val itemClickListener: OnMovieClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnMovieClickListener {
        fun onMoviePopularCLick(movie: Movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return MainViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_movie_popular, parent, false)
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
        val binding = ItemMoviePopularBinding.bind(view)
        override fun bind(item: Movie, position: Int) {
            Glide.with(context).load("${AppConstants.IMAGE_URL}${item.posterPath}")
                .placeholder(R.drawable.ic_img_loading).fitCenter()
                .into(binding.movieImg)

            binding.movieTitle.text = item.title
            binding.itemMovie.setOnClickListener { itemClickListener.onMoviePopularCLick(item) }
            binding.moviePopular.text = item.popularity.toString()
            binding.movieReleaseYear.text = item.releaseDate.split("-")[0]
        }
    }
}