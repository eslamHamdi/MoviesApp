package com.eslam.moviesapp.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.eslam.moviesapp.R
import com.eslam.moviesapp.common.isNetworkConnected
import com.eslam.moviesapp.databinding.FragmentMovieSearchBinding
import com.eslam.moviesapp.domain.models.Movie
import com.eslam.moviesapp.ui.search.adapters.SearchAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieSearchFragment : Fragment() , SearchAdapter.MovieSearchClick {

    private var binding:FragmentMovieSearchBinding?=null

    private val viewModel:SearchViewModel by viewModels()
    var job: Job? = null

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // get the content of both the edit text
            job?.cancel()
            resetSearchList()

            job = MainScope().launch{
                delay(1000)
                s.let {
                    if(it.toString().isNotBlank()|| it.toString().isNotEmpty() ) {
                        if(isNetworkConnected(requireContext())) {
                            viewModel.moviesSearch(it.toString())
                        }else {
                            Toast.makeText(requireActivity().applicationContext,"Connect To The Internet",Toast.LENGTH_LONG).show()
                        }

                    }

                }
            }



        }

        override fun afterTextChanged(s: Editable) {

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieSearchBinding.inflate(LayoutInflater.from(container!!.context),container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        addObservers()

        binding?.searchBar!!.addTextChangedListener(textWatcher)



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
                adapter.movieSearchClick=this@MovieSearchFragment
                adapter.submitList(it)

            }
        }
    }


    private fun resetSearchList(){
        if ( binding!!.searchBar.text.isEmpty() ||  binding!!.searchBar.editableText.isBlank())
        {
             viewModel.resetSearch()
        }
    }

    override fun onMovieClick(movie: Movie) {
        findNavController().navigate(MovieSearchFragmentDirections.actionMovieSearchFragmentToMovieDetailsFragment(movie))
    }


}