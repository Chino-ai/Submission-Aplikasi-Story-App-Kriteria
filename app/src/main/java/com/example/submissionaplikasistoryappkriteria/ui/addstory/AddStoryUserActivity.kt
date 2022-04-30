package com.example.submissionaplikasistoryappkriteria.ui.addstory

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.submissionaplikasistoryappkriteria.databinding.ActivityAddStoryuserBinding
import com.example.submissionaplikasistoryappkriteria.rotateBitmap
import com.example.submissionaplikasistoryappkriteria.ui.storyuser.StoryActivity
import com.example.submissionaplikasistoryappkriteria.uriToFile
import com.example.submissionaplikasistoryappkriteria.viewModel.ViewModelFactory

import java.io.File


class AddStoryUserActivity : AppCompatActivity() {

    private lateinit var addStoryUserBinding: ActivityAddStoryuserBinding
    private var getFile: File? = null
    private lateinit var addStoryUserViewModel: AddStoryUserViewModel


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        addStoryUserBinding = ActivityAddStoryuserBinding.inflate(layoutInflater)
        setContentView(addStoryUserBinding.root)

        addStoryUserViewModel =
            ViewModelProvider(this, ViewModelFactory(this))[AddStoryUserViewModel::class.java]


        addStoryUserViewModel.toast.observe(this) {
            it.getContentIfNotHandled()?.let {
                showToast(it)
            }
        }

        addStoryUserViewModel.change.observe(this) {
            changePage(it)
        }

        addStoryUserViewModel.isLoading.observe(this) {
            showLoading(it)
        }



        addStoryUserBinding.cameraXButton.setOnClickListener { startCameraX() }
        addStoryUserBinding.galleryButton.setOnClickListener { startGallery() }
        addStoryUserBinding.uploadButton.setOnClickListener {
            addStoryUserViewModel.getDescriptionResult(addStoryUserBinding.description.text.toString())
            addStoryUserViewModel.uploadImage()

        }
    }

    companion object {
        const val CAMERA_X_RESULT = 200


        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            addStoryUserBinding.progressBar.visibility = View.VISIBLE
        } else {
            addStoryUserBinding.progressBar.visibility = View.GONE
        }
    }

    private fun changePage(value: Boolean) {
        if (value) {
            val intent = Intent(this@AddStoryUserActivity, StoryActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()

        }
    }

    private fun showToast(value: String) {
        when (value) {
            "berhasil" -> Toast.makeText(
                this@AddStoryUserActivity,
                "Uploaded successfully",
                Toast.LENGTH_SHORT
            ).show()

            "file_besar" -> Toast.makeText(
                this@AddStoryUserActivity,
                "No Description or Image",
                Toast.LENGTH_SHORT
            ).show()

            "gagal" -> Toast.makeText(
                this@AddStoryUserActivity,
                "Connection Failed",
                Toast.LENGTH_SHORT
            ).show()

            "masukan_berkas" -> Toast.makeText(
                this@AddStoryUserActivity,
                "Please enter the image file first.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }


    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            getFile = myFile
            addStoryUserViewModel.getFileResult(getFile)
            val result = rotateBitmap(
                BitmapFactory.decodeFile(getFile?.path),
                isBackCamera
            )

            addStoryUserBinding.previewImageView.setImageBitmap(result)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri

            val myFile = uriToFile(selectedImg, this@AddStoryUserActivity)

            getFile = myFile
            addStoryUserViewModel.getFileResult(getFile)

            addStoryUserBinding.previewImageView.setImageURI(selectedImg)
        }
    }


}