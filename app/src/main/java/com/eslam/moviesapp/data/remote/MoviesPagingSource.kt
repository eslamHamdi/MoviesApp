package com.eslam.moviesapp.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.eslam.moviesapp.data.repositories.RemoteDataSource
import com.eslam.moviesapp.domain.models.Movie
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MoviesPagingSource @Inject constructor(private val remoteDataSource: RemoteDataSource,private val genre:String,
private val genreTitle:String):
    PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
       return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val pageIndex = params.key ?: 1
        return  try {

            val response = remoteDataSource.getMoviesByGenre(genre,
                pageIndex)


            val nextKey =
                if (response?.totalPages == pageIndex || response?.totalPages ==0) {

                    null
                } else {
                    // By default, initial load size = 3 * NETWORK PAGE SIZE
                    // ensure we're not requesting duplicating items at the 2nd request
                    pageIndex + 1

                }


            LoadResult.Page(
                data = response?.movieList?.onEach {
                    it.genre = genreTitle
                }!!,
                prevKey = if (pageIndex == 1) null else pageIndex-1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {

            return LoadResult.Error(exception)
        } catch (exception: HttpException) {

            return LoadResult.Error(exception)
        }catch (e:Exception)
        {

            return LoadResult.Error(e)
        }
    }



}