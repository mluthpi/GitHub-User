package com.example.githubuser.presentation.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.model.UserItem
import com.example.githubuser.repository.UserRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mUserRepository = UserRepository(application)

    fun getFavoriteUsers(): LiveData<List<UserItem>> = mUserRepository.getAllUsers()
}