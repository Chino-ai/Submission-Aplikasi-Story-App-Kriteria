package com.example.submissionaplikasistoryappkriteria.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.submissionaplikasistoryappkriteria.R
import com.example.submissionaplikasistoryappkriteria.databinding.ActivityLoginBinding
import com.example.submissionaplikasistoryappkriteria.ui.register.RegisterActivity
import com.example.submissionaplikasistoryappkriteria.ui.register.UserPreference
import com.example.submissionaplikasistoryappkriteria.ui.storyuser.StoryActivity
import com.example.submissionaplikasistoryappkriteria.viewModel.ViewModelFactory

class LoginActivity : AppCompatActivity() {

    private var emailValid = false
    private var passwordValid = false


    private lateinit var loginBinding: ActivityLoginBinding
    private var sharedPreference: UserPreference? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)
        val loginViewModel =
            ViewModelProvider(this, ViewModelFactory(this))[LoginViewModel::class.java]

        loginViewModel.noConnections.observe(this) {

            allowLogin(it)

        }



        sharedPreference = UserPreference(this)
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


                if (emailId.equals(strEmail) && password.equals(strPassword)) {
                    sharedPreference?.save_String("login_status", "1")
                    loginViewModel.userLogin(emailId, password)
                } else {
                    Toast.makeText(this, "User Not Available.", Toast.LENGTH_SHORT).show()
                }
            }
        }




        loginBinding.txtRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        loginBinding.edtPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                validatePassword()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

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

    private fun allowLogin(value: Boolean) {
        if (value) {
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this@LoginActivity, StoryActivity::class.java)
                startActivity(intent)
            }, delay)
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

    fun validatePassword() {

        val input = loginBinding.edtPassword.text.toString()
        if (input.length < 6) {
            passwordValid = false
            showPasswordMinimalAlert(true)
        } else {
            passwordValid = true
            showPasswordMinimalAlert(false)
        }

    }

    private fun showEmailExistAlert(isNotValid: Boolean) {
        loginBinding.edtEmail.error = if (isNotValid) getString(R.string.email_not_valid) else null
    }

    private fun showPasswordMinimalAlert(isNotValid: Boolean) {
        loginBinding.edtPassword.error =
            if (isNotValid) getString(R.string.password_not_valid) else null
    }

    companion object {
        private const val delay = 500L
    }


}