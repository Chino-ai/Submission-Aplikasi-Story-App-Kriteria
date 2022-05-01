package com.example.submissionaplikasistoryappkriteria.ui.storyuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionaplikasistoryappkriteria.R
import com.example.submissionaplikasistoryappkriteria.data.remote.remote.GetAllStoriesMapsResponse
import com.example.submissionaplikasistoryappkriteria.data.remote.remote.GetAllStoriesResponse
import com.example.submissionaplikasistoryappkriteria.databinding.ActivityStoryBinding
import com.example.submissionaplikasistoryappkriteria.ui.addstory.AddStoryUserActivity
import com.example.submissionaplikasistoryappkriteria.ui.login.LoginActivity
import com.example.submissionaplikasistoryappkriteria.ui.maps.MapsActivity
import com.example.submissionaplikasistoryappkriteria.ui.register.UserPreference
import com.example.submissionaplikasistoryappkriteria.viewModel.ViewModelFactory


class StoryActivity : AppCompatActivity() {

    private lateinit var storyBinding: ActivityStoryBinding


    private var sharedPreference: UserPreference? = null
    private lateinit var storyViewModel: StoryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        storyBinding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(storyBinding.root)
        storyViewModel = ViewModelProvider(this, ViewModelFactory(this))[StoryViewModel::class.java]
        sharedPreference = UserPreference(this)




        storyBinding.fabAdd.setOnClickListener {
            val intent = Intent(this@StoryActivity, AddStoryUserActivity::class.java)
            startActivity(intent)
        }

        showRecycleView()
        getData()


    }

    private fun getData() {

        val adapter = ListUserStoryAdapter()
        storyBinding.rvGithub.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        storyViewModel.userStory.observe(this) {

            adapter.submitData(lifecycle, it)






    }
}


private fun showRecycleView() {
    storyBinding.rvGithub.layoutManager = LinearLayoutManager(this)
}

override fun onCreateOptionsMenu(menu: Menu): Boolean {
    val inflater = menuInflater
    inflater.inflate(R.menu.option_menu, menu)

    return true
}

override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
        R.id.log_out -> {
            sharedPreference?.clearSharedPreference()
            Toast.makeText(this, "User LogOut Successfully.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@StoryActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        R.id.maps -> {
            val intent = Intent(this@StoryActivity, MapsActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

        }

    }
    return super.onOptionsItemSelected(item)
}
}
