package com.example.submissionaplikasistoryappkriteria.ui.register


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionaplikasistoryappkriteria.Event
import com.example.submissionaplikasistoryappkriteria.api.ApiConfig
import com.example.submissionaplikasistoryappkriteria.data.remote.remote.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {

    private val _toast = MutableLiveData<Boolean>()
    val toast: LiveData<Boolean> = _toast
    private val _noConnections = MutableLiveData<Boolean>()
    val noConnections: LiveData<Boolean> = _noConnections

    fun uploadRegister(name: String, email: String, password: String) {
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
                _toast.value = true
                if (response.isSuccessful) {
                    val responseBody = response.body()


                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _noConnections.value = false
                _toast.value = false


            }
        })
    }
}
