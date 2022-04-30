package com.example.submissionaplikasistoryappkriteria.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.submissionaplikasistoryappkriteria.api.ApiService
import com.example.submissionaplikasistoryappkriteria.data.QuotePagingSource
import com.example.submissionaplikasistoryappkriteria.data.remote.remote.GetAllStoriesResponse
import com.example.submissionaplikasistoryappkriteria.data.remote.remote.ListStoryItem
import com.example.submissionaplikasistoryappkriteria.data.remote.remote.QuoteResponseItem

class UserStoryRepository(private val context: Context, private val apiService: ApiService) {
    fun getUserStory(): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,

            ),
            pagingSourceFactory = {
                QuotePagingSource(context,apiService)
            }
        ).liveData
    }
}