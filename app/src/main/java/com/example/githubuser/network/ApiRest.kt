package com.example.githubuser.network

import com.example.githubuser.model.Repository
import com.example.githubuser.model.SearchUserResponse
import com.example.githubuser.model.User
import com.example.githubuser.model.UserDetailsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiRest {

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUsers(@Query("q") username: String): Call<SearchUserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetails(@Path("username") username: String): Call<UserDetailsResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getUserFollowers(@Path("username") username: String): Call<List<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getUserFollowing(@Path("username") username: String): Call<List<User>>

    @GET("users/{username}/repos")
    @Headers("Authorization: token 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")
    fun getUserRepository(@Path("username") username: String): Call<List<Repository>>
}
