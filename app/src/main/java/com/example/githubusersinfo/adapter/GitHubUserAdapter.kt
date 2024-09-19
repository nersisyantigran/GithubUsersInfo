package com.example.githubusersinfo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.githubusersinfo.model.GitHubUser
import com.example.githubusersinfo.R

class GitHubUserAdapter(private val users: List<GitHubUser>, private val onItemClick: (GitHubUser) -> Unit) : RecyclerView.Adapter<GitHubUserAdapter.UserViewHolder>() {

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val avatar = itemView.findViewById<ImageView>(R.id.avatarImageView)

        private val username = itemView.findViewById<TextView>(R.id.usernameTextView)
        private val userId = itemView.findViewById<TextView>(R.id.userIdTextView)

        fun bind(user: GitHubUser) {
            avatar.load(user.avatar_url) {
                transformations(CircleCropTransformation())
            }

            username.text = user.login
            userId.text = "ID: ${user.id}"

            itemView.setOnClickListener { onItemClick(user) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount() = users.size
}
