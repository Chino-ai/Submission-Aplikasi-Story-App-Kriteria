package com.example.submissionaplikasistoryappkriteria.ui.storyuser

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionaplikasistoryappkriteria.Event
import com.example.submissionaplikasistoryappkriteria.api.ApiConfig
import com.example.submissionaplikasistoryappkriteria.data.remote.remote.GetAllStoriesResponse
import com.example.submissionaplikasistoryappkriteria.data.remote.remote.ListStoryItem
import com.example.submissionaplikasistoryappkriteria.ui.register.UserPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoryViewModel(private val context: Context) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _userStories = MutableLiveData<List<ListStoryItem>>()
    val userStories: LiveData<List<ListStoryItem>> = _userStories
    private val _toast = MutableLiveData<Event<Boolean>>()
    val toast: LiveData<Event<Boolean>> = _toast
    private val _noData = MutableLiveData<String>()
    val noData: LiveData<String> = _noData
    private val _refresh = MutableLiveData<Boolean>()
    val refresh: LiveData<Boolean> = _refresh
    private lateinit var sharedPreference: UserPreference


    companion object {
        private const val TAG = "StoryViewModel"
    }

    init {
        getAllStories()
    }

    private fun getAllStories() {
        sharedPreference = UserPreference(context)
        _isLoading.value = true
        val client =
            ApiConfig().getApiService().getAllStories(sharedPreference.getPreferenceString("token"))

        client.enqueue(object : Callback<GetAllStoriesResponse> {
            override fun onResponse(
                call: Call<GetAllStoriesResponse>,
                response: Response<GetAllStoriesResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()

                    if (responseBody != null) {
                        _userStories.value = responseBody.listStory
                        _noData.value = ""
                        _refresh.value = false

                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")

                }

            }

            override fun onFailure(call: Call<GetAllStoriesResponse>, t: Throwable) {
                _isLoading.value = false
                _toast.value = Event(true)
                _noData.value = "No User"
                _refresh.value = true
                Log.e(TAG, "onFailure: ${t.message}")


            }

        })
    }
}