package com.example.githubuser.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubuser.database.UserDao
import com.example.githubuser.database.UserRoomDatabase
import com.example.githubuser.model.UserItem
import java.util.concurrent.Executors

class UserRepository(application: Application) {
    private val mUserDao: UserDao
    private val executorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserRoomDatabase.getDatabase(application)
        mUserDao = db.userDao()
    }

    fun getAllUsers(): LiveData<List<UserItem>> = mUserDao.getAllUsers()

    fun insert(user: UserItem) {
        executorService.execute { mUserDao.insert(user) }
    }

    fun delete(user: UserItem) {
        executorService.execute { mUserDao.delete(user) }
    }

}