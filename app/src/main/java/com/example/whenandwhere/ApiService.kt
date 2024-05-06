package com.example.whenandwhere

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("api/test")
    fun testData(): Call<String>

    @POST("api/oauth/sign-check")
    fun oauthCheck(@Body string : String) : Call<LoginDto>

    @GET("api/user/get-user")
    fun getUser() : Call<ObjectDto>

    @POST("api/user/sign-up")
    fun signUp(@Body userDto : UserDto) : Call<ObjectDto>

    @GET("api/group/get-my-groups")
    fun getMyGroups() : Call<ObjectListDto>

    @POST("api/group/create")
    fun createGroup(@Body groupDto : GroupDto) : Call<ObjectDto>
}