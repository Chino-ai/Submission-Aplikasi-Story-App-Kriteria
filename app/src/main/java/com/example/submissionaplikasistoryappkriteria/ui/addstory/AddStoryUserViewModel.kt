package com.example.submissionaplikasistoryappkriteria.ui.addstory

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionaplikasistoryappkriteria.Event
import com.example.submissionaplikasistoryappkriteria.api.ApiConfig

import com.example.submissionaplikasistoryappkriteria.data.remote.remote.PhotoUploadResponse
import com.example.submissionaplikasistoryappkriteria.reduceFileImage
import com.example.submissionaplikasistoryappkriteria.ui.register.UserPreference
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AddStoryUserViewModel(private val context: Context): ViewModel() {

    private val _toast = MutableLiveData<Event<String>>()
    val toast: LiveData<Event<String>> = _toast

    private val _change = MutableLiveData<Boolean>()
    val change: LiveData<Boolean> = _change






    var description : String? = null
    var getFile: File? = null

    private  var sharedPreference: UserPreference? = null

   fun getFileResult(getFiles: File?){
       getFile = getFiles
   }

    fun getDescriptionResult(descriptions : String?){
        description = descriptions
    }

    fun uploadImage() {

        if (getFile != null) {
            sharedPreference = UserPreference(context)
            val file = reduceFileImage(getFile as File)

            val description = description?.toRequestBody("text/plain".toMediaType())
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                file.name,
                requestImageFile
            )

            val service = ApiConfig().getApiService().uploadImage(sharedPreference?.getPreferenceString("token"),imageMultipart, description)

            service.enqueue(object : Callback<PhotoUploadResponse> {
                override fun onResponse(
                    call: Call<PhotoUploadResponse>,
                    response: Response<PhotoUploadResponse>
                ) {
                    _change.value = true
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null && !responseBody.error) {
                           _toast.value = Event("berhasil")

                        }
                    } else {
                        _toast.value = Event("file_besar")
                        _change.value = false
                    }
                }
                override fun onFailure(call: Call<PhotoUploadResponse>, t: Throwable) {
                    _toast.value = Event("gagal")
                    _change.value = false
                }
            })
        } else {
            _toast.value = Event("masukan_berkas")
            _change.value = false
        }
    }
}