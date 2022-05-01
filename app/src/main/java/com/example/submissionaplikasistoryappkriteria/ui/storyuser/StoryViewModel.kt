package com.example.submissionaplikasistoryappkriteria.ui.storyuser


import androidx.lifecycle.LiveData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.submissionaplikasistoryappkriteria.data.remote.remote.ListStoryItem
import com.example.submissionaplikasistoryappkriteria.data.repository.UserStoryRepository

class StoryViewModel(userStoryRepository: UserStoryRepository) : ViewModel() {


    val userStory: LiveData<PagingData<ListStoryItem>> =
        userStoryRepository.getUserStory().cachedIn(viewModelScope)




}



