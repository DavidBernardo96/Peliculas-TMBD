package com.bernardo.pelicula.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bernardo.pelicula.R
import com.bernardo.pelicula.base.BaseViewHolder
import com.bernardo.pelicula.data.model.Movie
import com.bernardo.pelicula.databinding.ItemMovieRatedBinding
import com.bernardo.pelicula.utils.AppConstants
import com.bumptech.glide.Glide

class MainRatedAdapter(
    private val context: Context, private val moviesList: MutableList<Movie>,
    private val itemClickListener: OnMovieClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnMovieClickListener {
        fun onMovieRatedCLick(movie: Movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return MainViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_movie_rated, parent, false)
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
        val binding = ItemMovieRatedBinding.bind(view)
        override fun bind(item: Movie, position: Int) {
            Glide.with(context).load("${AppConstants.IMAGE_URL}${item.posterPath}")
                .placeholder(R.drawable.ic_img_loading).fitCenter()
                .into(binding.movieImg)

            binding.movieTitle.text = item.title
            binding.itemMovie.setOnClickListener { itemClickListener.onMovieRatedCLick(item) }
            binding.movieVotesAverage.text = item.voteAverage.toString()
            binding.movieReleaseYear.text = item.releaseDate.split("-")[0]
        }
    }
}