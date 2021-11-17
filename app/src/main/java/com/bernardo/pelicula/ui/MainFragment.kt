package com.bernardo.pelicula.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bernardo.pelicula.R
import com.bernardo.pelicula.data.model.Movie
import com.bernardo.pelicula.databinding.FragmentMainBinding
import com.bernardo.pelicula.ui.adapter.MainPopularAdapter
import com.bernardo.pelicula.ui.adapter.MainRatedAdapter
import com.bernardo.pelicula.ui.viewmodel.MainViewModel
import com.bernardo.pelicula.vo.Resource
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.net.SocketTimeoutException


@AndroidEntryPoint
class MainFragment : Fragment(), MainPopularAdapter.OnMovieClickListener,MainRatedAdapter.OnMovieClickListener {

    private var _binding:FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var popularAdapter: MainPopularAdapter
    private lateinit var ratedAdapter: MainRatedAdapter

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()

        binding.toolb.btnSearchMain.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_movieSearchFragment)
        }
    }

    private fun setupObservers() {

        viewModel.populares.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    shimmerPopular(true,View.VISIBLE)
                }
                is Resource.Success -> {
                    shimmerPopular(false,View.GONE)

                    binding.rvMoviesPopulares.adapter =
                        MainPopularAdapter(requireContext(), result.data.toMutableList(), this)
                    binding.rvMoviesPopulares.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
                    popularAdapter = binding.rvMoviesPopulares.adapter as MainPopularAdapter
                }
                is Resource.Failure -> {
                    shimmerPopular(false, View.VISIBLE)
                    showError(result.exception)

                }
            }
        })


        viewModel.rateds.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    shimmerRated(true,View.VISIBLE)
                }
                is Resource.Success -> {
                    shimmerRated(false,View.GONE)

                    binding.rvMoviesRateds.adapter =
                        MainRatedAdapter(requireContext(), result.data.toMutableList(), this)

                    ratedAdapter = binding.rvMoviesRateds.adapter as MainRatedAdapter
                }
                is Resource.Failure -> {
                    shimmerRated(false,View.VISIBLE)
                    showError(result.exception)
                }
            }
        })
    }

    private fun showError(e: Exception) {
        when (e) {
            is SocketTimeoutException -> {
                Toast.makeText(requireContext(),"Demasiado tiempo de espera",Toast.LENGTH_SHORT).show()
            }
            is IOException -> {
                Toast.makeText(requireContext(),"Error de conexión",Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(
                    requireContext(),
                    "Error al obtener datos: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun shimmerPopular(b: Boolean, visible: Int) {
        binding.shimmerPopular.visibility=visible
        if(b){
            binding.shimmerPopular.startShimmer()
        }else{
            binding.shimmerPopular.stopShimmer()
        }
    }
    private fun shimmerRated(b: Boolean, visible: Int) {

        if(b){
            binding.shimmerValorados.startShimmer()
        }else{
            binding.shimmerValorados.stopShimmer()
        }
        binding.shimmerValorados.visibility=visible
    }


    private fun setupRecyclerView() {
        binding.rvMoviesPopulares.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)

        binding.rvMoviesRateds.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
    }

    override fun onMoviePopularCLick(movie: Movie) {
        detailMovie(movie)
    }
    override fun onMovieRatedCLick(movie: Movie) {
        detailMovie(movie)
    }
    private fun detailMovie(movie: Movie) {
        val bundle = Bundle()
        bundle.putParcelable("movie", movie)
        findNavController().navigate(R.id.action_mainFragment_to_movieDetailFragment, bundle)
    }


}