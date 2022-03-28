package com.example.githubuser.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class SearchUserResponse(

	@Json(name="total_count")
	val totalCount: Int? = null,

	@Json(name="incomplete_results")
	val incompleteResults: Boolean? = null,

	@Json(name="items")
	val items: List<UserItem>? = null
)

@Entity
data class UserItem(
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "id")
	@Json(name="id")
	var id: Int? = 0,

	@Json(name="login")
	@ColumnInfo(name = "login")
	var login: String? = null,

	@Json(name="avatar_url")
	@ColumnInfo(name = "avatar_url")
	var avatarUrl: String? = null,
)
