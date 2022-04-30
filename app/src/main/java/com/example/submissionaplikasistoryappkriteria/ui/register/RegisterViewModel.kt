package com.example.submissionaplikasistoryappkriteria.ui.register


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissionaplikasistoryappkriteria.Event
import com.example.submissionaplikasistoryappkriteria.api.ApiConfig
import com.example.submissionaplikasistoryappkriteria.data.remote.remote.RegisterResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {

    private val _connection = MutableLiveData<Boolean>()
    val connection: LiveData<Boolean> = _connection

    private val _toast = MutableLiveData<Event<Boolean>>()
    val toast: LiveData<Event<Boolean>> = _toast

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    companion object {
        private const val TAG = "RegisterViewModel"
    }


    fun uploadRegister(name: String, email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val service = ApiConfig.getApiService().register(
                name,
                email,
                password,
            )


            service.enqueue(object : Callback<RegisterResponse> {

                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {

                            if (!responseBody.error && responseBody.message == "User created") {
                                _connection.value = true
                                _toast.value = Event(false)
                            }

                        }

                    } else {

                        Log.e(TAG, "onResponse: ${response.message()}")
                        _isLoading.value = false
                        _connection.value = false
                        _toast.value = Event(true)

                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    _isLoading.value = false
                    _connection.value = false
                    Log.e(TAG, "onFailure: ${t.message}")


                }
            })
        }
    }
}
