package com.eslam.moviesapp.ui.home

import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.eslam.moviesapp.common.NetworkObserver
import com.eslam.moviesapp.databinding.FragmentHomeBinding
import com.eslam.moviesapp.domain.models.Genre
import com.eslam.moviesapp.domain.models.Movies
import com.eslam.moviesapp.ui.home.adapters.GenresAdapter
import com.eslam.moviesapp.ui.home.adapters.MoviesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment(),GenresAdapter.GenreClick {

    private var binding:FragmentHomeBinding?=null
    private lateinit var moviesAdapter:MoviesAdapter
    private lateinit var genresAdapter: GenresAdapter
    private val genres:MutableList<Genre> = mutableListOf()
    private val viewModel:HomeViewModel by viewModels()
    lateinit var networkObserver:NetworkObserver
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(LayoutInflater.from(container?.context),container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviesAdapter = MoviesAdapter()
        genresAdapter = GenresAdapter()
        addObservers()
        val connectivityManager =
            requireActivity().getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        networkObserver = NetworkObserver(connectivityManager)
        listenToNetwork(networkObserver!!)

    }



    override fun onDestroyView() {
        super.onDestroyView()
        binding=null

    }

    private fun listenToNetwork(networkObserver: NetworkObserver) {

        viewLifecycleOwner.lifecycleScope.launch {
            networkObserver.networkState.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect{
                if (it)
                {
                    viewModel.getGenres()
                }else
                {
                    Toast.makeText(requireActivity().applicationContext,"No Internet",Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    private fun addObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.genreFlow.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect{
                if (it.isNotEmpty())
                {
                    binding!!.generRecycler.adapter = genresAdapter
                    genresAdapter.genreClick=this@HomeFragment
                    genres.clear()
                    genres.addAll(it)
                    genresAdapter.submitList(it)

                    genres.forEach { genre->
                        viewModel.getMovies(genre.id,genre.name)
                    }



                }
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.movieFlow.flowWithLifecycle(viewLifecycleOwner.lifecycle,Lifecycle.State.RESUMED).collect{
                if (it !=null)
                {
                    val currentList = moviesAdapter.currentList
                    val newList:MutableList<Movies> = mutableListOf()
                    newList.addAll(currentList)
                    newList.add(Movies(it.second, listOf(),0))
                    binding!!.categoriesRecycler.adapter = moviesAdapter
                    moviesAdapter.setNestedPagingData(pagingData = it.first,viewLifecycleOwner.lifecycleScope)

                    moviesAdapter.submitList(newList)


                }



            }
        }


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
            viewModel.filteredMoviesFlow.flowWithLifecycle(viewLifecycleOwner.lifecycle,Lifecycle.State.RESUMED).collect{
                if (it !=null)
                {

                    val newList:MutableList<Movies> = mutableListOf()
                    newList.add(Movies(it.second, listOf(),0))
                    moviesAdapter.submitList(listOf())
                    binding!!.categoriesRecycler.adapter = moviesAdapter
                    moviesAdapter.setNestedPagingData(it.first,viewLifecycleOwner.lifecycleScope)
                    moviesAdapter.submitList(newList)



                }



            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            moviesAdapter.selectMovieFlow.flowWithLifecycle(viewLifecycleOwner.lifecycle).collectLatest {
                if (it !=null)
                {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMovieDetailsFragment(it))
                }
            }
        }


    }




    override fun genreOnClick(genre: String,isSelected:Boolean,id: Int) {

        if (isSelected)
        {

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.filterMovies(id,genre)
            }

        }else
        {
            moviesAdapter.submitList(listOf())
            viewLifecycleOwner.lifecycleScope.launch {
                genres.forEach {
                    viewModel.getMovies(it.id,it.name)
                }
            }



        }

    }
}