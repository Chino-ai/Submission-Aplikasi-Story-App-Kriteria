package com.example.submissionaplikasistoryappkriteria.ui.storyuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionaplikasistoryappkriteria.R
import com.example.submissionaplikasistoryappkriteria.data.remote.remote.ListStoryItem
import com.example.submissionaplikasistoryappkriteria.databinding.ActivityStoryBinding
import com.example.submissionaplikasistoryappkriteria.ui.addstory.AddStoryUserActivity
import com.example.submissionaplikasistoryappkriteria.ui.login.LoginActivity
import com.example.submissionaplikasistoryappkriteria.ui.register.UserPreference
import com.example.submissionaplikasistoryappkriteria.viewModel.ViewModelFactory

class StoryActivity : AppCompatActivity() {

    private lateinit var storyBinding: ActivityStoryBinding


    private var sharedPreference: UserPreference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        storyBinding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(storyBinding.root)
        val storyViewModel = ViewModelProvider(this, ViewModelFactory(this)).get(
            StoryViewModel::class.java
        )
        sharedPreference = UserPreference(this)

        storyViewModel.noData.observe(this) {
            storyBinding.noData.text = it
        }

        storyViewModel.userStories.observe(this) {
            setUserData(it)

        }

        storyViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        storyViewModel.refresh.observe(this) {
            showRefresh(it)
        }

        storyViewModel.toast.observe(this) {
            it.getContentIfNotHandled()?.let {
                showToast(it)
            }
        }

        storyBinding.refresh.setOnClickListener {
            val intent = Intent(this@StoryActivity, StoryActivity::class.java)
            startActivity(intent)
        }

        storyBinding.fabAdd.setOnClickListener {
            val intent = Intent(this@StoryActivity, AddStoryUserActivity::class.java)
            startActivity(intent)
        }

        showRecycleView()


    }

    private fun setUserData(userData: List<ListStoryItem>) {
        val listReview = ArrayList<ListStoryItem>()
        for (review in userData) {
            listReview.add(review)
        }
        val adapter = ListUserStoryAdapter(listReview)
        storyBinding.rvGithub.adapter = adapter


    }

    private fun showRefresh(value: Boolean) {
        if (value) {
            storyBinding.refresh.visibility = View.VISIBLE
        } else {
            storyBinding.refresh.visibility = View.GONE
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            storyBinding.progressBar.visibility = View.VISIBLE
        } else {
            storyBinding.progressBar.visibility = View.GONE
        }
    }

    private fun showToast(isToast: Boolean) {
        val caution = "No Internet Connection"
        if (isToast) {

            Toast.makeText(this@StoryActivity, caution, Toast.LENGTH_SHORT).show()
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
            }

        }
        return super.onOptionsItemSelected(item)
    }
}