package com.example.githubuser.presentation.details.following

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.databinding.ItemUserBinding
import com.example.githubuser.model.User
import com.example.githubuser.model.UserItem

class FollowingAdapter() : RecyclerView.Adapter<FollowingAdapter.ViewHolder>() {

    private val userItemList = mutableListOf<User>()

    fun addItems(userItemList: List<User>) {
        this.userItemList.clear()
        this.userItemList.addAll(userItemList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemUserBinding =
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemUserBinding)
    }

    override fun getItemCount(): Int = userItemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userItem = userItemList[position]
        holder.bind(userItem)
    }

    inner class ViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(userItem: User) {
            with(binding) {
                Glide.with(binding.root)
                    .load(userItem.avatarUrl)
                    .into(imgAvatar)
                tvUsername.text = userItem.login
            }
        }
    }

}