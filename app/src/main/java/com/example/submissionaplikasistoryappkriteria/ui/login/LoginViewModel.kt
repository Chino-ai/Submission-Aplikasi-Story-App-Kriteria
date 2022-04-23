package com.example.submissionaplikasistoryappkriteria.ui.login


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissionaplikasistoryappkriteria.api.ApiConfig
import com.example.submissionaplikasistoryappkriteria.data.remote.remote.*
import com.example.submissionaplikasistoryappkriteria.ui.register.UserPreference
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val context: Context) : ViewModel() {


    private val _noConnections = MutableLiveData<Boolean>()
    val noConnections: LiveData<Boolean> = _noConnections
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private  var sharedPreference: UserPreference? = null


    fun userLogin(email: String?, password: String?) {
        sharedPreference = UserPreference(context)
        _isLoading.value = true
        viewModelScope.launch {
            val service = ApiConfig().getApiService().Login(
                email,
                password
            )

            service.enqueue(object : Callback<GetLoginResponse> {
                override fun onResponse(
                    call: Call<GetLoginResponse>,
                    response: Response<GetLoginResponse>
                ) {
                    _isLoading.value = false
                    _noConnections.value = true
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        sharedPreference?.save_String("token","bearer " + (responseBody?.loginResult?.token))

                        if (responseBody?.error == true) {
                            _isLoading.value = false
                        }


                    }
                }

                override fun onFailure(call: Call<GetLoginResponse>, t: Throwable) {
                    _noConnections.value = false
                    _isLoading.value = false
                }
            })
        }
    }


}