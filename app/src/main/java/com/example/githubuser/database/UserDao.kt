package com.example.githubuser.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.githubuser.model.UserItem

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: UserItem)

    @Delete
    fun delete(user: UserItem)

    @Query("SELECT * from useritem ORDER BY id ASC")
    fun getAllUsers(): LiveData<List<UserItem>>
}