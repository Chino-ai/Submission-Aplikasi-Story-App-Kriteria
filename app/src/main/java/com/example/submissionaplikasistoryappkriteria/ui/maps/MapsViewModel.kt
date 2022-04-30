package com.example.submissionaplikasistoryappkriteria.ui.maps

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissionaplikasistoryappkriteria.Event
import com.example.submissionaplikasistoryappkriteria.api.ApiConfig
import com.example.submissionaplikasistoryappkriteria.data.remote.remote.GetAllStoriesMapsResponse
import com.example.submissionaplikasistoryappkriteria.data.remote.remote.ListStoryMapsItem
import com.example.submissionaplikasistoryappkriteria.ui.register.UserPreference
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsViewModel(private val context: Context): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _userStories = MutableLiveData<List<ListStoryMapsItem>>()
    val userStories: LiveData<List<ListStoryMapsItem>> = _userStories
    private val _toast = MutableLiveData<Event<Boolean>>()
    val toast: LiveData<Event<Boolean>> = _toast
    private val _lat = MutableLiveData<Double>()
    val lat: LiveData<Double> = _lat
    private val _lon = MutableLiveData<Double>()
    val lon: LiveData<Double> = _lon
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name
    private val _description = MutableLiveData<String>()
    val description: LiveData<String> = _description

    private lateinit var sharedPreference: UserPreference

    companion object{
        private const val TAG = "MapsViewModel"
    }

    init {
        getAllStories()
    }

    private fun getAllStories() {
        sharedPreference = UserPreference(context)
        _isLoading.value = true
        viewModelScope.launch {
            val client =
                ApiConfig.getApiService()
                    .getMaps(sharedPreference.getPreferenceString("token"))

            client.enqueue(object : Callback<GetAllStoriesMapsResponse> {
                override fun onResponse(
                    call: Call<GetAllStoriesMapsResponse>,
                    response: Response<GetAllStoriesMapsResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                           _userStories.value = responseBody.listStory



                        }
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")

                    }

                }

                override fun onFailure(call: Call<GetAllStoriesMapsResponse>, t: Throwable) {
                    _isLoading.value = false
                    _toast.value = Event(true)
                    Log.e(TAG, "onFailure: ${t.message}")


                }

            })
        }
    }
}