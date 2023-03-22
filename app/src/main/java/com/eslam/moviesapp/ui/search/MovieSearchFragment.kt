package com.eslam.moviesapp.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.eslam.moviesapp.R
import com.eslam.moviesapp.common.isNetworkConnected
import com.eslam.moviesapp.databinding.FragmentMovieDetailsBinding
import com.eslam.moviesapp.databinding.FragmentMovieSearchBinding
import com.eslam.moviesapp.domain.models.Movie
import com.eslam.moviesapp.ui.search.adapters.SearchAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieSearchFragment : Fragment() , SearchAdapter.MovieSearchClick {

    private var binding:FragmentMovieSearchBinding?=null

    private val viewModel:SearchViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieSearchBinding.inflate(LayoutInflater.from(container!!.context),container,false)
        return inflater.inflate(R.layout.fragment_movie_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        addObservers()

        var job: Job? = null
        binding?.searchBar!!.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {


            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                job?.cancel()
                resetSearchList()

                job = viewLifecycleOwner.lifecycleScope.launch{
                    delay(2000)
                    p0?.let {
                        if(it.toString().isNotBlank()|| it.toString().isNotEmpty() ) {
                            if(isNetworkConnected(requireContext()))
                            {
                                viewModel.moviesSearch(it.toString())
                            }else
                            {
                                Toast.makeText(requireActivity().applicationContext,"Connect To The Internet",Toast.LENGTH_LONG).show()
                            }

                        }

                    }
                }

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })


    }



    override fun onDestroyView() {
        super.onDestroyView()
        binding=null
    }

    private fun addObservers() {
        viewLifecycleOwner.lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                viewModel.errorFlow.collectLatest {

                    if (it.isNullOrEmpty())
                    {
                        Toast.makeText(requireActivity().applicationContext,it,Toast.LENGTH_LONG).show()
                    }
                }
            }

        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loadingState.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect(){
                if (it)
                {
                    binding!!.searchProgressBar.visibility = View.VISIBLE
                }else
                {
                    binding!!.searchProgressBar.visibility = View.GONE
                }
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.movieFlow.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect{
                val adapter = SearchAdapter()
                binding!!.searchRecycler.adapter = adapter
                adapter.submitList(it)

            }
        }
    }


    private fun resetSearchList(){
        if ( binding!!.searchBar.editableText.isEmpty() ||  binding!!.searchBar.editableText.isBlank())
        {
             viewModel.resetSearch()
        }
    }

    override fun onMovieClick(movie: Movie) {
        findNavController().navigate(MovieSearchFragmentDirections.actionMovieSearchFragmentToMovieDetailsFragment(movie))
    }


}