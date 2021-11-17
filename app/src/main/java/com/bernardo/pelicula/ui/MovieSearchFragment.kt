package com.bernardo.pelicula.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bernardo.pelicula.R
import com.bernardo.pelicula.data.model.Movie
import com.bernardo.pelicula.databinding.FragmentMovieSearchBinding
import com.bernardo.pelicula.ui.adapter.MovieSearchAdapter
import com.bernardo.pelicula.ui.viewmodel.MainViewModel
import com.bernardo.pelicula.vo.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieSearchFragment : Fragment(),MovieSearchAdapter.OnMovieClickListener {

    private var _binding: FragmentMovieSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var seacrhAdapter: MovieSearchAdapter

    private val viewModel by activityViewModels<MainViewModel>()

    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvSearch.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        setupSearchView()
        showSoftKeyboard(binding.toolb.edtsearch)
        setupObservers()

        binding.toolb.btnBackSearch.setOnClickListener {
            hideKeyboard()
            findNavController().popBackStack()
        }
        binding.toolb.btnClearSearch.setOnClickListener {
            binding.toolb.edtsearch.text=null
        }
    }
    private fun setupObservers() {
        viewModel.fetchMoviesSearchList.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    shimmerSearch(true, View.VISIBLE)
                }
                is Resource.Success -> {
                    shimmerSearch(false, View.GONE)
                    actualizarDatosReciclerView(result.data.toMutableList())
                }
                is Resource.Failure -> {
                    shimmerSearch(false, View.VISIBLE)

                    Toast.makeText(
                        requireContext(),
                        "Error al obtener datos: ${result.exception.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    private fun actualizarDatosReciclerView(toMutableList: MutableList<Movie>) {

        binding.rvSearch.adapter =
            MovieSearchAdapter(requireContext(), toMutableList, this)
        seacrhAdapter = binding.rvSearch.adapter as MovieSearchAdapter
    }

    private fun shimmerSearch(b: Boolean, visible: Int) {
        binding.shimmerSearch.visibility=visible
        if(b){
            binding.shimmerSearch.startShimmer()
        }else{
            binding.shimmerSearch.stopShimmer()
        }
    }

    private fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val inputMethodManager: InputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }
    private fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }
    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun setupSearchView() {

        binding.toolb.edtsearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(p0.toString().equals(null) || p0.toString().equals("")){
                    actualizarDatosReciclerView(ArrayList())
                    return
                }
                viewModel.setMovie(p0.toString()!!)
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }

    override fun onMovieCLick(movie: Movie) {
        val bundle = Bundle()
        bundle.putParcelable("movie", movie)
        findNavController().navigate(R.id.action_searchFragment_to_movieDetailFragment, bundle)
    }

}