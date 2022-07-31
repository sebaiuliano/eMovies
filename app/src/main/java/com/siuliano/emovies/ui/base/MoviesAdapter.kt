package com.siuliano.emovies.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.siuliano.emovies.databinding.ItemMovieBinding
import com.siuliano.emovies.model.movie.Movie
import com.siuliano.emovies.model.movie.MovieMinimalData
import com.siuliano.emovies.utils.StringUtils

class MoviesAdapter : RecyclerView.Adapter<MovieViewHolder>() {
    private var movieList : List<MovieMinimalData> = ArrayList()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemMovieBinding.inflate(inflater, viewGroup,false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun setMovies(movies: List<MovieMinimalData>) {
        val oldList = movieList
        movieList = movies
        DiffUtil
            .calculateDiff(MovieDiffCallback(oldList, movieList))
            .dispatchUpdatesTo(this)
    }
}

class MovieViewHolder(
    private val binding: ItemMovieBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(movie: MovieMinimalData) {
        if (movie.posterPath != null) {
            Glide.with(itemView.context)
                .load(StringUtils.getThumbnailUrl(movie.posterPath))
                .into(binding.ivMovie)
        }
    }
}

class MovieDiffCallback(
    private val oldList: List<MovieMinimalData>,
    private val newList: List<MovieMinimalData>
): DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size


    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]


}