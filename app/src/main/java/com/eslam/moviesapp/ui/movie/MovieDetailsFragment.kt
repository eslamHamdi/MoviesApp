package com.eslam.moviesapp.ui.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.eslam.moviesapp.R
import com.eslam.moviesapp.databinding.FragmentMovieDetailsBinding
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private var binding:FragmentMovieDetailsBinding?=null
    private val args:MovieDetailsFragmentArgs by navArgs()
    private val imageBaseUrl = "https://image.tmdb.org/t/p/original"
    private var isbookMarked = false
    private val  viewModel:MovieDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieDetailsBinding.inflate(LayoutInflater.from(container!!.context),container,false)
        return binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movie = args.movie

        binding!!.details.text = movie.overview
        binding!!.movieTitle.text = movie.title
        Glide.with(requireContext())
            .load(imageBaseUrl+movie.posterPath)
            .placeholder(R.drawable.loading_animation)
            .error(R.drawable.ic_baseline_broken_image_24)
            .into(binding!!.moviePoster)

      if (movie.isFavorite)
      {
          binding!!.bookMark.setImageResource(R.drawable.ic_baseline_bookmark_filled)
      }

        binding!!.bookMark.setOnClickListener {
            isbookMarked = !isbookMarked
            if (isbookMarked)
            {
                binding!!.bookMark.setImageResource(R.drawable.ic_baseline_bookmark_filled)
                movie.isFavorite=true
                viewModel.saveMovie(movie)
            }else
            {
                viewModel.deleteMovie(movie.id)
                binding!!.bookMark.setImageResource(R.drawable.bookmark_border)
            }

        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}