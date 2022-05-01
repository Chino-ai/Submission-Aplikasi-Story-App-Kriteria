package com.example.submissionaplikasistoryappkriteria.data

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.submissionaplikasistoryappkriteria.api.ApiService
import com.example.submissionaplikasistoryappkriteria.data.remote.remote.ListStoryItem
import com.example.submissionaplikasistoryappkriteria.database.StoryDatabase
import com.example.submissionaplikasistoryappkriteria.ui.register.UserPreference

@OptIn(ExperimentalPagingApi::class)
class StoryRemoteMediator(

    private val database: StoryDatabase,
    private val apiService: ApiService,
    private val context: Context
) : RemoteMediator<Int, ListStoryItem>() {
    private  var sharedPreference: UserPreference? = null
    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ListStoryItem>
    ): MediatorResult {
        sharedPreference = UserPreference(context)
        val page = INITIAL_PAGE_INDEX
        return try {
            val responseData = apiService.getAllStories(page, state.config.pageSize,sharedPreference?.getPreferenceString("token"))
            val endOfPaginationReached = responseData.listStory.isEmpty()
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.storyDao().deleteAll()
                }
                database.storyDao().insertStory(responseData.listStory)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            MediatorResult.Error(exception)
        }
    }

}