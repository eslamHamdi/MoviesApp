package com.eslam.moviesapp.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.eslam.moviesapp.R
import com.eslam.moviesapp.databinding.FragmentFavoritesBinding
import com.eslam.moviesapp.domain.models.Movie
import com.eslam.moviesapp.ui.favorites.adapters.FavoritesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment(), FavoritesAdapter.FavoriteMovieSearchClick {



    private var binding:FragmentFavoritesBinding?=null
    private val viewModel:FavoritesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoritesBinding.inflate(LayoutInflater.from(container!!.context),container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addObservers()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        binding= null
    }

    private fun addObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.movieFlow.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect{
                val adapter = FavoritesAdapter()
                binding!!.favRecycler.adapter = adapter
                adapter.favoriteMovieClick=this@FavoritesFragment
                adapter.submitList(it)
            }
        }
    }

    override fun onMovieClick(movie: Movie) {
        findNavController().navigate(FavoritesFragmentDirections.actionFavoritesFragmentToMovieDetailsFragment(movie))
    }


}