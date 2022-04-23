package com.example.submissionaplikasistoryappkriteria.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.submissionaplikasistoryappkriteria.R
import com.example.submissionaplikasistoryappkriteria.databinding.ActivityLoginBinding
import com.example.submissionaplikasistoryappkriteria.ui.addstory.AddStoryUserViewModel
import com.example.submissionaplikasistoryappkriteria.ui.register.RegisterActivity
import com.example.submissionaplikasistoryappkriteria.ui.register.UserPreference
import com.example.submissionaplikasistoryappkriteria.ui.storyuser.StoryActivity
import com.example.submissionaplikasistoryappkriteria.viewModel.ViewModelFactory

class LoginActivity : AppCompatActivity() {

    private var emailValid = false



    private lateinit var loginBinding: ActivityLoginBinding
    private var sharedPreference: UserPreference? = null
    private lateinit var loginViewModel : LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        loginViewModel = ViewModelProvider(this, ViewModelFactory(this)).get(
            LoginViewModel::class.java
        )
        sharedPreference = UserPreference(this)

        loginViewModel.noConnections.observe(this) {

            allowLogin(it)

        }

        loginViewModel.isLoading.observe(this){
            showLoading(it)
        }





        val strLoginStatus = sharedPreference?.getPreferenceString("login_status")
        if (strLoginStatus != null) {
            val intent = Intent(this@LoginActivity, StoryActivity::class.java)
            startActivity(intent)
            finish()
        }

        loginBinding.btnLogin.setOnClickListener {
            val strEmail = loginBinding.edtEmail.text.toString()
            val strPassword = loginBinding.edtPassword.text.toString()

            if (strEmail == "" || strPassword == "") {
                Toast.makeText(this@LoginActivity, "Please Enter Details.", Toast.LENGTH_SHORT)
                    .show()
            } else {

                val emailId = sharedPreference?.getPreferenceString("email")
                val password = sharedPreference?.getPreferenceString("password")
                loginViewModel.userLogin(emailId, password)


                if (emailId.equals(strEmail) && password.equals(strPassword)) {
                    sharedPreference?.save_String("login_status", "1")

                } else {
                    Toast.makeText(this, "User Not Available.", Toast.LENGTH_SHORT).show()
                }
            }
        }




        loginBinding.txtRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }



        loginBinding.edtEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                validateEmail()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            loginBinding.progressBar.visibility = View.VISIBLE
        } else {
            loginBinding.progressBar.visibility = View.GONE
        }
    }

    private fun allowLogin(value: Boolean) {
        if (value) {

            val intent = Intent(this@LoginActivity, StoryActivity::class.java)
            startActivity(intent)

            Toast.makeText(this, "Successfully Login.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(
                this, "Failed Cause No Connection", Toast.LENGTH_SHORT
            )
                .show()
        }
    }


    fun validateEmail() {

        val input = loginBinding.edtEmail.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(input).matches()) {
            emailValid = false
            showEmailExistAlert(true)
        } else {
            emailValid = true
            showEmailExistAlert(false)
        }

    }


    private fun showEmailExistAlert(isNotValid: Boolean) {
        loginBinding.edtEmail.error = if (isNotValid) getString(R.string.email_not_valid) else null
    }


}