package com.example.githubuser.presentation.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.databinding.ActivityDetailsBinding
import com.example.githubuser.model.UserDetailsResponse
import com.example.githubuser.presentation.main.MainViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailsActivity : AppCompatActivity() {

    companion object {
        const val USERNAME = "USERNAME"

        private val TAB_TITLES = arrayListOf<String>(
            "Followers",
            "Following"
        )
    }

    private var _binding: ActivityDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // get data from intent
        val username = intent.getStringExtra(USERNAME)!!

        // setup tabLayout
        setupTabLayout(username)

        // init viewModel
        val detailsViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailsViewModel::class.java)

        detailsViewModel.getDetailsUser(username)
        detailsViewModel.detailsUser.observe(this, { userDetails ->
            showUserDetails(userDetails)
        })

        detailsViewModel.isLoading.observe(this,{ isLoading ->
            showLoading(isLoading)
        })

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
        binding.tvUsername.text = userDetailsResponse.login
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}