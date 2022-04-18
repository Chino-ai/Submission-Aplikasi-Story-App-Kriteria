package com.example.submissionaplikasistoryappkriteria.ui.register

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.submissionaplikasistoryappkriteria.R
import com.example.submissionaplikasistoryappkriteria.databinding.ActivityRegisterBinding
import com.example.submissionaplikasistoryappkriteria.ui.addstory.AddStoryUserActivity
import com.example.submissionaplikasistoryappkriteria.ui.login.LoginActivity
import com.example.submissionaplikasistoryappkriteria.ui.storyuser.StoryActivity
import kotlin.properties.Delegates


class RegisterActivity : AppCompatActivity() {

    private var emailValid = false
    private var passwordValid = false


    private val registerViewModel by viewModels<RegisterViewModel>()

    private var sharedPreference: UserPreference? = null
    private lateinit var registerBinding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)






        registerViewModel.toast.observe(this) {

            allowRegister(it)

        }


        registerBinding.btnLogin.setOnClickListener {

            sharedPreference = UserPreference(this)
            val strName = registerBinding.edtName.text.toString()
            val strEmail = registerBinding.edtEmail.text.toString()
            val strPassword = registerBinding.edtPassword.text.toString()

            if (strEmail == "" || strPassword == "" || strName == "") {
                Toast.makeText(this, "Please Enter Details.", Toast.LENGTH_SHORT).show()

            } else {


                sharedPreference?.save_String("name", strName)
                sharedPreference?.save_String("email", strEmail)
                sharedPreference?.save_String("password", strPassword)
                registerViewModel.uploadRegister(strName, strEmail, strPassword)


            }


        }

        registerBinding.edtName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().isNotEmpty()) registerBinding.btnLogin.isEnabled =
                    true else registerBinding.btnLogin.isEnabled = false
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        registerBinding.edtPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
                validatePassword()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        registerBinding.edtEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
                validateEmail()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }


    private fun allowRegister(value: Boolean) {
        if (value) {

            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)

            Toast.makeText(this, "Data Saved Successfully.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this@RegisterActivity, "Failed Cause No Connection", Toast.LENGTH_SHORT)
                .show()
        }
    }


    fun validateEmail() {

        val input = registerBinding.edtEmail.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(input).matches()) {
            emailValid = false
            showEmailExistAlert(true)
            registerBinding.btnLogin.isEnabled = false

        } else {
            emailValid = true
            showEmailExistAlert(false)
            registerBinding.btnLogin.isEnabled = true
        }

    }

    fun validatePassword() {

        val input = registerBinding.edtPassword.text.toString()
        if (input.length < 6) {
            passwordValid = false
            showPasswordMinimalAlert(true)
            registerBinding.btnLogin.isEnabled = false
        } else {
            passwordValid = true
            showPasswordMinimalAlert(false)
            registerBinding.btnLogin.isEnabled = true
        }

    }

    private fun showEmailExistAlert(isNotValid: Boolean) {
        registerBinding.edtEmail.error =
            if (isNotValid) getString(R.string.email_not_valid) else null
    }

    private fun showPasswordMinimalAlert(isNotValid: Boolean) {
        registerBinding.edtPassword.error =
            if (isNotValid) getString(R.string.password_not_valid) else null
    }
}