package com.example.githubusersinfo.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubusersinfo.R
import com.example.githubusersinfo.adapter.GitHubUserAdapter
import com.example.githubusersinfo.model.GitHubUser
import com.example.githubusersinfo.service.GitHubService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GitHubUserAdapter
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val githubService = retrofit.create(GitHubService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchUsers()
    }

    private fun fetchUsers() {
        githubService.getUsers().enqueue(object : Callback<List<GitHubUser>> {
            override fun onResponse(call: Call<List<GitHubUser>>, response: Response<List<GitHubUser>>) {
                if (response.isSuccessful) {
                    val users = response.body()?.take(30)
                    users?.let {
                        adapter = GitHubUserAdapter(it) { user ->
                            val intent = Intent(this@MainActivity, UserDetailsActivity::class.java)
                            intent.putExtra("username", user.login)
                            startActivity(intent)
                        }
                        recyclerView.adapter = adapter
                    }
                }
            }

            override fun onFailure(call: Call<List<GitHubUser>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error fetching users", Toast.LENGTH_SHORT).show()
            }
        })
    }
}


