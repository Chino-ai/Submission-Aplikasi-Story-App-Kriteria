package com.example.submissionaplikasistoryappkriteria.ui.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionaplikasistoryappkriteria.Event
import com.example.submissionaplikasistoryappkriteria.api.ApiConfig
import com.example.submissionaplikasistoryappkriteria.data.remote.remote.*
import com.example.submissionaplikasistoryappkriteria.ui.register.UserPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val context: Context) : ViewModel() {


    private val _noConnections = MutableLiveData<Boolean>()
    val noConnections: LiveData<Boolean> = _noConnections

    private lateinit var sharedPreference: UserPreference


    fun userLogin(email: String?, password: String?) {
        val service = ApiConfig().getApiService().Login(
            email,
            password
        )

        service.enqueue(object : Callback<GetLoginResponse> {
            override fun onResponse(
                call: Call<GetLoginResponse>,
                response: Response<GetLoginResponse>
            ) {
                _noConnections.value = true
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {

                        sharedPreference = UserPreference(context)
                        sharedPreference.save_String(
                            "token",
                            "bearer " + responseBody.loginResult.token
                        )

                    }


                }
            }

            override fun onFailure(call: Call<GetLoginResponse>, t: Throwable) {
                _noConnections.value = false
            }
        })
    }


}