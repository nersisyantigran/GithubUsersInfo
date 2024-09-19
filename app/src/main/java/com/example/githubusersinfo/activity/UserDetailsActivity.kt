package com.example.githubusersinfo.activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.load
import coil.transform.CircleCropTransformation
import com.example.githubusersinfo.R
import com.example.githubusersinfo.model.GitHubUserDetail
import com.example.githubusersinfo.service.GitHubService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserDetailsActivity : AppCompatActivity() {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val githubService = retrofit.create(GitHubService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        val username = intent.getStringExtra("username")

        username?.let {
            fetchUserDetails(it)
        }
    }

    private fun fetchUserDetails(username: String) {
        githubService.getUserDetails(username).enqueue(object : Callback<GitHubUserDetail> {
            override fun onResponse(call: Call<GitHubUserDetail>, response: Response<GitHubUserDetail>) {
                if (response.isSuccessful) {
                    val userDetails = response.body()
                    if (userDetails != null) {
                        updateUI(userDetails)
                    } else {
                        Toast.makeText(this@UserDetailsActivity, "No user details found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@UserDetailsActivity, "Failed to fetch user details: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GitHubUserDetail>, t: Throwable) {
                Toast.makeText(this@UserDetailsActivity, "Error fetching user details: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateUI(userDetails: GitHubUserDetail) {
        findViewById<ImageView>(R.id.avatarImageView).load(userDetails.avatar_url) {
            transformations(CircleCropTransformation())
        }
        findViewById<TextView>(R.id.fullNameTextView).text = userDetails.name ?: "N/A"
        findViewById<TextView>(R.id.locationTextView).text = userDetails.location ?: "Unknown"
        findViewById<TextView>(R.id.followersTextView).text = "Followers: ${userDetails.followers}"
        findViewById<TextView>(R.id.followingTextView).text = "Following: ${userDetails.following}"
        findViewById<TextView>(R.id.bioTextView).text = ("Bio: \n${userDetails.bio ?: ""}").toString()
        findViewById<TextView>(R.id.repoTextView).text = ("Public repository: \n${userDetails.public_repos ?: ""}").toString()
        findViewById<TextView>(R.id.gistTextView).text = ("Public gists: \n${userDetails.public_gists ?: ""}").toString()
        findViewById<TextView>(R.id.updatedTextView).text = ("Updated at: \n${userDetails.updated_at ?: ""}").toString()
    }
}
