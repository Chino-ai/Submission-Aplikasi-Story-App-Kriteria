package com.example.submissionaplikasistoryappkriteria.data

import android.content.Context
import android.util.Log
import com.example.submissionaplikasistoryappkriteria.api.ApiService

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.submissionaplikasistoryappkriteria.data.remote.remote.GetAllStoriesResponse
import com.example.submissionaplikasistoryappkriteria.data.remote.remote.ListStoryItem
import com.example.submissionaplikasistoryappkriteria.data.remote.remote.QuoteResponseItem
import com.example.submissionaplikasistoryappkriteria.ui.register.UserPreference


class QuotePagingSource(private val context: Context, private val apiService: ApiService) : PagingSource<Int, ListStoryItem>() {
    private  var sharedPreference: UserPreference? = null



    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
        sharedPreference = UserPreference(context)
        return try {

            val page = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getAllStories(page,params.loadSize,sharedPreference?.getPreferenceString("token"))


            LoadResult.Page(
                data = responseData.listStory,
                prevKey = if (page == 1) null else page - 1,
                nextKey = page + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}