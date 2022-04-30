package com.example.submissionaplikasistoryappkriteria.di

import android.content.Context
import com.example.submissionaplikasistoryappkriteria.api.ApiConfig
import com.example.submissionaplikasistoryappkriteria.api.ApiService
import com.example.submissionaplikasistoryappkriteria.data.repository.UserStoryRepository

object Injection {
    fun provideRepository(context: Context): UserStoryRepository {

        val apiService = ApiConfig.getApiService()
        return UserStoryRepository(context,apiService)
    }
}