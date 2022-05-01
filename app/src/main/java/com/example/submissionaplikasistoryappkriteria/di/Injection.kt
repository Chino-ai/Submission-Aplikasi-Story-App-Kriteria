package com.example.submissionaplikasistoryappkriteria.di

import android.content.Context
import com.example.submissionaplikasistoryappkriteria.api.ApiConfig
import com.example.submissionaplikasistoryappkriteria.api.ApiService
import com.example.submissionaplikasistoryappkriteria.data.repository.UserStoryRepository
import com.example.submissionaplikasistoryappkriteria.database.StoryDatabase

object Injection {
    fun provideRepository(context: Context): UserStoryRepository {
        val database = StoryDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return UserStoryRepository(database,context,apiService)
    }
}