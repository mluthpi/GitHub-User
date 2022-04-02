package com.example.githubuser.presentation.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.databinding.ActivityDetailsBinding
import com.example.githubuser.model.UserDetailsResponse
import com.example.githubuser.model.UserItem
import com.example.githubuser.presentation.main.MainViewModel
import com.example.githubuser.utils.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.math.log

class DetailsActivity : AppCompatActivity() {

    companion object {
        const val USERNAME = "USERNAME"

        private val TAB_TITLES = arrayListOf(
            "Followers",
            "Following"
        )
    }

    private lateinit var detailsViewModel: DetailsViewModel

    private var _binding: ActivityDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var username: String
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupView()

        detailsViewModel.getDetailsUser(username)
        detailsViewModel.detailsUser.observe(this, { userDetails ->
            showUserDetails(userDetails)
        })

        detailsViewModel.isLoading.observe(this, { isLoading ->
            showLoading(isLoading)
        })

    }

    private fun setupView() {
        username = intent.getStringExtra(USERNAME)!!
        setupTabLayout(username)
    }

    private fun setupViewModel() {
        detailsViewModel = obtainViewModel(this)
    }

    private fun setupTabLayout(username: String) {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, username)
        val viewPager: ViewPager2 = findViewById(R.id.vp_details)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = TAB_TITLES[position]
        }.attach()
        supportActionBar?.elevation = 0f
    }

    private fun showUserDetails(userDetailsResponse: UserDetailsResponse) {
        Glide.with(this)
            .load(userDetailsResponse.avatarUrl)
            .into(binding.imgAvatar)
        binding.tvFullName.text = userDetailsResponse.name
        binding.tvUsername.text = userDetailsResponse.login
        binding.tvLocation.text = userDetailsResponse.location ?: "-"
        binding.tvCompany.text = userDetailsResponse.company ?: "-"
        binding.tvFollowersCount.text = userDetailsResponse.followers.toString()
        binding.tvFollowingCount.text = userDetailsResponse.following.toString()
        binding.tvRepository.text = userDetailsResponse.publicRepos.toString()

        detailsViewModel.getFavoriteUsers().observe(this, { favUser ->
            val isFavorite = favUser.filter { it.id == userDetailsResponse.id }.isNotEmpty()
            setupFavoriteUser(isFavorite, userDetailsResponse)
        })

    }

    private fun setupFavoriteUser(isFavorite: Boolean, user: UserDetailsResponse) {
        if (isFavorite) {
            binding.fbFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)

            binding.fbFavorite.setOnClickListener {
                val user = UserItem(
                    id = user.id,
                    login = user.login,
                    avatarUrl = user.avatarUrl
                )
                detailsViewModel.deleteFromDB(user)
                Toast.makeText(this, "Berhasil dihapus dari favorite", Toast.LENGTH_SHORT).show()
            }
        } else {
            binding.fbFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)

            binding.fbFavorite.setOnClickListener {
                val user = UserItem(
                    id = user.id,
                    login = user.login,
                    avatarUrl = user.avatarUrl
                )
                detailsViewModel.insertToDB(user)
                Toast.makeText(this, "Berhasil ditambahkan ke favorite", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailsViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(DetailsViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}