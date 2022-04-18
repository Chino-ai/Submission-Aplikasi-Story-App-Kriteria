package com.example.submissionaplikasistoryappkriteria.ui.detailuserstory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class DetailUserViewModel: ViewModel() {
    private val _description = MutableLiveData<String>()
    val description: LiveData<String> =_description

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _avatar = MutableLiveData<String>()
    val avatar: LiveData<String> = _avatar


     fun getDetailUser(username: String,description: String, avatar : String ){
         _username.value = username
         _description.value = description
         _avatar.value = avatar
     }

}