package com.eslam.moviesapp.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.eslam.moviesapp.R
import com.eslam.moviesapp.databinding.GenerLayoutBinding
import com.eslam.moviesapp.domain.models.Genre

class GenresAdapter:ListAdapter<Genre,GenresAdapter.GenresViewHolder>(GenresDiffCallBack) {

    private lateinit var genreBinding:GenerLayoutBinding
    var genreClick:GenreClick? = null


    inner class GenresViewHolder(private val genreBinding:GenerLayoutBinding):ViewHolder(genreBinding.root)
    {
        fun bind(genre: Genre,position:Int)
        {
            genreBinding.genreTitle.text = genre.name

            if (genre.isSelected)
            {
                genreBinding.genreContainer.background.setTint(genreBinding.root.context.getColor(R.color.yellow))
            }else
            {
                genreBinding.genreContainer.background.setTint(genreBinding.root.context.getColor(R.color.white))
            }





            genreBinding.genreContainer.setOnClickListener {

                genre.isSelected = !genre.isSelected
                if (genre.isSelected)
                {
                    currentList.forEach {
                        if (it.id != genre.id)
                        {
                            it.isSelected = false

                        }
                    }
                }
                genreClick?.genreOnClick(genre.name,genre.isSelected,genre.id)

                    notifyDataSetChanged()

            }
        }
    }

    object GenresDiffCallBack : DiffUtil.ItemCallback<Genre>() {
        override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem == newItem
        }


    }

    interface GenreClick
    {
        fun genreOnClick(genre:String,isSelected:Boolean,id:Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresViewHolder {
        genreBinding = GenerLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return GenresViewHolder(genreBinding)
    }

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem,position)
    }
}