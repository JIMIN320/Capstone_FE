package com.example.whenandwhere

import android.provider.ContactsContract.Data
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.Objects

data class LoginDto (
    @Expose
    @SerializedName("accessToken") var accessToken : String,
    @SerializedName("data") var data : TokenDto
) : DataDto

data class ObjectDto (
    @Expose
    @SerializedName("data") var data : DataDto,
    @SerializedName("message") var message : String
)

data class ObjectListDto(
    @Expose
    @SerializedName("data") var data : ArrayList<GroupDto>,
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