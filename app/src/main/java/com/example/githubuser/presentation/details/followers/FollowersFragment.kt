package com.example.githubuser.presentation.details.followers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.databinding.FragmentFollowersBinding
import com.example.githubuser.model.User

class FollowersFragment : Fragment() {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val followersViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowersViewModel::class.java)

        val username = arguments?.getString("USERNAME")!!
        followersViewModel.getFollowers(username)
        followersViewModel.listFollowers.observe(requireActivity(), { listFollowers ->
            showListUser(listFollowers)
        })

        followersViewModel.isLoading.observe(requireActivity(), { isLoading ->
            showLoading(isLoading)
        })
    }

    private fun showListUser(listFollowers: List<User>) {
        val followersAdapter = FollowersAdapter()
        followersAdapter.addItems(listFollowers)

        binding.rvFollowers.apply {
            layoutManager = LinearLayoutManager(
                requireActivity(),
                RecyclerView.VERTICAL,
                false
            )
            adapter = followersAdapter
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
        _binding = FragmentFollowersBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}