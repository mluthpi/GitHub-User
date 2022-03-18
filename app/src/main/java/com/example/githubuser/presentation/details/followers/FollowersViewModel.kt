package com.example.githubuser.presentation.details.followers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.model.SearchUserResponse
import com.example.githubuser.model.User
import com.example.githubuser.model.UserItem
import com.example.githubuser.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {

    companion object{
        private const val TAG = "FollowersViewModel"
    }

    private val _listFollowers = MutableLiveData<List<User>>()
    val listFollowers: LiveData<List<User>> = _listFollowers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFollowers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowers(username)
        client.enqueue(object : Callback<List<User>> {
            override fun onResponse(
                call: Call<List<User>>,
                response: Response<List<User>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listFollowers.value = response.body() ?: emptyList()
                } else {
                    _listFollowers.value = emptyList()
                    Log.e(TAG, "onFailure onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure onFailure: ${t.message.toString()}")
            }

        })
    }

}