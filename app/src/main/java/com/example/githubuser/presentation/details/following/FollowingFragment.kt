package com.example.githubuser.presentation.details.following

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.R
import com.example.githubuser.databinding.FragmentFollowingBinding
import com.example.githubuser.model.User

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val followingViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowingViewModel::class.java)

        val username = arguments?.getString("USERNAME")!!
        followingViewModel.getFollowing(username)
        followingViewModel.listFollowing.observe(requireActivity(), { listFollowing ->
            showListUser(listFollowing)
        })

        followingViewModel.isLoading.observe(requireActivity(), { isLoading ->
            showLoading(isLoading)
        })
    }

    private fun showListUser(listFollowing: List<User>) {
        val followingAdapter = FollowingAdapter()
        followingAdapter.addItems(listFollowing)

        binding.rvFollowers.apply {
            layoutManager = LinearLayoutManager(
                requireActivity(),
                RecyclerView.VERTICAL,
                false
            )
            adapter = followingAdapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollowingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


}