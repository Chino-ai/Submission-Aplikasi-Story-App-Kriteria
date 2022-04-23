package com.example.submissionaplikasistoryappkriteria.ui.register


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

    private val _toast = MutableLiveData<Boolean>()
    val toast: LiveData<Boolean> = _toast

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun uploadRegister(name: String, email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val service = ApiConfig().getApiService().register(
                name,
                email,
                password
            )

            service.enqueue(object : Callback<RegisterResponse> {

                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    _isLoading.value = false
                    _toast.value = true
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody?.error == true) {
                            _isLoading.value = false
                        }

                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    _isLoading.value = false
                    _toast.value = false


                }
            })
        }
    }
}
