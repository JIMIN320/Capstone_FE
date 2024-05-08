package com.example.whenandwhere

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

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
    fun getMyGroups() : Call<GroupListDto>

    @POST("api/group/create")
    fun createGroup(@Body groupDto : GroupDto) : Call<ObjectDto>

    @GET("api/group/get-members/{group_id}")
    fun getGroupMembers(@Path("group_id", encoded = true) groupId: Int): Call<MemberListDto>

    @GET("api/schedule/get-schedule")
    fun getSchedules() : Call<ScheduleListDto>

    @POST("api/schedule/add")
    fun addSchedule(@Body scheduleDto: ScheduleDto) : Call<ObjectDto>
}