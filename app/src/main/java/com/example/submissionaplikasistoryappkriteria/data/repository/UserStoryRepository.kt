package com.example.submissionaplikasistoryappkriteria.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.*
import com.example.submissionaplikasistoryappkriteria.api.ApiService
import com.example.submissionaplikasistoryappkriteria.data.QuotePagingSource
import com.example.submissionaplikasistoryappkriteria.data.StoryRemoteMediator
import com.example.submissionaplikasistoryappkriteria.data.remote.remote.GetAllStoriesResponse
import com.example.submissionaplikasistoryappkriteria.data.remote.remote.ListStoryItem
import com.example.submissionaplikasistoryappkriteria.data.remote.remote.QuoteResponseItem
import com.example.submissionaplikasistoryappkriteria.database.StoryDatabase

class UserStoryRepository(private val storyDatabase: StoryDatabase,private val context: Context, private val apiService: ApiService) {
    @OptIn(ExperimentalPagingApi::class)
    fun getUserStory(): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,

            ),
            remoteMediator = StoryRemoteMediator(storyDatabase, apiService,context),
            pagingSourceFactory = {
//                QuotePagingSource(apiService)
                storyDatabase.storyDao().getAllStory()
            }
        ).liveData
    }
}