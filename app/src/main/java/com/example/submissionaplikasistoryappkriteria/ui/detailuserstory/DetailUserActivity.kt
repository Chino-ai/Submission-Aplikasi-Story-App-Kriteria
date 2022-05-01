package com.example.submissionaplikasistoryappkriteria.ui.detailuserstory


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submissionaplikasistoryappkriteria.data.remote.remote.ListStoryItem


import com.example.submissionaplikasistoryappkriteria.databinding.ActivityDetailUserBinding


class DetailUserActivity : AppCompatActivity() {
    private val detailUserViewModel by viewModels<DetailUserViewModel>()
    private lateinit var detailUserBinding : ActivityDetailUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        detailUserBinding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(detailUserBinding.root)

       val user = intent.getParcelableExtra<ListStoryItem>(EXTRA_USER) as ListStoryItem
        detailUserViewModel.getDetailUser(user.name, user.description,user.photoUrl)

        detailUserViewModel.username.observe(this){
            detailUserBinding.vUsername.text = it
        }

        detailUserViewModel.description.observe(this){
            detailUserBinding.vDescription.text = it
        }

       detailUserViewModel.avatar.observe(this){
            Glide.with(detailUserBinding.avatar)
                .load(user.photoUrl)
                .apply(RequestOptions().override(640, 640))
                .into(detailUserBinding.avatar)
        }

        detailUserBinding.progressBar.visibility = View.GONE

    }

    companion object{
        const val EXTRA_USER = "extra_user"
    }


}