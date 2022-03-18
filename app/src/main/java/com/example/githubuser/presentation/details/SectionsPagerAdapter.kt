package com.example.githubuser.presentation.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubuser.presentation.details.followers.FollowersFragment
import com.example.githubuser.presentation.details.following.FollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity, val username: String) : FragmentStateAdapter(activity) {
 
    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null

        val bundle = Bundle()
        val followersFragment = FollowersFragment()
        val followingFragment = FollowingFragment()

        bundle.putString("USERNAME", username)

        followersFragment.arguments = bundle
        followingFragment.arguments = bundle

        when (position) {
            0 -> fragment = followersFragment
            1 -> fragment = followingFragment
        }
        return fragment as Fragment
    }
 
    override fun getItemCount(): Int {
        return 2
    }
}