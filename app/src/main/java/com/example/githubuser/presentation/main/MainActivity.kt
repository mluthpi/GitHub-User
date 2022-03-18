package com.example.githubuser.presentation.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.R
import com.example.githubuser.UserAdapter
import com.example.githubuser.UserData
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.model.UserItem
import com.example.githubuser.presentation.details.DetailsActivity

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val mainAdapter = MainAdapter {
        Toast.makeText(this, it.login, Toast.LENGTH_SHORT).show()

        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(DetailsActivity.USERNAME, it.login)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Github Search"

        val mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        mainViewModel.isLoading.observe(this, {
            showLoading(it)
        })

        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mainViewModel.getUser(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
        mainViewModel.listUser.observe(this, { listUser ->
            showListUser(listUser)
        })
    }

    private fun showListUser(listUser: List<UserItem>) {
        mainAdapter.addItems(listUser)
        binding.rvUsers.apply {
            layoutManager = LinearLayoutManager(
                this@MainActivity,
                RecyclerView.VERTICAL,
                false
            )
            adapter = mainAdapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}