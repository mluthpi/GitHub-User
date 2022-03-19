package com.example.githubuser.network

import com.example.githubuser.model.Repository
import com.example.githubuser.model.SearchUserResponse
import com.example.githubuser.model.User
import com.example.githubuser.model.UserDetailsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiRest {

//    http://service.com/movies/list?movie_lang=hindi
//    http://service.com/movies/list
//    Single<JsonElement> getMovieList(@Query("movie_lang") String userLanguage);

    //    https://api.github.com/search/users?q=rakapermanaptr
    @GET("search/users")
    fun getUsers(@Query("q") username: String): Call<SearchUserResponse>

    @GET("users/{username}")
    fun getUserDetails(@Path("username") username: String): Call<UserDetailsResponse>

    @GET("users/{username}/followers")
    fun getUserFollowers(@Path("username") username: String): Call<List<User>>

    @GET("users/{username}/following")
    fun getUserFollowing(@Path("username") username: String): Call<List<User>>

    @GET("users/{username}/repos")
    fun getUserRepository(@Path("username") username: String): Call<List<Repository>>
}
