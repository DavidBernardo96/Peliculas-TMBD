package com.bernardo.pelicula.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bernardo.pelicula.R
import com.bernardo.pelicula.data.model.Movie
import com.bernardo.pelicula.databinding.FragmentMovieDetailBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireArguments().let {
            movie = it.getParcelable("movie")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(requireContext()).load("https://image.tmdb.org/t/p/w500${movie.backdropPath}")
            .fitCenter().centerCrop().placeholder(R.drawable.ic_img_loading).into(binding.movieImgPoster)
        binding.movieTitleDetail.text = movie.title
        binding.movieDescDetail.text = movie.overview
        binding.movieVotesAverageDetail.text = movie.voteAverage.toString()
        binding.movieReleaseYearDetail.text = movie.releaseDate
        binding.btnBackMovieDetail.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}