package com.example.whenandwhere

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginDto (
    @Expose
    @SerializedName("accessToken") var accessToken : String,
    @SerializedName("data") var data : TokenDto
) : DataDto

data class ObjectDto (
    @Expose
    @SerializedName("data") var data : DataDto?,
    @SerializedName("message") var message : String
)

data class GroupListDto(
    @Expose
    @SerializedName("data") var data : ArrayList<GroupDto>,
    @SerializedName("message") var message : String
)

data class MemberListDto(
    @Expose
    @SerializedName("data") var data : ArrayList<UserDto>,
    @SerializedName("message") var message : String
)

data class ApplyListDto(
    @Expose
    @SerializedName("data") var data : ArrayList<ApplyDto>,
    @SerializedName("message") var message : String
)

data class ScheduleListDto(
    @Expose
    @SerializedName("data") var data : ArrayList<ScheduleDto>,
    @SerializedName("message") var message : String
)

interface DataDto

data class TokenDto(
    @SerializedName("token") var token : String,
    @SerializedName("email") val email : String
) : DataDto

data class UserDto(
    val id: Int,
    val userId: String,
    val nickname: String
) : DataDto

data class GroupDto(
    val id : Int,
    val groupName : String,
    val attribute : String
) : DataDto

data class ScheduleDto(
    val id: Int,
    val title: String,
    val detail: String,
    val startTime: String,
    val endTime: String
): DataDto

data class ApplyDto(
    val id : Int,
    val applyGroupId : Int?,
    val applierId : String?,
    val applierNickname : String?,
    val state : Boolean?,
    val decide : Boolean?
) : DataDto