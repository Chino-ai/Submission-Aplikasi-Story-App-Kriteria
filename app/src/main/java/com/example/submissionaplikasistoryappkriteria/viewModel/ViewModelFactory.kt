package com.example.submissionaplikasistoryappkriteria.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.submissionaplikasistoryappkriteria.ui.addstory.AddStoryUserViewModel
import com.example.submissionaplikasistoryappkriteria.ui.login.LoginViewModel
import com.example.submissionaplikasistoryappkriteria.ui.storyuser.StoryViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StoryViewModel::class.java)) {
            return StoryViewModel(context) as T
        }
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(context) as T
        }

        if (modelClass.isAssignableFrom(AddStoryUserViewModel::class.java)) {
            return AddStoryUserViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}