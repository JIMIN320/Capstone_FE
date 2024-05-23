package com.example.whenandwhere

import android.provider.ContactsContract.Data
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime
import java.util.Objects
import android.os.Parcel
import android.os.Parcelable

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

data class AIResultDto(
    @Expose
    @SerializedName("data") var data : AIRecommend,
    @SerializedName("message") var message : String
)

data class RecommendResultDto(
    @Expose
    @SerializedName("data") var data : RecommendResult,
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
    val id : Int,
    val title : String,
    val detail : String,
    val startTime : String,
    val endTime : String
<<<<<<< HEAD
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(detail)
        parcel.writeString(startTime)
        parcel.writeString(endTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ScheduleDto> {
        override fun createFromParcel(parcel: Parcel): ScheduleDto {
            return ScheduleDto(parcel)
        }

        override fun newArray(size: Int): Array<ScheduleDto?> {
            return arrayOfNulls(size)
        }
    }
}
=======
): DataDto

>>>>>>> 525080dfae65d2f4bf313915d6e527729957d2eb
data class ApplyDto(
    val id : Int,
    val applyGroupId : Int?,
    val applierId : String?,
    val applierNickname : String?,
    val state : Boolean?,
    val decide : Boolean?
) : DataDto


data class AIRecommend (
    val restaurantObj : Recommend?,
    val cafeObj : Recommend?,
    val drinkObj : Recommend?
) :DataDto

data class Recommend(
    val name: String = "",
    val telephone: String = "",
    val address : String = "",
    val keyword: String = ""
)

data class RecommendResult(
    var id: Int? = null,
    var restTitle: String? = null,
    var restAddress: String? = null,
    var restPhone: String? = null,
    var restHash: String? = null,
    var cafeTitle: String? = null,
    var cafeAddress: String? = null,
    var cafePhone: String? = null,
    var cafeHash: String? = null,
    var drinkTitle: String? = null,
    var drinkAddress: String? = null,
    var drinkPhone: String? = null,
    var drinkHash: String? = null,
<<<<<<< HEAD
    var groupId: Int? = null,
    var resultAddress : String? = null,
    var startTime : String? = null,
    var endTime : String? = null
=======
    var groupId: Int? = null
>>>>>>> 525080dfae65d2f4bf313915d6e527729957d2eb
) : DataDto