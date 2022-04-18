package com.example.submissionaplikasistoryappkriteria.ui.storyuser

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submissionaplikasistoryappkriteria.data.remote.remote.ListStoryItem
import com.example.submissionaplikasistoryappkriteria.databinding.ItemRowUserBinding
import com.example.submissionaplikasistoryappkriteria.ui.detailuserstory.DetailUserActivity


class ListUserStoryAdapter(private val listUser: ArrayList<ListStoryItem>) :
    RecyclerView.Adapter<ListUserStoryAdapter.ListViewHolder>() {


    class ListViewHolder(private var binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(listUser: ListStoryItem) {
            with(binding) {
                tvUsername.text = listUser.name
                tvName.text = listUser.description
                tvDetail.setOnClickListener {

                    val intent = Intent(itemView.context, DetailUserActivity::class.java)
                    intent.putExtra(DetailUserActivity.EXTRA_USER, listUser)
                    val optionsCompat: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            itemView.context as Activity,
                            Pair(imgAvatar, "avatar"),

                        )
                    itemView.context.startActivity(intent, optionsCompat.toBundle())
                }

                Glide.with(itemView.context)
                    .load(listUser.photoUrl)
                    .apply(RequestOptions().override(640, 640))
                    .into(imgAvatar)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemRowUserBinding =
            ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(itemRowUserBinding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]
        holder.bind(user)

    }

    override fun getItemCount(): Int = listUser.size


}