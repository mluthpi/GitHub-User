package com.example.githubuser.presentation.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.R
import com.example.githubuser.databinding.ActivityFavoriteBinding
import com.example.githubuser.model.UserItem
import com.example.githubuser.presentation.details.DetailsActivity
import com.example.githubuser.presentation.details.DetailsViewModel
import com.example.githubuser.utils.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private var _binding: ActivityFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var favoriteViewModel: FavoriteViewModel

    private var favoriteAdapter = FavoriteAdapter {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(DetailsActivity.USERNAME, it.login)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()

        favoriteViewModel.getFavoriteUsers().observe(this, {
            showFavoriteUsers(it)
        })
    }

    private fun setupView() {
        supportActionBar?.title = "Favorite"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupViewModel() {
        favoriteViewModel = obtainViewModel(this)
    }

    private fun showFavoriteUsers(favUserList: List<UserItem>) {
        if (favUserList.isNotEmpty()) {
            favoriteAdapter.addItems(favUserList)

            binding.tvEmptyFavorite.visibility = View.GONE
            binding.rvUsers.visibility = View.VISIBLE
            binding.rvUsers.apply {
                layoutManager = LinearLayoutManager(
                    this@FavoriteActivity,
                    RecyclerView.VERTICAL,
                    false
                )
                adapter = favoriteAdapter
            }
        } else {
            binding.tvEmptyFavorite.visibility = View.VISIBLE
            binding.rvUsers.visibility = View.GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }
}