package com.example.githubuser.presentation.details

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.model.*
import com.example.githubuser.network.ApiConfig
import com.example.githubuser.presentation.details.followers.FollowersViewModel
import com.example.githubuser.presentation.details.following.FollowingViewModel
import com.example.githubuser.repository.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsViewModel(application: Application) : ViewModel() {
    private val mUserRepository: UserRepository = UserRepository(application)

    companion object{
        private const val TAG = "DetailsViewModel"
    }

    private val _detailsUser = MutableLiveData<UserDetailsResponse>()
    val detailsUser: LiveData<UserDetailsResponse> = _detailsUser

    private val _repositoryNumber = MutableLiveData<Int>()
    val repositoryNumber: LiveData<Int> = _repositoryNumber

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getDetailsUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserDetails(username)
        client.enqueue(object : Callback<UserDetailsResponse> {
            override fun onResponse(
                call: Call<UserDetailsResponse>,
                response: Response<UserDetailsResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailsUser.value = response.body()
                } else {
                    Log.e(TAG, "onFailure onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserDetailsResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure onFailure: ${t.message.toString()}")
            }

        })
    }

    fun insertToDB(user: UserItem) {
        mUserRepository.insert(user)
    }

    fun deleteFromDB(user: UserItem) {
        mUserRepository.delete(user)
    }

    fun getFavoriteUsers(): LiveData<List<UserItem>> = mUserRepository.getAllUsers()
}